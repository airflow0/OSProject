package KOS;

import java.math.BigInteger;

public class Utility
{
    public static String changeToBinary(int location)
    {
        String diskData = Main.memory.getDiskInformation(location);
        diskData = diskData.replace("0x", "");
        String binary = new BigInteger(diskData, 16).toString(2);
        if(binary.length() < 32)
        {
            int binaryMatch = 32 - binary.length();
            for(int i = 0; i < binaryMatch; i++)
            {
                binary = "0" + binary;
            }
        }
        return binary;
    }
    public static String hexToBinary(String s)
    {
        return new BigInteger(s,16).toString(2);
    }


}