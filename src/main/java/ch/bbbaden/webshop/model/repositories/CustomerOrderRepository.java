package ch.bbbaden.webshop.model.repositories;

import ch.bbbaden.webshop.model.entity.Customer;
import ch.bbbaden.webshop.model.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
