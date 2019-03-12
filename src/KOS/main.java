package KOS;
import java.io.*;
import KOS.Memory.*;


public class Main
{
    public static Memory memory;
    public static ProcessControl process;
    public static Loader load;
    public static void main(String[] args)
    {
        try
        {
            FileReader dataFile = new FileReader("data.txt");
            load = new Loader(dataFile);
            load.load();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
