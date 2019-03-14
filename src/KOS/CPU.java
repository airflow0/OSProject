package KOS;

public class CPU
{
    public int programCounter;
    public int STATE;
    public final int WORKING = 2, STALE = 1, ACCUMULATION = 0;
    public static int count;
    public final int registrySize = 16;

    public long stateAddress;

    public short op, s1Registery, s2Registry, registry1, registry2, dataRegistry, bufferRegistry;
    public int[] registry;
    public ProcessControl pcb;

    public CPU()
    {
        initalize();

    }
    public void startCPU( ProcessControl pcb)
    {


    }
    public void initalize()
    {
        programCounter = 0;
        count = 0;
        registry = new int[registrySize];
        registry[1] = 0;
    }
}
