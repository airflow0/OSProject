package KOS;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Loader
{
    public static int counter;
    public static int address;
    public void load(FileReader dataFile)
    {
        try
        {
            BufferedReader dataFileInput = new BufferedReader(dataFile);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void readFile(BufferedReader bf)
    {
        counter = 0;
        address = 0;
        try
        {
            String jobLine = bf.readLine();
            while ( jobLine.length() > 0) // While number of lines in text is greater than 0
            {
                if(jobLine.contains("JOB"))
                {

                }
            }
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }
}
