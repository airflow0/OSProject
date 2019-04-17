package KOS;

public class Page
{
    public boolean isValid;
    public int pNum, fNum;

    public Page(int pNum)
    {
        this.pNum = pNum;
        this.fNum = 0;
        this.isValid = false;
    }
    public void updatePage(int frame, boolean isValid)
    {
        this.fNum = frame;
        this.isValid = isValid;
    }
    public int getPage()
    {
        return this.pNum;
    }
    public int getfNum()
    {
        return this.fNum;
    }
    public boolean getIsValid()
    {
        if(isValid == false)
        {
            //System.out.println("Page Validation is false!");
        }
        return this.isValid;
    }
}

