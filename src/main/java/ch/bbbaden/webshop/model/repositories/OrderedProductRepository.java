package ch.bbbaden.webshop.model.repositories;

import ch.bbbaden.webshop.model.entity.CustomerOrder;
import ch.bbbaden.webshop.model.entity.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
}
