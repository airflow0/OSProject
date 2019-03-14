package KOS;

public class CPU
{
    public int programCounter;
    public int STATE;
    public final int WORKING = 2, STALE = 1, ACCUMULATION = 0;
    public static int count;

    public long stateAddress;

    private short op, s1Registery, s2Registry, registry1, registry2, dataRegistry;

    CPU(){}

    private void fetch()
    {

    }

    private void decode()
    {
        //done after fetch
    }

    private void execute()
    {
          //Increments PC (programCounter) value on success, produces logicalAddress (done after decode)
    }

    public int effectiveAddress(int logicalAddress)
    {
          //Takes in a logical address, returns its physical address
          int offset = logicalAddress % pageSize;
    }
}
