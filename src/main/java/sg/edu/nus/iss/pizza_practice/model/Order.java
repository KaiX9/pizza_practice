package sg.edu.nus.iss.pizza_practice.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order implements Serializable {
    
    private String orderId;
    private float totalCost = 0.00f;
    private Pizza pizza;
    private Delivery delivery;

    public Order(Pizza pizza, Delivery delivery) {
        this.pizza = pizza;
        this.delivery = delivery;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public float getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
    public Pizza getPizza() {
        return pizza;
    }
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
    public Delivery getDelivery() {
        return delivery;
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", totalCost=" + totalCost + ", pizza=" + pizza + ", delivery=" + delivery
                + "]";
    }

    public float getPizzaCost() {
        float pizzaCost = 0.00f;
        if (this.getDelivery().isRush()) {
            pizzaCost = this.getTotalCost() - 2;
        } else {
            pizzaCost = this.getTotalCost();
        }
        return pizzaCost;
    }
    
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("orderId", this.getOrderId())
            .add("name", this.getDelivery().getName())
            .add("address", this.getDelivery().getAddress())
            .add("phone", this.getDelivery().getPhone())
            .add("rush", this.getDelivery().isRush())
            .add("comments", this.getDelivery().getComments())
            .add("pizza", this.getPizza().getPizza())
            .add("size", this.getPizza().getSize())
            .add("quantity", this.getPizza().getQuantity())
            .add("total", this.getTotalCost())
            .build();
    }

    public static Order createFromJSON(String json) throws IOException {
        
        InputStream is = new ByteArrayInputStream(json.getBytes());
        JsonReader r = Json.createReader(is);
        JsonObject o = r.readObject();
        Pizza pizza = Pizza.createFromJSON(o);
        Delivery delivery = Delivery.createFromJSON(o);
        Order order = new Order(pizza, delivery);
        order.setOrderId(o.getString("orderId"));
        order.setTotalCost((float)o.getJsonNumber("total").doubleValue());
            
        return order;
    }
    
}
