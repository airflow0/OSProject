package KOS;

import java.io.*;
public class Loader
{
    private static FileReader _file;
    public Loader(FileReader file)
    {
        this._file = file;
    }
    public void load()
    {
        try (BufferedReader read = new BufferedReader(_file))
        {
            getDataFromFile(read);
        }
        catch (Exception ex)
        {
            System.out.println("Buffered Reader Exception; Class: Loader; Method: Load");
        }
    }
    //TODO: Seperate data from Data file to Driver Memory and Disk
    private static void getDataFromFile(BufferedReader buffer)
    {
        String data;
        try
        {

            while((data = buffer.readLine()) != null)
            {
                data = buffer.readLine();
                if(data.contains("JOB"))
                {
                    System.out.println(data);
                    data = buffer.readLine();
                    while(data.contains("0x"))
                    {
                        System.out.println(data);
                        data = buffer.readLine();
                    }
                }
                else if (data.contains("Data"))
                {
                    System.out.println(data);
                    data = buffer.readLine();
                    while(data.contains("0x"))
                    {
                        System.out.println(data);
                        data = buffer.readLine();
                    }
                }
                else
                {
                    data = buffer.readLine();
                }
            }

        }
        catch (NullPointerException ex)
        {
            System.out.println("Null Pointer Exception; Class: Loader; Method: getDataFromFile");
        }
        catch(IOException ex)
        {
            System.out.println("IO Exception; Class: Loader; Method: getDataFromFile");
        }
    }
    public void seperateJob(String job, int jobDataType)
    {
        //Seperates job data type and insert into Driver
    }
    public void seperateData()
    {
        //Seperates data and inserts into disk
    }

}
