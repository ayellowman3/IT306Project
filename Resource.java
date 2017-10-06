
public class Resource {

    private static int count = 0;
    private String type;
    private String name;
    private boolean checkedOut;

    public Resource(){
        count++;
    }

    public Resource(String type, String name, boolean checkedOut){
        this();
        this.type = type;
        this.name = name;
        this.checkedOut = checkedOut;
    }

    public static int getCount(){
        return count;
    }

    public String getName(){
        return name;
    }

    public boolean getCheckedOut(){
        return checkedOut;
    }

    public String toString(){
        String message = "Type : " + type + " Name: " + name + " checkedOut: " + checkedOut;
        return message;
    }
}
