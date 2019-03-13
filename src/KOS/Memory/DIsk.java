package KOS.Memory;

public class Disk
{
    public String[] dataCollection;

    public Disk(int size)
    {
        dataCollection = new String[size];
    }
    public String getDiskData(int input)
    {
        return dataCollection[input];
    }
    public void setDiskData(int location, String input)
    {
        dataCollection[location] = input;
    }
}
