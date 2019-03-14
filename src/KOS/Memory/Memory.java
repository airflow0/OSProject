package KOS.Memory;

import KOS.Utility;

public class Memory
{
    private static final int diskSize = Utility.DISKSIZE;
    private static final int ramSize =  Utility.RAMSIZE;

    public Ram randomMemory;
    public Disk diskMemory;
    public Memory()
    {
        randomMemory = new Ram(ramSize);
        diskMemory = new Disk(diskSize);
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
        System.out.println("Inserted Data: " + information + "; Location: " + input);
    }
    public int getDiskLength()
    {
        return diskMemory.getDataCollectionLength();
    }
}
