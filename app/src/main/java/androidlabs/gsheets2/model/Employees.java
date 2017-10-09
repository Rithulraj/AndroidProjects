package androidlabs.gsheets2.model;

/**
 * Created by rithul on 10/1/2017.
 */

public class Employees {
    private String id ,name;

    public Employees(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
