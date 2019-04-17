package KOS;
import java.util.*;
public class ProcessControl
{
    public ProcessControlVariables block;
    public static Stack<ProcessControlVariables> pcbQueue;
    public static int processCounter = 0;
    public static Page[] page;

    public ProcessControl()
    {
        pcbQueue = new Stack<ProcessControlVariables>();
        page = new Page[512];
        for(int i = 0; i < 512;i++)
        {
            initPage(i);
        }
    }

    public ProcessControlVariables getJob(int i)
    {
        return pcbQueue.get(i);
    }
    public int getProcessCounter()
    {
        return processCounter;
    }
    public void insertJob(int id, int address, int size, int priority)
    {
        block = new ProcessControlVariables(id, address, size, priority);
        //Utility.write("Added job to PCB [ID: " + id + "], [address: " + address + "], [size: " + size + "],[priority: " + priority + "]" );
    }
    public ProcessControlVariables findJob(int input)
    {
        return pcbQueue.get(input);
    }
    public void insertMetaData(int inputData, int tempData, int outputData)
    {
        //Utility.write("Added metaData to PCB [inputData: " + inputData + "], [tempData: " + tempData + "], [outputData: " + outputData  + "]" );
        block.insertBufferData(inputData, tempData, outputData);
        pcbQueue.add(block);
        processCounter++;
        //.write("[Meta Data Location: " + processCounter + "]");
    }
    public void setMemorySize(int size)
    {
        block.setMemorySize(size);
    }
    public void initPage(int index)
    {
        page[index] = new Page(index);
    }
    public synchronized void updatePage(int index, int fNum, boolean flag)
    {
        page[index].updatePage(fNum, flag);
    }
    public synchronized int getFrame(int index, ProcessControlVariables process)
    {
        int offset = process.getPageTable();
        int pageOffset = index + offset;
        try
        {
            page[pageOffset].getIsValid();
            return page[pageOffset].getfNum();

        }
        catch (Exception ex)
        {
            updatePage(pageOffset,Main.memory.getPNum(index, process), true);
            return page[pageOffset].getfNum();
        }
    }
    public void printPage()
    {
        System.out.println("Page Table");
        for(ProcessControlVariables p : pcbQueue)
        {
            System.out.println("Process: " + p.getProcId() + " PTBR: " + p.getPageTable());
        }
        System.out.println("");
    }
}
