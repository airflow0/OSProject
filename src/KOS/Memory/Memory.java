package KOS.Memory;
import KOS.Utility;

public class Memory
{
    private static final int diskSize = 2048;
    private static final int ramSize = 1024;

    public Ram randomMemory;
    public Disk diskMemory;
    public Memory()
    {
        randomMemory = new Ram(ramSize);
        diskMemory = new Disk(diskSize);
    }
    public short getRamInformation(int input)
    {
        return randomMemory.getRamData(input);
    }
    public void setRamInformation(short information, int location)
    {
        randomMemory.setRamData(information, location);
        //System.out.println("Inserted Binary Data to Ram; Information: " + information + ", Location: " + location);
    }
    public String getDiskInformation(int input)
    {
        return diskMemory.getDiskData(input);
    }
    public void setDiskInformation(String information, int input)
    {
        diskMemory.setDiskData(input, information);
        //System.out.println("Inserted Data: " + information + "; Location: " + input);
    }
    public int getDiskLength()
    {
        return diskMemory.getDataCollectionLength();
    }
}
