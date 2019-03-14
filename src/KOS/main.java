package KOS;
import java.io.*;
import KOS.Memory.*;
import Handler.*;


public class Main
{
    /* public static Memory memory;
    public static ProcessControl process;
    public static Loader load; */ //Not Needed?
    public static void main(String[] args)
    {
        Process process = new ProcessControl(); 
        Memory memory = new Memory(); 
        Loader load = new Loader();
        //LongTerm LongScheduler = new LongTerm();
        for(int i = 0; i < memory.getDiskLength(); i++)
        {
            //System.out.println(memory.getDiskInformation(i));
        }
    }
}
