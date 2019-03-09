package KOS;
import java.io.FileReader;
import java.io.IOException;

public class main
{
    public static void main(String[] args)
    {
        try
        {
            FileReader dataFile = new FileReader("Data.txt");

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
