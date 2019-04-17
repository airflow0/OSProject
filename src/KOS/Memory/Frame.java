package KOS.Memory;

public class Frame
{
    private int id, pNum;
    private Boolean alloc;

    public Frame()
    {
        this.alloc = false;
        this.pNum = 0;
    }
    public void update(int p, int id, Boolean alloc)
    {
        this.pNum = p;
        this.id = id;
        this.alloc = alloc;
    }
    public String toString()
    {
        return "\tJobID: " + this.id + "\tPage Num:" + this.pNum + "\tAlloc? " + this.alloc;
    }
}
