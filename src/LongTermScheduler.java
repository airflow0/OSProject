import java.util.ArrayList;


public class LongTermScheduler
{
    public LongTermScheduler()
    {
    }

    public void Schedule()
    {
        if (!RandomMemory.memoryLimit())
        {
            int processes = ProcessHandler.getProcSize();
            for (int i = 1; i <= processes; i++)
            {
                if (!ProcessHandler.getProcess(i).isProcessed()  && !RandomMemory.memoryLimit() && ProcessHandler.getProcess(i).getStatus() == Process.STATE.NEW)
                {
                    loadProcesses(ProcessHandler.getProcess(i));
                }
            }
        }
    }


    public void loadProcesses(Process proc)
    {
        String[] resizedData;
        int pageNumber;
        int ProcID = proc.getProcID();
        int fullBufferSize = proc.getInBuffer() + proc.getOutBuffer() + proc.getTempBuffer();
        int availableMemory = ProcessHandler.getProcess(ProcID).getProcDataSize() + fullBufferSize;
        int sizeOfPage = getPages(availableMemory);
        int procMemoryLocation = proc.getProcAddress();
        int tmpProcLocation = procMemoryLocation;
        int tempPageNumber = RandomMemory.getNextAvailableLocation();
        ArrayList<Integer> filledPages = RandomMemory.fill(sizeOfPage);
        if (filledPages.size() != 0)
        {
            System.out.println(ProcID);
            ProcessHandler.getProcess(ProcID).setProcessed(true);
            ProcessHandler.getProcess(ProcID).setProcRamAddress(tempPageNumber * Utility.PAGESIZE);
            ProcessHandler.getProcess(ProcID).setStatus(Process.STATE.READY);
            ProcessHandler.getProcess(ProcID).setFilledPages(filledPages);


            for (int i = 0; i < filledPages.size(); i++)
            {
                pageNumber = RandomMemory.getlocation(filledPages.get(i));
                resizedData = Disk.getResizedData(tmpProcLocation, getPaceSize(proc, tmpProcLocation, fullBufferSize));
                try
                {
                    RandomMemory.fillPage(pageNumber, resizedData);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }

                tmpProcLocation = tmpProcLocation + Utility.PAGESIZE;
            }
            Main.ShortTermScheduler.insert(ProcessHandler.getProcess(ProcID));
        }
    }

    private static int getPages(int mem)
    {
        int page = (int) Math.ceil((double) mem / (double) Utility.PAGESIZE);
        return page;
    }

    private int getPaceSize(Process process, int address, int buffer)
    {
        int memory = process.getProcDataSize() + buffer;
        if (address + Utility.PAGESIZE > process.getProcAddress() + memory)
        {
            return (process.getProcAddress() + memory) - address;
        }
        else
        {
            return Utility.PAGESIZE;
        }

    }

}



