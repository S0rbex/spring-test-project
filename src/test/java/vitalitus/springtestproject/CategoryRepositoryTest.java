package vitalitus.springtestproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vitalitus.springtestproject.model.Category;
import vitalitus.springtestproject.repository.category.CategoryRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Save category and find it by ID")
    void save_ValidCategory_ReturnsCategory() {
        Category category = new Category();
        category.setName("Fiction");
        category.setDescription("Fictional books");

        Category savedCategory = categoryRepository.save(category);
        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getId());

        assertTrue(foundCategory.isPresent());
        assertEquals(savedCategory.getName(), foundCategory.get().getName());
    }

    @Test
    @DisplayName("Verify soft delete logic works correctly")
    void delete_SoftDeleteCategory_CategoryNotFound() {
        Category category = new Category();
        category.setName("Science");
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.deleteById(savedCategory.getId());
        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getId());

        assertTrue(foundCategory.isEmpty());
    }
}
