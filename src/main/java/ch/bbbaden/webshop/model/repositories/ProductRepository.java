package ch.bbbaden.webshop.model.repositories;

import ch.bbbaden.webshop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
