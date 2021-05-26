package code.Model;

public class DriverTask
{
    private final int myTaskID;
    private final int myCharityID;
    private final int myIndex;

    public DriverTask(int aTaskID, int aCharityID, int anIndex)
    {
        if ((aTaskID > -1) == (aCharityID > -1))
        {
            System.out.printf("A DriverTask can only be a task, or a charity. Not both. TaskID: %d, CharityID: %d!\n", aTaskID, aCharityID);
        }

        if ((aTaskID == -1) == (aCharityID == -1))
        {
            System.out.print("A DriverTask must have one task, or charity ID!\n");
        }

        myTaskID = aTaskID;
        myCharityID = aCharityID;
        myIndex = anIndex;
    }

    public int getTaskID()
    {
        return myTaskID;
    }

    public int getCharityID()
    {
        return myCharityID;
    }

    public int getIndex()
    {
        return myIndex;
    }

    public String getAddress()
    {
        if (myTaskID >= 0)
            return AppManager.getInstance().getDb().getTaskAddress(myTaskID);

        if (myCharityID >= 0)
            return AppManager.getInstance().getDb().getCharityAddress(myCharityID);

        return null;
    }

    public boolean isCharity()
    {
        return myCharityID != -1;
    }

    public boolean isTask()
    {
        return myTaskID != -1;
    }
}