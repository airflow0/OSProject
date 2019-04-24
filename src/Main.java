import java.util.ArrayList;
import java.util.concurrent.*;

public class Main
{
    public static LongTermScheduler LongTermScheduler;
    public static shortTerm ShortTermScheduler;

    public static CPU[] CPU;
    public static ProcessDataHandler[] processDataHandlers;
    public static ExecutorService execute;
    public static Future<?>[] executeCPUs;
    public static ArrayList<String> jobsRan;
    public static long timeStart = 0;
    public static long timeEnd = 0;
    public static int count;
    public static long delay = 0;
    public static int NUM_CPUS=4;
    public static boolean END = false;
    public static Algorithm algorithm = Algorithm.PRIORITY;

    public static void main(String [] args) {
        try
        {
            Disk disk = new Disk();
            RandomMemory memory = new RandomMemory();
            ProcessHandler proc = new ProcessHandler();
            Loader loader = new Loader();
            LongTermScheduler = new LongTermScheduler();
            ShortTermScheduler = new shortTerm();
            CPU = new CPU[NUM_CPUS];
            initCPU();
            jobsRan = new ArrayList<>();
            END = false;
            count = 0;
            executeCPUs = new Future<?>[NUM_CPUS];
            execute = Executors.newFixedThreadPool(NUM_CPUS);
            timeStart = System.currentTimeMillis();
            processDataHandlers = new ProcessDataHandler[ProcessHandler.getProcSize()];
            for (int i = 0; i < processDataHandlers.length; i++)
                processDataHandlers[i] = new ProcessDataHandler();

            while (!END && !ProcessHandler.processFinished())
            {
                LongTermScheduler.Schedule();
                ShortTermScheduler.Schedule(algorithm);
                removeProcesses();
                for (int i = 0; i < Main.CPU.length; i++) {
                    if (Main.CPU[i].getReadyStatus() && Main.ShortTermScheduler.processList.size() > 0)
                    {
                        Process pcb  = Main.ShortTermScheduler.processList.pop();
                        if(Main.CPU[i].getStartCPU())
                        {
                            removeProcesses();
                        }
                        Main.processDataHandlers[pcb.getProcID()-1].setTimestamp(System.currentTimeMillis());
                        Main.processDataHandlers[pcb.getProcID()-1].setProcID(pcb.getProcID());
                        Main.processDataHandlers[pcb.getProcID()-1].setTimeWait(System.currentTimeMillis());
                        Main.processDataHandlers[pcb.getProcID()-1].setCPU_NUM(i+1);
                        Main.CPU[i].load(pcb);

                    }
                }
                if (Main.ShortTermScheduler.processList.size() == 0 && Main.cpuREADY()) {
                    removeProcesses();
                    Main.execute.shutdown();
                    Main.END = true;
                    Main.timeEnd = System.currentTimeMillis();
                    System.out.println("Ending Program!");
                }
                for (int i = 0; i < CPU.length && !END && !ProcessHandler.processFinished(); i++)
                {
                    try {

                        if(executeCPUs[i] != null  && executeCPUs[i].isDone())
                            executeCPUs[i].get();
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    if(((executeCPUs[i] == null || executeCPUs[i].isDone() || executeCPUs[i].isCancelled()) && CPU[i].getDataStatus() && !CPU[i].getStartCPU()))
                    {
                        executeCPUs[i] = execute.submit(CPU[i]);
                    }
                }
            }
            execute.shutdown();
            System.out.println("Final Program Time!: " + timeRunning());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static void initCPU()
    {
        for (int i = 0; i < CPU.length; i++)
        {
            CPU[i] = new CPU(i + 1);
        }

    }

    public static double timeRunning()
    {
        return (double)((timeEnd - timeStart)/1000.0);
    }

    public static boolean cpuREADY()
    {
        for (int i = 0; i < CPU.length; i++)
        {
            if(!CPU[i].getReadyStatus())
                return false;
        }
        return true;
    }
    public static void removeProcesses()
    {
        for (int i = 0; i < Main.CPU.length; i++)
        {
            int procLocation = Main.CPU[i].getProcMemory();
            try {
                if (Main.CPU[i].getReadyStatus() && Main.CPU[i].cpuDONE()) {
                    Main.CPU[i].stop(ProcessHandler.getProcess(procLocation));
                    Memory.sync(ProcessHandler.getProcess(procLocation));
                    RandomMemory.removeFromProcess(ProcessHandler.getProcess(procLocation));
                    if(ProcessHandler.getProcess(procLocation).getStatus() == Process.STATE.PROCEND)
                    {
                        Main.count++;
                    }

                }
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
