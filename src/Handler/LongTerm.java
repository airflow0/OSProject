package Handler;

import KOS.Main;
import KOS.Utility;
import KOS.ProcessControlVariables;
import java.util.*;

public class LongTerm
{

    //Processes (PCB)

    public int procBegin;
    public int procEnd;
    public int sizeOfProcess;

    public int processLocation;
    public int procCounter;
    public ProcessControlVariables process;
    //Buffers
    public int inputBufferSize;
    public int outputBufferSize;
    public int tempBufferSize;
    //Memory
    public int sizeOfMemory;
    public int memoryLeft;
    public int ramMemory;
    //LongTerm Scheduler

    public int thresh;
    public static ArrayList<ProcessControlVariables> controller;

    public LongTerm()
    {
        setup();
        start();

    }
    public void start()
    {

        process.setBeginMemory(processLocation);
        while(checkMemory())
        {
            insertRamData();
            process.setCBuffer(createProcesses());
        }

        System.out.println(memoryLeft);
    }

    public void setup()
    {
        //Setup Processes
        process = Main.process.getJob(processLocation);
        processLocation = 0;
        sizeOfProcess = process.getProcSize();
        sizeOfMemory = process.getMemorysize();
        procBegin = process.getProcAddress();

        //Setup Buffers
        tempBufferSize = process.getTBufferSize();
        outputBufferSize = process.getOBufferSize();
        inputBufferSize = process.getIBufferSize();

        System.out.println("TempBufferSize: " + tempBufferSize + ", inputBufferSize " + inputBufferSize + ", outputBufferSize " + outputBufferSize);


        //memory
        memoryLeft = 1024;
        ramMemory = 1024;
        thresh  = procBegin + sizeOfProcess;


    }
    public short[] createProcesses()
    {


        short[] dataStorage = new short[sizeOfMemory*4];
        System.out.println(sizeOfMemory*4);
        System.out.println(process.getMemorysize()*4);
        for(int i = 0; i < process.getMemorysize()*4; i=i+4)
        {
            int split = 32;
            System.out.println("Thresh: " + thresh);
            String binary = Utility.changeToBinary(thresh);
            System.out.println(binary);
            for(int j = 0; j < 4; j++)
            {
                dataStorage[i] = Short.valueOf(binary.substring(split-8, split),2);
                split = split - 8;

            }
            thresh++;
        }
        return dataStorage;
    }

    public void insertRamData()
    {

        procCounter = 0;
        while (thresh > procCounter)
        {
            String binary = Utility.changeToBinary(procCounter);
            System.out.println(binary);
            int j = 0;
            for (int i = 0; i < 4; i++)
            {

                short splitBinaryData = Short.valueOf(binary.substring(j, j + 8), 2);
                j = j + 8;

                Main.memory.setRamInformation(splitBinaryData, processLocation);
                processLocation = processLocation + 1;
            }
            memoryLeft = memoryLeft - 4;
            procCounter++;
        }
    }
    public boolean checkMemory()
    {
        int memory = sizeOfMemory * 4;
        if(memoryLeft >= memory && getProcessControlCounter() > processLocation)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public int getProcessControlCounter()
    {
        return Main.process.getProcessCounter();
    }

}
