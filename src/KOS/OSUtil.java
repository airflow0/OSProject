package KOS;

import KOS.Main;
public class OSUtil
{
    public static String changeToBinary(int location)
    {
        int difference = 0;
        String readDisk = Main.memory.getDiskInformation(location);
        long data = Long.parseLong(readDisk.replace("0x", ""), 16);
        readDisk = Long.toBinaryString(data);
        if(checkData(readDisk))
        {
            difference = 32 - readDisk.length();
            for(int i = 0; i < difference; i++)
            {
                readDisk = "0" + readDisk;
            }
        }
        return readDisk;
    }
    public static boolean checkData(String input)
    {
        int a = input.length();
        if (a < 32)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
