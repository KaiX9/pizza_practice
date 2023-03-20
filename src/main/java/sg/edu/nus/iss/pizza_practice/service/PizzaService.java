package sg.edu.nus.iss.pizza_practice.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import sg.edu.nus.iss.pizza_practice.model.Delivery;
import sg.edu.nus.iss.pizza_practice.model.Order;
import sg.edu.nus.iss.pizza_practice.model.Pizza;
import sg.edu.nus.iss.pizza_practice.repository.PizzaRepository;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepo;
    
    public static final String[] PIZZA_TYPES = {"bella", "margherita"
        , "marinara", "spianatacalabrese", "trioformaggio"};

    public static final String[] PIZZA_SIZES = {"sm", "md", "lg"};

    private final Set<String> pizzaTypes;
    private final Set<String> pizzaSizes;

    public PizzaService() {
        pizzaTypes = new HashSet<String>(Arrays.asList(PIZZA_TYPES));
        pizzaSizes = new HashSet<String>(Arrays.asList(PIZZA_SIZES));
    }

    public List<ObjectError> pizzaValidation(Pizza pizza) {
        List<ObjectError> errors = new LinkedList<>();

        if (!pizzaTypes.contains(pizza.getPizza().toLowerCase())) {
            FieldError e = new FieldError("pizza", "pizza", "%s pizza is not available".formatted(pizza.getPizza()));
            errors.add(e);
        }

        if (!pizzaSizes.contains(pizza.getSize().toLowerCase())) {
            FieldError e = new FieldError("pizza", "size", "%s size is not available".formatted(pizza.getSize()));
            errors.add(e);
        }
        return errors;
    }

    public String generateId() {
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        return orderId;
    }

    public Order createPizzaOrder(Pizza pizza, Delivery delivery) {
        Order order = new Order(pizza, delivery);
        order.setOrderId(generateId());
        return order;
    }

    public float calculateCost(Order order) {
        float totalCost = 0.00f;
        if (order.getPizza().getPizza().contains("bella") 
        || order.getPizza().getPizza().contains("marinara")
        || order.getPizza().getPizza().contains("spianatacalabrese")) {
            totalCost += 30;
        } else if (order.getPizza().getPizza().contains("margherita")) {
            totalCost += 22;
        } else if (order.getPizza().getPizza().contains("trioformaggio")) {
            totalCost += 25;
        }

        if (order.getPizza().getSize().contains("md")) {
            totalCost *= 1.2;
        } else if (order.getPizza().getSize().contains("lg")) {
            totalCost *= 1.5;
        }

        totalCost *= order.getPizza().getQuantity();

        if (order.getDelivery().isRush()) {
            totalCost += 2;
        }

        order.setTotalCost(totalCost);
        return totalCost;
    }

    public Order savePizzaOrder(Pizza pizza, Delivery delivery) {
        Order order = createPizzaOrder(pizza, delivery);
        calculateCost(order);
        pizzaRepo.savePizzaOrder(order);
        return order;
    }

    public Optional<Order> getOrderByOrderId(String orderId) throws IOException {
        return pizzaRepo.getOrderDetails(orderId);
    }
}
