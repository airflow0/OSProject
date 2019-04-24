import java.util.LinkedList;


public class shortTerm
{
    public LinkedList<Process> processList;
    public shortTerm(){
        processList = new LinkedList<>();
    }
    public void insert(Process process)
    {
        processList.add(process);
    }
    public void Schedule(Algorithm type)
    {
        ProcessHandler.ALGORITHM algorithm = ProcessHandler.ALGORITHM.FIFO;
        switch (type.getType())
        {
            case 1:
                algorithm = ProcessHandler.ALGORITHM.PRIO;
                break;
            case 2:
                algorithm = ProcessHandler.ALGORITHM.FIFO;
                break;
            case 3:
                algorithm = ProcessHandler.ALGORITHM.SJF;
                break;
        }

        ProcessHandler.applyAlgorithm(algorithm, processList);
    }
}

