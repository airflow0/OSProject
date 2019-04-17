package Handler;

import KOS.Main;
import KOS.Utility;
import KOS.ProcessControlVariables;
import com.sun.javafx.image.BytePixelSetter;
import sun.util.locale.provider.AvailableLanguageTags;

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
    public int pNum,fNum = 0, fCount, incProcStart, fProcBegin;

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
    public static ArrayList<ProcessControlVariables> controller;
    public ProcessControlVariables process;

    public LongTerm()
    {
        currentLocation = 0;
        controller = new ArrayList<>();
        start();

    }
    public void printControlVariables()
    {
        for(ProcessControlVariables e : controller)
        {
            process.printPCB();
        }
    }
    public void start()
    {
        while (checkMemory())
        {


            process = Main.process.getJob(currentLocation);
            sizeOfProcess = process.getProcSize();
            sizeOfMemory = process.getMemorysize();
            procBegin = process.getProcAddress();
            tempBufferSize = process.getTBufferSize();
            outputBufferSize = process.getOBufferSize();
            inputBufferSize = process.getIBufferSize();
            fCount = 0;

            System.out.println("Proc Begin: " + procBegin);
            while (fCount < 4)
            {
                fNum = Main.memory.nextFrame();
                process.addFrames(fNum);
                pNum = procBegin / 4;
                process.setOffset(procBegin % 4);
                processLocation = fNum * 16;
                int thresh = procBegin + 4;
                int procCounter = procBegin;
                process.setBeginMemory(processLocation);

                //System.out.println("Frame Number: " + fNum + "; page Number: " + pNum + "; location: " + processLocation);

                while (procCounter < thresh)
                {
                    System.out.println("ProcCounter: " + procCounter + "; Thresh: " + " Available Memory: " + availableMemory);
                    int j = 0;
                    String converted = Utility.changeToBinary(procCounter);
                    for (int i = 0; i < 4; i++)
                    {

                        short section = Short.valueOf(converted.substring(j, j + 8), 2);
                        Main.memory.setRamInformation(section, processLocation);
                        //System.out.println("Counter: " + i + " Section: " + section+ " Location: " + processLocation);
                        j = j + 8;
                        processLocation++;
                    }
                    procCounter++;
                    //System.out.println("Memory Available: " + availableMemory);
                    availableMemory = availableMemory - 4;
                }
                if (fCount == 0)
                {
                    process.setPageTable(pNum);
                }
                System.out.println("Frame Number: " + fNum + "; page Number: " + pNum + "; location: " + processLocation);
                Main.memory.updateFrame(fNum, pNum, true, process.getProcId());

                Main.process.updatePage(pNum, fNum, true);
                procBegin = procBegin + 4;
                fCount++;
            }


            //System.out.println("Size of Process: " + sizeOfMemory);
            short[] dataStorage = new short[sizeOfMemory * 4];
            int dIndex = process.getProcAddress() + process.getProcSize();
            int temp = 0;
            String binary;
            while (temp < process.getMemorysize() * 4)
            {
                int j = 32;
                binary = Utility.changeToBinary(dIndex);

                for (int i = 0; i < 4; i++)
                {
                    dataStorage[temp] = Short.valueOf(binary.substring(j - 8, j), 2);
                    temp = temp + 1;
                    j = j - 8;
                }
                dIndex = dIndex + 1;
            }
            process.setEndMemory(processLocation);
            process.setCBuffer(dataStorage);
            process.setStatus(ProcessControlVariables.STATE.ACCESSIBLE);
            controller.add(process);
            process.setInternalTimer(System.nanoTime());
            currentLocation = currentLocation + 1;


        }
        if (loadedAllProcess())
        {
            System.out.println("All jobs loaded!");
            Main.end = true;
            return;
        }
        else
        {
            processLocation = 0;
            availableMemory = Utility.RAMSIZE;
        }
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
        if(availableMemory >= 32 && Main.process.getProcessCounter() > currentLocation)
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