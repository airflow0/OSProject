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
        this.procID = id;
        this.procAddress = address;
        this.procSize = size;
        this.procPriority = priority;
    }
    public STATE getStatus()
    {
        return status;
    }
    public void setStatus(STATE s)
    {
        this.status = s;
    }
    public int getProcId()
    {
        return this.procID;
    }
    public int getProcAddress()
    {
        return this.procAddress;
    }
    public int getProcSize()
    {
        return this.procSize;
    }
    public int getProcData()
    {
        return this.procDataSize;
    }
    public int getProcPriority()
    {
        return this.procPriority;
    }
    public short[] getCBuffer()
    {
        return this.inBuffer;
    }
    public short[] getOBuffer()
    {
        return this.OutBuffer;
    }
    public short[] getTBuffer()
    {
        return this.tempBuffer;
    }
    public void setCBuffer(short[] buffer)
    {
        this.inBuffer = buffer;
    }
    public void setOBuffer(short[] buffer)
    {
        this.OutBuffer = buffer;
    }
    public void setTBuffer(short[] buffer)
    {
        this.tempBuffer = buffer;
    }
    public int getIBufferSize()
    {
        return this.inBufferSize;
    }
    public int getOBufferSize()
    {
        return this.outBufferize;
    }
    public int getTBufferSize()
    {
        return this.tempBufferSize;
    }
    public long getInternalTimer()
    {
        return this.internalTimer;
    }
    public long getOuterTimer()
    {
        return this.outerTimer;
    }
    public long getCpuBegin()
    {
        return this.cpuBegin;
    }
    public long getCpuEnd()
    {
        return this.cpuEnd;
    }
    public int getBeginMemory()
    {
        return this.beginMemory;
    }
    public int getEndMemory()
    {
        return this.endMemory;
    }
    public void setBeginMemory(int memory) { this.beginMemory = memory;}
    public void setEndMemory(int memory) { this.endMemory = memory;}
    public void setMemorySize(int size)
    {
        this.memorysize = size;
    }
    public int getMemorysize()
    {
        return this.memorysize;
    }

    public void setInternalTimer(long timer)
    {
        this.internalTimer = timer;
    }
    public void setOuterTimer(long timer)
    {
        this.outerTimer = timer;
    }
    public void insertBufferData(int inputData, int tempData, int outputData)
    {
        this.inBufferSize = inputData;
        this.tempBufferSize = tempData;
        this.outBufferize = outputData;
        this.inBuffer = new short[inputData];
        this.tempBuffer = new short[tempData];
        this.OutBuffer = new short[outputData];
        System.out.println("Inserted meta data. inBuffer: " + inputData + " tempBuffer " + tempData + " outputData "+ outputData);
    }
}
