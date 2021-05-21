package code.Model;

import java.util.ArrayList;

public class DriverTaskList
{
    private int myIndexIterator;
    private final int myDriverID;
    private ArrayList<DriverTask> myTasks = new ArrayList<>();
    private DriverTask myActiveTask = null;

    public DriverTaskList(int aDriverID)
    {
        myDriverID = aDriverID;
        myIndexIterator = 0;

        refresh();
    }

    public ArrayList<DriverTask> getDriverTasks()
    {
        return myTasks;
    }

    public void clearCharities()
    {
        AppManager.getInstance().getDb().removeCharitiesFromDriverTaskList(myDriverID);
        refresh();
    }

    public void fixCharities()
    {
        refresh();

        DbBridge dbBridge = AppManager.getInstance().getDb();

        ArrayList<Integer> charities = new ArrayList<>();

        dbBridge.removeCharitiesFromDriverTaskList(myDriverID);

        for (DriverTask task : myTasks)
        {
            if (task.isTask())
            {
                int charityId = dbBridge.getCharityIdFromTask(task.getTaskID());
                if (!charities.contains(charityId))
                {
                    charities.add(charityId);
                }
            }
        }

        for (Integer charityId : charities)
        {
            dbBridge.addCharityToDriverTaskList(myDriverID, charityId);
        }

        refresh();
    }

    public void AddTask(int anId)
    {
        AppManager.getInstance().getDb().addTaskToDriverTaskList(myDriverID, anId);
    }

    public void refresh()
    {
        myTasks = AppManager.getInstance().getDb().getDriverTaskList(myDriverID);
        myActiveTask = null;

        if (myTasks.size() > myIndexIterator)
            myActiveTask = myTasks.get(myIndexIterator);
    }

    public void completeTaskAndAdvance()
    {
        if (myActiveTask != null && myActiveTask.getTaskID() != -1)
        {
            AppManager.getInstance().getDb().changeTaskStatus(myActiveTask.getTaskID(), TaskStatus.ArrivedToCharity);
        }

        advanceIterator();
    }

    public void close()
    {
        AppManager.getInstance().getDb().emptyDriverTaskList(myDriverID);
    }

    public boolean hasTaskLeft()
    {
        return myTasks.size() > myIndexIterator;
    }

    public DriverTask getActiveTask()
    {
        if (myTasks.size() > 0 && myTasks.size() > myIndexIterator)
            return myTasks.get(myIndexIterator);

        return null;
    }

    public void advanceIterator()
    {
        ++myIndexIterator;
        myActiveTask = null;

        if (myTasks.size() > myIndexIterator)
            myActiveTask = myTasks.get(myIndexIterator);
    }
}