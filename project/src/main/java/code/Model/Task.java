package code.Model;

public class Task
{
    private final int myId;

    Task(int anId)
    {
        myId = anId;
    }

    public String getAddress()
    {
        return AppManager.getInstance().getDb().getTaskAddress(myId);
    }

    public int getId()
    {
        return myId;
    }
}