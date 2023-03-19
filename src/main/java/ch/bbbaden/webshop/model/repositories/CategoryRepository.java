package ch.bbbaden.webshop.model.repositories;

import ch.bbbaden.webshop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
