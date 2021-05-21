package code.Model;

public class ItemType {
    private int id;
    private String name;
    private String description;

    ItemType(int anId,String name,String description)
    {
        id = id;
        this.name=name;
        this.description =description;
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
