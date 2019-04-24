public final class Utility
{
    public Utility(){}
    public static String header = "0x";
    public static int DISKMEM = 4096;
    public static int RAMSIZE = 1024;
    public static int PAGESIZE = 64;
    public static int hexToDex(String hex)
    {
        return Integer.parseInt(hex, 16);
    }
    public static String DecToHex(Integer dec)
    {
        return Integer.toHexString(dec);
    }
    public static int binToDecimal(String input)
    {
        return Integer.parseInt(input, 2);
    }
    public static String HexToBin(String hex){

        long parse = Long.parseLong(hex, 16);
        String tmp = Long.toBinaryString(parse);

        while(tmp.length() != 32)
        {
            tmp = "0" + tmp;
        }
        return tmp;
    }
}
