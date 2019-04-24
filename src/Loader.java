import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Loader
{
    public BufferedReader buffer;
    public boolean isInitalized;

    public Loader()
    {
        this.isInitalized = false;
        try
        {
            FileReader _file = new FileReader("DataFile.txt");
            this.buffer = new BufferedReader(_file);
            Start();
        }
        catch (IOException _fileEx)
        {
            _fileEx.printStackTrace();
        }
    }

    public void Start() throws IOException
    {

        String[] dataIndex;
        String data = "";
        int location = 0;
        int address = 0;
        while (!isInitalized)
        {
            data = buffer.readLine();

            if(data != null)
            {
                if(data.contains("JOB"))
                {
                    dataIndex = data.split("\\s+");
                    location = Integer.parseInt(dataIndex[2], 16);
                    insertJob(location, address,  Integer.parseInt(dataIndex[3],16),Integer.parseInt(dataIndex[4], 16));
                }
                else if (data.contains("Data"))
                {
                    dataIndex = data.split("\\s+");

                    if(ProcessHandler.getAlgorithm() != ProcessHandler.ALGORITHM.FIFO)
                        ProcessHandler.applyAlgorithm(ProcessHandler.ALGORITHM.FIFO);
                    Process process = ProcessHandler.getProcess(location);
                    addData(process, address, dataIndex);
                }
                else if (data.contains("END"))
                {
                }
                else
                {
                    Disk.setDiskData(data, address);
                    address++;
                }
            }
            else
                isInitalized = true;
        }
    }
    public static void insertJob(int loc, int address, int size, int priority)
    {
        ProcessHandler.addProcess( new Process(loc, address, size, priority));
    }
    public static void addData(Process proc, int add, String[] data)
    {
        proc.setDataAddress(add);
        proc.setInBuffer(Integer.parseInt(data[2], 16));
        proc.setOutBuffer(Integer.parseInt(data[3], 16));
        proc.setTempBuffer(Integer.parseInt(data[4], 16));
    }

}
