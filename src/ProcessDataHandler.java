public class ProcessDataHandler
{
    private int procID = -1;
    private int CPU_NUM = -1;
    private long timestamp = 0;
    public long timeRun = 0;
    public long timeEndRun = 0;
    public long timeWait = 0;
    public int InputOutput = 0;

    public ProcessDataHandler()
    {
    }
    public void setProcID(int procID)
    {
        this.procID = procID;
    }
    public void setCPU_NUM(int CPU_NUM)
    {
        this.CPU_NUM = CPU_NUM;
    }
    public void setTimeWait(long timeWait)
    {
        this.timeWait = timeWait;
    }
    public void setTimeEndRun(long timeEndRun)
    {
        this.timeEndRun = timeEndRun;
    }
    public void setTimeRun(long timeRun)
    {
        this.timeRun = timeRun;
    }
    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

}
