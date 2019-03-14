package Handler;

import KOS.Main;
import KOS.Utility;
import KOS.ProcessControlVariables;
import com.sun.javafx.image.BytePixelSetter;

import java.math.BigInteger;
import java.util.*;

public class LongTerm
{

    //Processes (PCB)

    public int procBegin;
    public int procEnd;
    public int sizeOfProcess;

    public int processLocation = 0;
    public int currentLocation;
    public ProcessControlVariables process;
    //Buffers
    public int inputBufferSize;
    public int outputBufferSize;
    public int tempBufferSize;
    //Memory
    public int sizeOfMemory;
    private static int availableMemory = Utility.RAMSIZE;
    public int ramMemory = Utility.RAMSIZE;
    //LongTerm Scheduler

    public static ArrayList<ProcessControlVariables> controller;

    public LongTerm()
    {
        currentLocation = 0;
        controller = new ArrayList<ProcessControlVariables>();
        start();

    }
    public void start()
    {
        /**if(loadedAllProcess())
        {
            Main.end = true;
            return;
        }
        else
        {
            processLocation = 0;
            memoryLeft = 1024;
        }**/
        process = Main.process.getJob(currentLocation);
        sizeOfProcess = process.getProcSize();
        sizeOfMemory = process.getMemorysize();
        procBegin = process.getProcAddress();
        tempBufferSize = process.getTBufferSize();
        outputBufferSize = process.getOBufferSize();
        inputBufferSize = process.getIBufferSize();

        while(checkMemory())
        {
            process.setBeginMemory(processLocation);
            int thresh  = procBegin + sizeOfProcess;
            int procCounter = procBegin;
            while(procCounter < thresh)
            {
                int j = 0;
                String converted = Utility.changeToBinary(procCounter);
                for(int i = 0; i < 3; i++)
                {

                    Main.memory.setRamInformation(Short.valueOf(converted.substring(j,j+8),2),processLocation++);
                    j = j + 8;
                }
                procCounter++;
                availableMemory = availableMemory - 4;
                System.out.println("Memleft: " + availableMemory);
            }

            System.out.println("Size of Process: " + sizeOfMemory);
            short[] dataStorage = new short[sizeOfMemory*4];
            int temp = 0;
            String binary;
            while(temp < process.getMemorysize()*4)
            {
                int j = 32;
                binary = Utility.changeToBinary(thresh);

                for(int i = 0;i < 4; i++)
                {
                    dataStorage[temp] = Short.valueOf(binary.substring(j-8,j), 2);
                    temp = temp + 1;
                    j = j - 8;
                }
                thresh = thresh + 1;

            }
            process.setEndMemory(processLocation);
            process.setCBuffer(dataStorage);
            process.setStatus(ProcessControlVariables.STATE.ACCESSIBLE);
            controller.add(process);
            if(Main.process.getProcessCounter() < currentLocation)
            {
                Main.end = true;
            }
            currentLocation = currentLocation+1;
            if ( Main.process.getProcessCounter() > currentLocation)
            {
                System.out.println("Job: " + currentLocation);
                process = Main.process.getJob(currentLocation);
                procBegin = process.getProcAddress();
                sizeOfProcess = process.getProcSize();
                sizeOfMemory = process.getMemorysize();
                inputBufferSize = process.getIBufferSize();
            }
            else
            {
                Main.end = true;
            }
        }
        return;
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

    public boolean checkMemory()
    {
        if(availableMemory >= (sizeOfProcess*4) && getProcessControlCounter() > currentLocation)
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