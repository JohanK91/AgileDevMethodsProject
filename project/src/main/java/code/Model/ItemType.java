package code.Model;

public class ItemType {
    private int myId;
    private String myName;
    private String mydescription;

    ItemType(int anId,String name,String description)
    {
        myId = anId;
        myName=name;
        mydescription=description;
    }

    public String getName(){
        return myName;
    }

    public String getDescription() {
        return mydescription;
    }
    public int getId(){
        return myId;
    }
}
