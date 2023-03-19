package ch.bbbaden.webshop.model;

import ch.bbbaden.webshop.cart.ShoppingCart;
import ch.bbbaden.webshop.cart.ShoppingCartItem;
import ch.bbbaden.webshop.controller.records.Order;
import ch.bbbaden.webshop.model.entity.Customer;
import ch.bbbaden.webshop.model.entity.CustomerOrder;
import ch.bbbaden.webshop.model.entity.OrderedProduct;
import ch.bbbaden.webshop.model.repositories.CustomerOrderRepository;
import ch.bbbaden.webshop.model.repositories.CustomerRepository;
import ch.bbbaden.webshop.model.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class OrderService {

    private static final Random RANDOM = new Random();
    @Autowired
    private ShoppingCart cart;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public CustomerOrder placeOrder(Order order) {
        Customer customer = createCustomer(order);
        CustomerOrder customerOrder = createCustomerOrder(order, customer);
        return customerOrder;
    }

    private Customer createCustomer(Order order) {
        Customer customer = new Customer();
        customer.setName(order.name());
        customer.setEmail(order.email());
        customer.setPhone(order.phone());
        customer.setAddress(order.address());
        customer.setCityRegion(order.cityRegion());
        customer.setCcNumber(order.ccnumber());
        customer.setCcNumber(order.ccnumber());
        return customerRepository.saveAndFlush(customer);
    }

    private CustomerOrder createCustomerOrder(Order order, Customer customer) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(customer);
        customerOrder.setAmount(cart.getTotal().add(BigDecimal.valueOf(3)));
        customerOrder.setConfirmationNumber(RANDOM.nextInt(999999999));
        customerOrder.setPromoCode(order.promocode());

        customerOrder.setOrderedProducts(cart.getItemList().stream()
                .map(i -> createOrderedProduct(i, customerOrder))
                .collect(Collectors.toList()));

        return customerOrderRepository.saveAndFlush(customerOrder);
    }

    private OrderedProduct createOrderedProduct(ShoppingCartItem item, CustomerOrder customerOrder) {
        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setProduct(productRepository.getReferenceById(item.getProduct().getId()));
        orderedProduct.setCustomerOrder(customerOrder);
        orderedProduct.setQuantity(item.getQuantity());
        return orderedProduct;
    }
}
