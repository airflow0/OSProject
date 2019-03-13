package KOS.Memory;

public class Ram
{
    short[] randomMemory;
    public Ram(int size)
    {
        randomMemory = new short[size];
    }

    public short getRamData(int input)
    {
        return randomMemory[input];
    }
    public void setRamData(short input, int dataLocation)
    {
        randomMemory[dataLocation] = input;
    }
}
