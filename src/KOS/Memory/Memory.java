package KOS.Memory;

import KOS.Main;
import KOS.ProcessControl;
import KOS.ProcessControlVariables;
import KOS.Utility;

import java.lang.reflect.Array;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;

public class Memory
{

    public Ram randomMemory;
    public Disk diskMemory;
    public static int pNum, fNum;
    public static PriorityBlockingQueue<Integer> pool;
    public ArrayList<Integer> frames;
    public Frame[] table;

    public Memory()
    {
        randomMemory = new Ram(Utility.RAMSIZE);
        diskMemory = new Disk(Utility.DISKSIZE);
        fNum = Utility.RAMSIZE/16;
        pNum = Utility.DISKSIZE/16;
        frames = new ArrayList<Integer>();
        pool = new PriorityBlockingQueue<Integer>();
        table = new Frame[fNum];
        for(int i = 0; i < fNum; i++)
        {
            createTable(i);
        }


    }
    public synchronized void removeFrames(ArrayList temp)
    {
        frames = temp;
        while(!temp.isEmpty())
        {
            int removeFrame = frames.remove(0);
            pool.add(removeFrame);
        }
    }
    public synchronized int getAddress(int index, ProcessControlVariables process)
    {
        System.out.println("Getting address for: " + process.getPageTable());
        int page,frame, programCounter;
        String offset, pNum;
        String address = Integer.toBinaryString(index);
        while(address.length() < 10)
        {
            address = "0" + address;
        }
        pNum = address.substring(0,6);
        page = Integer.valueOf(pNum, 2);
        offset = address.substring(6,10);
        frame = Main.process.getFrame(page, process);
        process.addFrames(frame);
        programCounter = (frame * 16) + Integer.valueOf(offset, 2);
        System.out.println("Page: " + page + "; Frame: " + frame + "; ProgramCounter: " + programCounter + "; offset: " + offset + "; pNum: " + pNum + "; Address: " + address);
        return programCounter;

    }
    public synchronized int getPNum(int index, ProcessControlVariables pcb)
    {
        int start = pcb.getProcAddress();
        start = start + index*4;
        int frame = nextFrame();
        int indexF = frame * 16;
        for(int i = start; i < start+4; i++)
        {
            String convert = Utility.changeToBinary(i);
            int j = 0;
            for (int k = 0; k < 4; k++)
            {

                short section = Short.valueOf(convert.substring(j, j + 8), 2);
                Main.memory.setRamInformation(section, indexF);
                j = j + 8;
                indexF++;
            }
        }
        return frame;

    }
    public synchronized int nextFrame()
    {
        System.out.println("FRAME POOL: " + pool.toString());
        if(!pool.isEmpty())
        {
            return pool.poll();
        }
        else
        {
            return pool.size();
        }
    }
    public void printFrame()
    {
        System.out.println("FRAME");
        for(Frame fte: table)
        {
            System.out.println(fte.toString());
        }
        System.out.println("");
    }
    public synchronized void updateFrame(int fNum, int pNum, Boolean alloc, int id)
    {
        table[fNum].update(pNum,id,alloc);
    }
    public synchronized void createTable(int i)
    {
        table[i] = new Frame();
        pool.add(i);
    }
    public synchronized short getRamInformation(int input)
    {
        return randomMemory.getRamData(input);
    }
    public synchronized void setRamInformation(short information, int location)
    {
        randomMemory.setRamData(information, location);
    }
    public synchronized String getDiskInformation(int input)
    {
        return diskMemory.getDiskData(input);
    }
    public synchronized void setDiskInformation(String information, int input)
    {
        diskMemory.setDiskData(input, information);
        //System.out.println("Inserted Data: " + information + "; Location: " + input);
    }
    public int getDiskLength()
    {
        return diskMemory.getDataCollectionLength();
    }
}
