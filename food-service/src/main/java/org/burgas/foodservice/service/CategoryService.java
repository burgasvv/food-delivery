package org.burgas.foodservice.service;

import org.burgas.foodservice.entity.Category;
import org.burgas.foodservice.exception.CategoryNotFoundException;
import org.burgas.foodservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.burgas.foodservice.entity.CategoryMessage.CATEGORY_DELETED;
import static org.burgas.foodservice.entity.CategoryMessage.CATEGORY_NOT_FOUND;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseGet(Category::new);
    }

    public Category findByName(String name) {
        return categoryRepository.findCategoryByName(name).orElseGet(Category::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(Category category) {
        return categoryRepository.save(category).getId();
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(
                        category -> {
                            categoryRepository.deleteById(category.getId());
                            return String.format(CATEGORY_DELETED.getMessage(), category.getId());
                        }
                )
                .orElseThrow(
                        () -> new CategoryNotFoundException(
                                String.format(CATEGORY_NOT_FOUND.getMessage(), categoryId))
                );
    }
}
