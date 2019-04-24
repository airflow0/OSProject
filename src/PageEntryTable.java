public class PageEntryTable
{
    public String[] page;
    public int jmp =0;
    public int pageSize =0;
    PageEntryTable(int mem)
    {
        page = new String[mem];
        pageSize = mem;
    }
    public String getData(int loc)
    {
        return page[loc];
    }
    public void setPage(int difference, String input)
    {
        page[difference]=input;
    }
    public void insertPage(String input)
    {
        if(!checkPageSize())
        {
            page[jmp]=input;
            jmp++;
        }
    }
    public boolean checkPageSize()
    {
        if(pageSize > jmp)
        {
            return false;
        }
        else{
            return true;
        }
    }
}
