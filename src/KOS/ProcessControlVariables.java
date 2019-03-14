package KOS;


public class ProcessControlVariables
{
    //Buffers
    public short[] inBuffer;
    public short[] OutBuffer;
    public short[] tempBuffer;
    public int inBufferSize;
    public int outBufferize;
    public int tempBufferSize;

    //Memory Location
    public int beginMemory;
    public int endMemory;

    //Creating Processes
    private int procID;
    private int procAddress;
    private int procSize;
    private int procDataSize;
    private int procPriority;

    //Timer
    public long internalTimer = 0;
    public long outerTimer = 0;
    public long cpuBegin = 0;
    public long cpuEnd = 0;

    //PCBSTATUS
    public enum STATE { ACCESSIBLE, END}
    public STATE status;
    //Data size
    public int memorysize;

    ProcessControlVariables(int id, int address, int size, int priority)
    {
        procID = id;
        procAddress = address;
        procSize = size;
        procPriority = priority;
    }
    public STATE getStatus()
    {
        return status;
    }
    public void setStatus(STATE s)
    {
        status = s;
    }
    public int getProcId()
    {
        return procID;
    }
    public int getProcAddress()
    {
        return procAddress;
    }
    public int getProcSize()
    {
        return procSize;
    }
    public int getProcData()
    {
        return procDataSize;
    }
    public int getProcPriority()
    {
        return procPriority;
    }
    public short[] getCBuffer()
    {
        return inBuffer;
    }
    public short[] getOBuffer()
    {
        return OutBuffer;
    }
    public short[] getTBuffer()
    {
        return tempBuffer;
    }
    public void setCBuffer(short[] buffer)
    {
        inBuffer = buffer;
    }
    public void setOBuffer(short[] buffer)
    {
        OutBuffer = buffer;
    }
    public void setTBuffer(short[] buffer)
    {
        tempBuffer = buffer;
    }
    public int getIBufferSize()
    {
        return inBufferSize;
    }
    public int getOBufferSize()
    {
        return outBufferize;
    }
    public int getTBufferSize()
    {
        return tempBufferSize;
    }
    public long getInternalTimer()
    {
        return internalTimer;
    }
    public long getOuterTimer()
    {
        return outerTimer;
    }
    public long getCpuBegin()
    {
        return cpuBegin;
    }
    public long getCpuEnd()
    {
        return cpuEnd;
    }
    public int getBeginMemory()
    {
        return beginMemory;
    }
    public int getEndMemory()
    {
        return endMemory;
    }
    public void setBeginMemory(int memory) { beginMemory = memory;}
    public void setEndMemory(int memory) { endMemory = memory;}
    public void setMemorySize(int size)
    {
        memorysize = size;
    }
    public int getMemorysize()
    {
        return memorysize;
    }

    public void setInternalTimer(long timer)
    {
        internalTimer = timer;
    }
    public void setOuterTimer(long timer)
    {
        outerTimer = timer;
    }
    public void insertBufferData(int inputData, int tempData, int outputData)
    {
        inBufferSize = inputData;
        tempBufferSize = tempData;
        outBufferize = outputData;
        inBuffer = new short[inputData];
        tempBuffer = new short[tempData];
        OutBuffer = new short[outputData];
        System.out.println("Inserted meta data. inBuffer: " + inputData + " tempBuffer " + tempData + " outputData "+ outputData);
    }
}
