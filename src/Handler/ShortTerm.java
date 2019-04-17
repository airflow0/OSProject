package Handler;
import KOS.ProcessControlVariables;
public class ShortTerm
{
    public ProcessControlVariables pcb;
    public void sort()
    {
        int inqueueSize = LongTerm.controller.size();
        boolean inQueue = true;
        while(inQueue == true)
        {
            inqueueSize--;
            inQueue = false;
            for(int i =0; i< inqueueSize; i++)
            {
                if(LongTerm.controller.get(i).getProcPriority() < LongTerm.controller.get(i+1).getProcPriority())
                {
                    pcb = LongTerm.controller.get(i);
                    LongTerm.controller.set(i, LongTerm.controller.get(i+1));
                    LongTerm.controller.set(i+1, pcb);
                    inQueue = true;
                }
            }
        }
    }
    public synchronized ProcessControlVariables start(int id)
    {
        ProcessControlVariables pcb_ = LongTerm.controller.get(id);
        pcb_.setStatus(ProcessControlVariables.STATE.INQUEUE);
        LongTerm.controller.remove(pcb_);
        System.out.println("Job removed!");
        return pcb_;
    }
}
