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
    public static boolean status = false;

    public ProcessControlVariables cpu_PCB;

    public static void main(String[] args)
    {
        Utility UA = new Utility();
        process = new ProcessControl();
        memory = new Memory();
        load = new Loader();
        CPU cpu = new CPU();
        ProcessControlVariables temp;
        LongTerm scheduler = new LongTerm();
        while( end == false)
        {
            if(!scheduler.controller.isEmpty())
            {
                for(int i = 0; i < scheduler.controller.size(); i++)
                {
                    //cpu.cpu_Start(scheduler.controller.get(0));
                }
                scheduler.start();

            }
            else
            {
                System.out.println("Scheduler is Empty!");
                System.exit(-1);
            }
        }
    }

}
