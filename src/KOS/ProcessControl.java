package KOS;
import java.util.*;
public class ProcessControl
{
    public ProcessControlVariables block;
    public static Stack<ProcessControlVariables> pcbQueue;
    public static int processCounter = 0;

    public ProcessControl()
    {
        pcbQueue = new Stack<ProcessControlVariables>();
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
}
