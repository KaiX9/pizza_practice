package sg.edu.nus.iss.pizza_practice.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Delivery implements Serializable {
    
    @NotEmpty(message="Please fill in your name")
    @Size(min=3, message="Name cannot be less than 3 characters")
    private String name;

    @NotEmpty(message="Please fill in your address")
    private String address;

    @NotEmpty(message="Please fill in your phone number")
    @Pattern(regexp="^[0-9]{8,}$", message="Must be a valid phone number")
    private String phone;

    private boolean rush = false;
    
    private String comments;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isRush() {
        return rush;
    }
    public void setRush(boolean rush) {
        this.rush = rush;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    @Override
    public String toString() {
        return "Delivery [name=" + name + ", address=" + address + ", phone=" + phone + ", rush=" + rush + ", comments="
                + comments + "]";
    }

    public static Delivery createFromJSON(JsonObject o) {
        Delivery delivery = new Delivery();
        delivery.setName(o.getString("name"));
        delivery.setAddress(o.getString("address"));
        delivery.setPhone(o.getString("phone"));
        delivery.setRush(o.getBoolean("rush"));
        delivery.setComments(o.getString("comments"));

        return delivery;
    }
    
}
