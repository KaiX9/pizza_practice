package sg.edu.nus.iss.pizza_practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.pizza_practice.model.Delivery;
import sg.edu.nus.iss.pizza_practice.model.Order;
import sg.edu.nus.iss.pizza_practice.model.Pizza;
import sg.edu.nus.iss.pizza_practice.service.PizzaService;

@Controller
@RequestMapping
public class PizzaController {
    
    @Autowired
    private PizzaService pizzaSvc;

    @GetMapping(path={"/", "/index.html"})
    public String orderPizzaForm(Model m, HttpSession s) {
        s.invalidate();

        m.addAttribute("pizza", new Pizza());
        return "index";
    }

    @PostMapping(path="/pizza")
    public String postPizzaSelection(Model m, HttpSession s
        , @ModelAttribute @Valid Pizza pizza, BindingResult binding) {
        if (binding.hasErrors()) {
            return "index";
        }

        List<ObjectError> errors = pizzaSvc.pizzaValidation(pizza);
        if (!errors.isEmpty()) {
            for (ObjectError e : errors) {
                binding.addError(e);
            }
            return "index";
        }

        s.setAttribute("pizza", pizza);
        m.addAttribute("delivery", new Delivery());
        return "delivery";
    }

    @PostMapping(path="/pizza/order")
    public String postOrder(Model m, HttpSession s
        , @ModelAttribute @Valid Delivery delivery, BindingResult binding) {
        if (binding.hasErrors()) {
            return "delivery";
        }
        Pizza pizza = (Pizza) s.getAttribute("pizza");
        Order order = pizzaSvc.savePizzaOrder(pizza, delivery);
        m.addAttribute("order", order);
        return "order";
    }
}
