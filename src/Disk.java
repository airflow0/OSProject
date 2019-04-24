public final class Disk
{

    private static String diskMemory[] = new String[Utility.DISKMEM];
    public Disk()
    {
        diskMemory = new String[Utility.DISKMEM];
    }
    public void initialize()
    {
        diskMemory = new String[Utility.DISKMEM];
    }
    public static void setDiskData(String input, int loc)
    {
        diskMemory[loc] = input;
    }
    public static String[] getResizedData(int loc, int mem)
    {
        String[] newData = new String[mem];
        int memoryLocation = 0;
        for (int startPosition = loc; startPosition < loc+mem; startPosition++)
        {
            newData[memoryLocation] = diskMemory[startPosition];
            memoryLocation++;
        }
        return newData;
    }
}
