package vitalitus.springtestproject.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import vitalitus.springtestproject.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
