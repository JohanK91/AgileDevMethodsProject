package Model;
//singleton
public class ActiveUser {
    private static ActiveUser instance = null;
    private String name;
    private int type;


    private ActiveUser(String name, int type)
    {
       this.name = name;
       this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public String getName(){
        return this.name;
    }

    public static ActiveUser getInstance(String name, int type)
    {
        if (instance == null)
            instance = new ActiveUser(name, type);
        return instance;
    }
    public static ActiveUser getInstance()
    {
        return instance;
    }
}
