package KOS;

import java.io.*;
import java.math.BigInteger;

public class Utility
{
    public static int RAMSIZE = 1024;
    public static int DISKSIZE = 2048;

    //bit selection for testing.
    public static int BIT4 = 4;
    public static int BIT8 = 8;
    public static int BIT16 = 16;
    public static int BIT32 = 32;
    public static BufferedWriter writer;
    Utility()
    {
        try
        {
            writer = new BufferedWriter(new FileWriter("RAMDUMP.txt", true));
        }
        catch (Exception ex)
        {

        }
    }
    public static void write(String a)
    {
        try
        {
            writer.write(a);
            writer.newLine();
            writer.flush();
        }
        catch (Exception ex)
        {

        }
    }

    public static String changeToBinary(int location)
    {

        String diskData = Main.memory.getDiskInformation(location);
        diskData = diskData.replace("0x", "");
        String binary = new BigInteger(diskData, 16).toString(2);
        //System.out.println("DiskData: " + diskData);
        if(binary.length() < 32)
        {
            int binaryMatch = 32 - binary.length();
            for(int i = 0; i < binaryMatch; i++)
            {
                binary = "0" + binary;
            }
        }
        Utility.write("[LOCATION]: "+ location +",[BINARY DATA]: " + binary);
        return binary;
    }
    public static String hexToBinary(String s)
    {
        return new BigInteger(s,16).toString(2);
    }
}