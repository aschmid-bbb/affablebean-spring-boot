package ch.bbbaden.webshop.controller;

import ch.bbbaden.webshop.controller.records.Login;
import ch.bbbaden.webshop.model.entity.Customer;
import ch.bbbaden.webshop.model.entity.CustomerOrder;
import ch.bbbaden.webshop.model.repositories.CustomerOrderRepository;
import ch.bbbaden.webshop.model.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Controller
@SessionScope
public class AdminController {
    private boolean isLoggedIn = false;

    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerOrderRepository orderRepository;

    @GetMapping({"/admin", "/admin/"})
    public String index() {
        return isLoggedIn ? "redirect:/admin/customers" : "redirect:/admin/login";
    }

    @GetMapping({"/admin/login"})
    public String login(Model model) {
        return "admin/login";
    }

    @PostMapping({"/admin/login"})
    public String login(Login login, Model model) {
        if (login.username().equals(username) && login.password().equals(password)) {
            isLoggedIn = true;
            return "redirect:/admin/customers";
        } else {
            isLoggedIn = false;
            return "redirect:/admin/login";
        }
    }

    @GetMapping({"/admin/logout"})
    public String logout() {
        isLoggedIn = false;
        return "redirect:/admin/login";
    }

    @GetMapping({"admin/customers"})
    public String customerList(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return setContent(model, "customer_list");
    }

    @GetMapping({"admin/customer/{id}"})
    public String customerDetail(@PathVariable Long id, Model model) {
        Customer customer = customerRepository.getReferenceById(id);
        model.addAttribute("customer", customer);
        return setContent(model, "customer_detail");
    }

    @GetMapping({"admin/orders"})
    public String orderList(Model model) {
        List<CustomerOrder> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return setContent(model, "order_list");
    }

    @GetMapping({"admin/order/{id}"})
    public String orderDetail(@PathVariable Long id, Model model) {
        CustomerOrder order = orderRepository.getReferenceById(id);
        model.addAttribute("order", order);
        return setContent(model, "order_detail");
    }

    private String setContent(Model model, String content) {
        if (!isLoggedIn) {
            return "redirect:/admin/login";
        }
        model.addAttribute("content", content);
        return "admin/index";
    }
}
