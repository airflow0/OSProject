package KOS;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Scanner;

public class Loader
{
    private static Scanner scan;
    public Loader()
    {
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
        try
        {
            while ((data = buffer.readLine()) != null)
            {
                //System.out.println(data);
                if (data.contains("JOB"))
                {

                    data = data.replace("// JOB ", "");
                    //System.out.println(data);
                    insertJob(data,true);
                    while (data.contains("0x"))
                    {
                        data = buffer.readLine();
                    }
                }
                else if (data.contains("Data"))
                {
                    data = data.replace( "// Data ", "");
                    System.out.println(data);
                    insertJob(data,false);
                    data = buffer.readLine();
                    while (data.contains("0x"))
                    {
                        //System.out.println(data);
                        data = buffer.readLine();
                    }
                }
                else
                {
                    data = buffer.readLine();
                }

            }
            buffer.close();
        }
        catch(IOException ex)
        {

        }
    }
    private static void insertData()
    {

    }
    private static void insertJob(String job, boolean isJob)
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
            System.out.println(jobID + " " + jobSize + " " + jobPriority);
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
            System.out.println(inputBuffer + " " + outputBuffer + " " + tempBuffer);
            Main.process.setMemorySize(tempBuffer + inputBuffer + outputBuffer);
            Main.process.insertMetaData(inputBuffer, tempBuffer,outputBuffer);
        }

    }

}
