import java.util.ArrayList;
import java.util.Arrays;

public final class RandomMemory
{
    private static PageEntryTable page[];
    private static Integer[] table;

    public RandomMemory()
    {
        int size = Utility.RAMSIZE / Utility.PAGESIZE;
        page = new PageEntryTable[size];
        table = new Integer[size];
        createTable(table);
    }
    public static int getlocation(int loc)
    {
        return table[loc];
    }
    public static void writeRam(int loc, int off, String value)
    {
        page[loc].setPage(off, value);
    }
    public static String readRam(int loc, int off)
    {
        return page[loc].getData(off);
    }
    public static int getNextAvailableLocation()
    {
        return Arrays.asList(table).indexOf(-1);
    }
    public static boolean memoryLimit()
    {
        return Arrays.asList(page).indexOf(null) == -1;
    }
    public static boolean checkPageAvailable(int num)
    {
        int page = 0;
        for (int i = 0; i < table.length; i++)
        {
            if (table[i] == -1)
            {
                page++;
            }
        }
        return (page >= num);
    }
    public static ArrayList<Integer> fill(int pg)
    {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int filled = 0;
        if (!checkPageAvailable(pg))
        {
            return temp;
        }

        for (int i = 0; i < table.length && temp.size() < pg; i++)
        {
            if (table[i] == -1)
            {
                temp.add(i);
            }
        }

        for (int i = 0; i < page.length && filled < pg; i++)
        {
            if (page[i] == null)
            {
                table[temp.get(filled)] = i;
                filled++;
            }
        }
        return temp;
    }

    public static void removeFromProcess(Process process)
    {
        if (process.getStatus() == Process.STATE.PROCEND)
        {
            for (int i = 0; i < process.getFilledPages().size(); i++)
            {
                int address = process.getFilledPages().get(i);
                int location = table[address];
                page[location] = null;
                table[address] = -1;
            }
        }
    }
    public static void createTable(Integer[] arr)
    {
        Arrays.fill(table, -1);
    }
    public static void fillPage(int location, String[] data) throws Exception
    {
        int i = 0;
        page[location] = new PageEntryTable(Utility.PAGESIZE);
        while (!page[location].checkPageSize() && i < data.length)
        {
            page[location].insertPage(data[i]);
            i++;
        }
        if (i < data.length)
        {
            System.out.println("ERROR RANDOM MEMORY 0x01");
        }
    }
}