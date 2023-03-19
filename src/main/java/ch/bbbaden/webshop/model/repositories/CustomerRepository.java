package ch.bbbaden.webshop.model.repositories;

import ch.bbbaden.webshop.model.entity.Category;
import ch.bbbaden.webshop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
