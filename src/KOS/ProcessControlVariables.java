package KOS;


public class ProcessControlVariables
{
    //Buffers
    public int[] CBuffer;
    public int[] OBuffer;
    public int[] tBuffer;
    public int inBufferSize;
    public int outBufferize;
    public int tempBufferSize;

    //Creating Processes
    public int procID;
    public int procAddress;
    public int procSize;
    public int procDataSize;
    public int procPriority;

    //Timer
    public long internalTimer = 0;
    public long outerTimer = 0;
    public long cpuBegin = 0;
    public long cpuEnd = 0;
    public int beginMemory;
    public int beginEnd;

    ProcessControlVariables(int id, int address, int size, int priority)
    {
        procID = id;
        procAddress = address;
        procSize = size;
        procPriority = priority;
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
    public int[] getCBuffer()
    {
        return CBuffer;
    }
    public int[] getOBuffer()
    {
        return OBuffer;
    }
    public int[] getTBuffer()
    {
        return tBuffer;
    }
    public void setCBuffer(int[] buffer)
    {
        CBuffer = buffer;
    }
    public void setOBuffer(int[] buffer)
    {
        OBuffer = buffer;
    }
    public void setTBuffer(int[] buffer)
    {
        tBuffer = buffer;
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
    public int getBeginEnd()
    {
        return beginEnd;
    }
    public void setInternalTimer(long timer)
    {
        internalTimer = timer;
    }
    public void setOuterTimer(long timer)
    {
        outerTimer = timer;
    }
}
