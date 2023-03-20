package sg.edu.nus.iss.pizza_practice.repository;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.pizza_practice.model.Order;

@Repository
public class PizzaRepository {
    
    @Autowired @Qualifier("pizza")
    private RedisTemplate<String, String> template;

    public void savePizzaOrder(Order order) {
        this.template.opsForValue().set(order.getOrderId(), order.toJSON().toString());
    }

    public Optional<Order> getOrderDetails(String orderId) throws IOException {
        String json = template.opsForValue().get(orderId);
        if ((json == null || json.trim().length() <= 0)) {
            return Optional.empty();
        }
        return Optional.of(Order.createFromJSON(json));
    }
}
