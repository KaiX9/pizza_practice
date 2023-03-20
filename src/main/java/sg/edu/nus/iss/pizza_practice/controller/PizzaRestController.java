package sg.edu.nus.iss.pizza_practice.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.pizza_practice.model.Order;
import sg.edu.nus.iss.pizza_practice.service.PizzaService;

@RestController
@RequestMapping(path="/order")
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaSvc;

    @GetMapping(path="{orderId}")
    public ResponseEntity<String> getOrderDetails(@PathVariable String orderId) throws IOException {
        Optional<Order> order = pizzaSvc.getOrderByOrderId(orderId);
        if (order.isEmpty()) {
            JsonObject error = Json.createObjectBuilder()
                .add("message", "Order %s not found".formatted(orderId))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
        }
        return ResponseEntity.ok(order.get().toJSON().toString());
    }
    
}
