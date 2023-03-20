package sg.edu.nus.iss.pizza_practice.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza implements Serializable {
    
    @NotNull(message="Please select a pizza type")
    private String pizza;

    @NotNull(message="Please select a size")
    private String size;
    
    @Min(value=1, message="Please order at least 1 pizza")
    @Max(value=10, message="You cannot order more than 10 pizzas")
    private int quantity;

    public String getPizza() {
        return pizza;
    }
    public void setPizza(String pizza) {
        this.pizza = pizza;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "Pizza [pizza=" + pizza + ", size=" + size + ", quantity=" + quantity + "]";
    }

    public static Pizza createFromJSON(JsonObject o) {
        Pizza pizza = new Pizza();
        pizza.setPizza(o.getString("pizza"));
        pizza.setQuantity(o.getInt("quantity"));
        pizza.setSize(o.getString("size"));

        return pizza;
    }
}
