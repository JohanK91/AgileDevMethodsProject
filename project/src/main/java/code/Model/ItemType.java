package code.Model;

public class ItemType {
    private int myId;
    private String myName;
    private String myDescription;

    ItemType(int anId,String name,String description)
    {
        myId = anId;
        myName=name;
        myDescription =description;
    }

    public String getName(){
        return myName;
    }

    public String getDescription() {
        return myDescription;
    }
    public int getId(){
        return myId;
    }
}
