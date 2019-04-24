public class Memory
{


    public static void setPhysicalData(int location, int diff, String value )
    {
        RandomMemory.writeRam(location, diff, value);
    }

    public static String readPhysicalData(int location, int diff)
    {
        return RandomMemory.readRam(location, diff);
    }

    public static int getPageLocation(int addr)
    {
        return addr/Utility.PAGESIZE;

    }
    public static String readMemoryData(int addr)
    {
        return readPhysicalData(getLocation(getPageLocation(addr)), getDifference(addr));
    }

    public static void setMemoryData(int addr, String value)
    {
        int location = getPageLocation(addr);
        int off = getDifference(addr);
        setPhysicalData(getLocation(location), off, value);
    }
    public static int getDifference(int addr)
    {
        return addr % Utility.PAGESIZE;
    }

    public static int getLocation(int location)
    {
        return RandomMemory.getlocation(location);
    }

    public static void sync(Process proc)
    {
        int size = proc.getTmpMemorySize() + proc.getProcAddress();
        int address = proc.getProcAddress();
        int initial = address;
        for (int i = 0; initial < size; i++)
        {
            setMemoryData(proc.getFilledPages().get(i / Utility.PAGESIZE) * Utility.PAGESIZE + i% Utility.PAGESIZE, proc.getTmpMemory()[i]);
            Disk.setDiskData(proc.getTmpMemory()[i], initial);
            initial++;
        }
    }
}