package Handler;

import KOS.Main;
import KOS.OSUtil;
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
    public int diskMemory;
    public int ramMemory;
    //LongTerm Scheduler
    public static ArrayList<ProcessControlVariables> controller;

    public LongTerm()
    {
        setup();

       start();

    }
    public void start()
    {
        while(checkMemory())
        {
            int procCounter;
            int thresh = procBegin + sizeOfProcess;
            process.setBeginMemory(processLocation);

            for(procCounter = procBegin; thresh > procCounter; procCounter++)
            {
                String binary = OSUtil.changeToBinary(procBegin);
                for(int i = 0; i <= 32; i++)
                {
                    short getBits = Short.valueOf(binary.substring(i, i+8),2);
                    System.out.println("Binary: " + getBits);

                }
                diskMemory = diskMemory - 4;
            }

        }
    }
    public void setup()
    {

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
        diskMemory = 1024;
        ramMemory = 1024;



        //Setup Processes


    }
    public boolean checkMemory()
    {
        int memory = sizeOfMemory * 4;
        if(diskMemory >= memory && getProcessControlCounter() > processLocation)
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
