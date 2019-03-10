package KOS.Memory;

public class Disk
{
    String[] dataCollection;

    public Disk(int size)
    {
        dataCollection = new String[size];
    }
    public String getDiskData(int size)
    {
        return dataCollection[size];
    }
    public void writeDiskData(int location, String input)
    {
        dataCollection[location] = input;
    }
}
