package code.Model;

public class ItemType {
    private int id;
    private String name;
    private String description;

    ItemType(int id,String name,String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
    public int getId(){
        return this.id;
    }
}
