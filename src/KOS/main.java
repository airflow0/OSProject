package KOS;
import java.io.*;


public class Main
{
    public static void main(String[] args)
    {
        try
        {
            FileReader dataFile = new FileReader("data.txt");
            Loader.load(dataFile);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
