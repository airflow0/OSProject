package KOS;

import java.io.*;

import KOS.Loader;
import KOS.Memory.*;
import Handler.*;
import KOS.ProcessControl;


public class Main
{
    public static Memory memory;
    public static ProcessControl process;
    public static Loader load;
    public static boolean end = false;
    public static LongTerm longScheduler;
    public static ShortTerm shortScheduler;
    public static CPU[] CPUs;
    public static CPU cpu1;
    public static CPU cpu2;
    public static CPU cpu3;
    public static CPU cpu4;

    public static void main(String[] args)
    {
        process = new ProcessControl();
        memory = new Memory();
        load = new Loader();
        CPU cpu = new CPU();
        CPUs = new CPU[4];
        longScheduler = new LongTerm();
        shortScheduler = new ShortTerm();
        memory.printFrame();
        process.printPage();
        cpu1 = new CPU();
        cpu2 = new CPU();
        cpu3 = new CPU();
        cpu4 = new CPU();
        CPUs[0] = cpu1;
        CPUs[1] = cpu2;
        CPUs[2] = cpu3;
        CPUs[3] = cpu4;

        do
        {
            //System.out.println("Started!");

            while(cpu1.status == false)
            {


                for(int i = 0; i < 1; i++)
                {
                    if(CPUs[i].status == false)
                    {
                        ProcessControlVariables tempShort = shortScheduler.start(0);
                        cpu.load(tempShort);
                        memory.removeFrames(tempShort.getUsedFrames());
                    }
                    else
                    {
                        System.out.println("Waiting on available CPU");
                    }
                }
                if(LongTerm.controller.size() == 0 && !end && cpu1.status == false)
                {
                    System.out.println("More jobs!");
                    longScheduler.start();
                    shortScheduler.sort();
                }
                cpu1.status = false;
            }
        }
        while(!longScheduler.controller.isEmpty());
    }

}
