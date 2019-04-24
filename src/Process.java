import java.util.ArrayList;
import java.util.Arrays;
public class Process
{
    private int procID;
    private int procPriority;
    private int procAddress;
    private int procRamAddress;
    private int procDataSize;
    private int dataAddress;
    private int pagesNeeded;

    private int inBuffer;
    private int outBuffer;
    private int tempBuffer;

    private boolean processed;
    private int[] Reg;
    private ArrayList<Integer> filledPages;
    private int pc;
    private String[] tmpMemory;
    private int tmpMemorySize;
    public enum STATE
    {
        NEW, READY, WAIT, PROCEND
    }
    private STATE status;

    public Process(int procID, int procAddress, int procDataSize, int procPriority)
    {
        this.procID = procID;
        this.procPriority = procPriority;
        this.procAddress = procAddress;
        this.procDataSize = procDataSize;
        Reg = new int[16];
        Arrays.fill(Reg,0);
        status = STATE.NEW;
        processed = false;
    }
    public void setDataAddress(int dataAddress) {
        this.dataAddress = dataAddress;
    }
    public int getProcDataSize() {
        return procDataSize;
    }
    public void setProcDataSize(int procDataSize) {
        this.procDataSize = procDataSize;
    }
    public void setProcRamAddress(int procRamAddress) {
        this.procRamAddress = procRamAddress;
    }
    public int getProcAddress() {
        return procAddress;
    }
    public int getProcPriority() {
        return procPriority;
    }
    public int getProcID() {
        return procID;
    }



    public int getPC()
    {
        return pc;
    }
    public void setPC(int pc)
    {
        this.pc = pc;
    }
    public void setReg(int[] reg)
    {
        Reg = reg;
    }
    public STATE getStatus() {
        return status;
    }
    public void setStatus(STATE status) {
        this.status = status;
    }
    public boolean isProcessed() {
        return processed;
    }
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
    public int getTempBuffer() {
        return tempBuffer;
    }
    public void setTempBuffer(int tempBuffer) {
        this.tempBuffer = tempBuffer;
    }
    public int getOutBuffer() {
        return outBuffer;
    }
    public void setOutBuffer(int outBuffer) {
        this.outBuffer = outBuffer;
    }
    public int getInBuffer() {
        return inBuffer;
    }
    public void setInBuffer(int inBuffer) {
        this.inBuffer = inBuffer;
    }
    public int getTmpMemorySize() {
        return tmpMemorySize;
    }
    public void setTmpMemorySize(int mem) {
        this.tmpMemorySize = mem;
    }
    public String[] getTmpMemory() {
        return tmpMemory;
    }
    public void setTmpMemory(String[] tmpMemory) {
        this.tmpMemory = tmpMemory;
    }
    public ArrayList<Integer> getFilledPages() {
        return filledPages;
    }
    public void setFilledPages(ArrayList<Integer> filledPages) {
        this.filledPages = filledPages;
    }
}




