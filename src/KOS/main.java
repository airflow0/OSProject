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

    public static void main(String[] args)
    {
        process = new ProcessControl();
        memory = new Memory();
        load = new Loader();
        while( end == false)
        {
            LongTerm LongScheduler = new LongTerm();
        }

    }
}
