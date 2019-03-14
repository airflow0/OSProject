package Handler;

import KOS.Main;
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

            }

        }
    }
    public void setup()
    {
        //Setup Buffers
        inputBufferSize = process.getIBufferSize();
        tempBufferSize = process.getTBufferSize();
        outputBufferSize = process.getOBufferSize();

        //Setup Processes
        processLocation = 0;
        process = Main.process.getJob(processLocation);
        sizeOfProcess = process.getProcSize();
        sizeOfMemory = process.getMemorysize();
        procBegin = process.getProcAddress();

        //memory
        diskMemory = 1024;
        ramMemory = 1024;

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
