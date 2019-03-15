package Handler;

import KOS.Main;
import KOS.Utility;
import KOS.ProcessControlVariables;
import com.sun.javafx.image.BytePixelSetter;

import java.io.FileWriter;
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

    FileWriter writer;
    //LongTerm Scheduler
    public ArrayList<ProcessControlVariables> controller;

    public LongTerm()
    {
        currentLocation = 0;
        controller = new ArrayList<ProcessControlVariables>();
        try
        {
            writer = new FileWriter("RAM DUMP", true);
        }
        catch(Exception ex)
        {

        }
        start();

    }
    public void start()
    {
        if(Main.process.getProcessCounter() == controller.size())
        {
            Main.end = true;
        }
        else
        {
            availableMemory = Utility.RAMSIZE;
            processLocation = 0;
        }
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
                    Utility.write("[CONVERT TO]: " + converted);
                }
                procCounter++;
                availableMemory = availableMemory - 4;
                Utility.write("Available Memory: " + availableMemory);
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
                    Utility.write("[CONVERT TO]: " + binary);
                }
                thresh = thresh + 1;
                Utility.write("[THRESHHOLD]: " + thresh);

            }
            process.setEndMemory(processLocation);
            process.setCBuffer(dataStorage);
            process.setStatus(ProcessControlVariables.STATE.ACCESSIBLE);
            controller.add(process);
            Utility.write("[DATA LOADING TIME: ]" + System.nanoTime());
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
        Utility.write("[FULL PROCESS ENDING]" + System.nanoTime());
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