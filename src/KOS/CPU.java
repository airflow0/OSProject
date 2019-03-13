package KOS;

public class CPU
{
    public int programCounter;
    public int STATE;
    public final int WORKING = 2, STALE = 1, ACCUMULATION = 0;
    public static int count;

    public long stateAddress;

    private short op, s1Registery, s2Registry, registry1, registry2, dataRegistry,
}
