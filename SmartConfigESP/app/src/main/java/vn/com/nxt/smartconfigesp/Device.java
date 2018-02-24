package vn.com.nxt.smartconfigesp;

/**
 * Created by AutoUsr on 10/02/2018.
 */

public class Device {
    private String name;
    private String description;
    private int color;

    public Device(String name, String description, int color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String phoneNumber) {
        this.description = phoneNumber;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}