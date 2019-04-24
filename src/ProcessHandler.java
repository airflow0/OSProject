import java.util.*;


public final class ProcessHandler
{
    private static LinkedList<Process> process = new LinkedList<>();
    private static ALGORITHM initialAlgorithm;
    public static int getProcSize()
    {
        return process.size();
    }
    public static void addProcess(Process process)
    {
        ProcessHandler.process.add(process);
    }
    public static Process getProcess(int loc)
    {
        return process.get(loc-1);
    }
    public static void applyAlgorithm(ALGORITHM type)
    {
        processAlgorithm(type, process);
        initialAlgorithm = type;
    }
    public static void applyAlgorithm(ALGORITHM type, LinkedList<Process> list)
    {
        processAlgorithm(type, list);
    }
    public static ALGORITHM getAlgorithm() {return initialAlgorithm;}

    private static void processAlgorithm(ALGORITHM type, LinkedList<Process> processes)
    {
        if(type == ALGORITHM.FIFO)
        {
            Collections.sort(processes,(Process first, Process second) -> first.getProcID() - second.getProcID());
        }
        else if(type == ALGORITHM.SJF)
        {
            Collections.sort(processes,(Process first, Process second) -> first.getProcDataSize() - second.getProcDataSize());
        }
        else if(type == ALGORITHM.PRIO)
        {
            Collections.sort(processes,(Process first, Process second) -> first.getProcPriority() - second.getProcPriority());
        }
    }
    public static boolean processFinished(){
        ListIterator<Process> iter = process.listIterator();
        while (iter.hasNext())
        {
            if(iter.next().getStatus() != Process.STATE.PROCEND)
                return false;
        }
        return true;
    }
    public enum ALGORITHM
    {FIFO, PRIO, SJF}

    public ProcessHandler(){
        process = new LinkedList<>();
        initialAlgorithm = ALGORITHM.FIFO;
    }
}
