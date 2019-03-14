package KOS;

import java.io.*;
import java.util.Scanner;

public class Loader
{
    public static int addressLocation;
    public static Scanner scan;
    public static int location;
    public Loader()
    {
        addressLocation = 0;
        location = 0;
        try
        {
            FileReader _file = new FileReader("Data.txt");
            BufferedReader buffer = new BufferedReader(_file);
            getDataFromFile(buffer);
        }
        catch (FileNotFoundException _fileEx)
        {
            _fileEx.printStackTrace();
        }
    }
    private static void getDataFromFile(BufferedReader buffer)
    {
        String data;
        location = 0;
        try
        {
            while ((data = buffer.readLine()) != null)
            {
                if(data.contains("JOB"))
                {
                    data = data.replace("// JOB ", "");
                    insertJob(data, true);
                }
                else if(data.contains("Data"))
                {
                    data = data.replace("// Data ", "");
                    insertJob(data, false);
                }
                else if (data.contains("0x"))
                {
                    insertData(data, location);
                    location = location + 1;
                }
            }
            buffer.close();
        }
        catch(IOException ex)
        {

        }
    }
    public static void insertData(String data, int location)
    {
        Main.memory.setDiskInformation(data, location);
    }
    public static void insertJob(String job, boolean isJob)
    {
        scan = new Scanner(job);
        if(isJob)
        {
            int jobID = 0, jobSize = 0, jobPriority = 0;
            while(scan.hasNext())
            {
                jobID = Integer.parseInt(scan.next(), 16);
                jobSize = Integer.parseInt(scan.next(), 16);
                jobPriority = Integer.parseInt(scan.next(), 16);
                Main.process.insertJob(jobID, 0, jobSize, jobPriority);
            }
            scan.close();
            //System.out.println("Job: " + jobID + " " + jobSize + " " + jobPriority);
        }
        else
        {
            int inputBuffer = 0, outputBuffer = 0, tempBuffer = 0;
            while(scan.hasNext())
            {
                inputBuffer = Integer.parseInt(scan.next(), 16);
                outputBuffer = Integer.parseInt(scan.next(), 16);
                tempBuffer = Integer.parseInt(scan.next(), 16);
            }
            scan.close();
            //System.out.println("Meta Data: " + inputBuffer + " " + outputBuffer + " " + tempBuffer);
            int memorySize = tempBuffer + inputBuffer + outputBuffer;
            Main.process.setMemorySize(memorySize);
            Main.process.insertMetaData(inputBuffer, tempBuffer,outputBuffer);
        }

    }


}
