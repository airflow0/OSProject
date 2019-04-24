public enum Algorithm
{
    PRIORITY(1),
    FIFO(2),
    SJF(3);

    private final int type;
    public int getType(){return type;}

    Algorithm(int type)
    {
        this.type = type;
    }
}
