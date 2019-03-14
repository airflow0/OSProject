package Handler;

import KOS.Main;
import KOS.Utility;
import KOS.ProcessControlVariables;
import com.sun.javafx.image.BytePixelSetter;

import java.util.*;

public class LongTerm
{

    //Processes (PCB)

    public int procBegin;
    public int procEnd;
    public int sizeOfProcess;

    public int processLocation;
    public int currentLocation;
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
        if(loadedAllProcess())
        {
            Main.status = true;
        }
        else
        {
            memoryLeft = 1024;
            processLocation = 0;
        }

        while(checkMemory())
        {
            process.setBeginMemory(processLocation);

            insertRamData();
            short[] temp = createProcesses();
            process.setEndMemory(processLocation);
            process.setStatus(ProcessControlVariables.STATE.ACCESSIBLE);

            controller.add(process);

            System.out.println("Process Counter: " + Main.process.getProcessCounter());
            if(Main.process.getProcessCounter() < currentLocation)
            {
                Main.status = true;
            }
            currentLocation = currentLocation + 1;
            if ( Main.process.getProcessCounter() > currentLocation)
            {
                System.out.println("Process: " + currentLocation);
                process = Main.process.getJob(currentLocation);
                procBegin = process.getProcAddress();
                sizeOfProcess = process.getProcSize();
                sizeOfMemory = process.getMemorysize();
                inputBufferSize = process.getIBufferSize();
            }
            else
            {
                Main.status = true;
            }
        }

        System.out.println(memoryLeft);
    }

    public void setup()
    {

        //Setup Processes
        process = Main.process.getJob(currentLocation);
        processLocation = 0;
        sizeOfProcess = process.getProcSize();
        sizeOfMemory = process.getMemorysize();
        procBegin = process.getProcAddress();

        //Setup Buffers
        tempBufferSize = process.getTBufferSize();
        outputBufferSize = process.getOBufferSize();
        inputBufferSize = process.getIBufferSize();

        //memory
        memoryLeft = 1024;
        ramMemory = 1024;

        controller = new ArrayList<ProcessControlVariables>();
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
            String binary = Utility.changeToBinary(i);
            for(int j = 0; j < 4; j++)
            {
                dataStorage[i] = Short.valueOf(binary.substring(split-8, split),2);
                System.out.println("Added to Disk: " + dataStorage[i] + ", Thresh: " + thresh);
                split = split - 8;

            }
            thresh++;
        }
        return dataStorage;
    }
    public Boolean loadedAllProcess()
    {
        for(int i = 0; i < Main.process.getProcessCounter(); i++)
        {
            ProcessControlVariables temp = Main.process.getJob(i);
            if(temp.getStatus() == ProcessControlVariables.STATE.ACCESSIBLE)
            {
                return false;
            }
        }
        return true;
    }
    public void insertRamData()
    {

        int procCounter = 0;
        while (procCounter < thresh)
        {
            String binary = Utility.changeToBinary(procCounter);
            //System.out.println(binary);
            int j = 0;
            for (int i = 0; i < 4; i++)
            {

                short splitBinaryData = Short.valueOf(binary.substring(j, j + 8), 2);
                j = j + 8;

                Main.memory.setRamInformation(splitBinaryData, processLocation);
                System.out.println("Added RAM: " + splitBinaryData + " Location: " + processLocation);
                processLocation = processLocation + 1;
            }
            memoryLeft = memoryLeft - 4;
            procCounter++;
        }
    }
    public boolean checkMemory()
    {
        int memory = sizeOfMemory * 4;
        if(memoryLeft >= memory && getProcessControlCounter() > currentLocation)
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