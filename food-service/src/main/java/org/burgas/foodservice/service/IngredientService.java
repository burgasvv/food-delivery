package org.burgas.foodservice.service;

import org.burgas.foodservice.entity.Ingredient;
import org.burgas.foodservice.exception.IngredientNotFoundException;
import org.burgas.foodservice.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.burgas.foodservice.entity.IngredientMessage.INGREDIENT_DELETED;
import static org.burgas.foodservice.entity.IngredientMessage.INGREDIENT_NOT_FOUND;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public List<Ingredient> findByFoodId(Long foodId) {
        return ingredientRepository.findIngredientsByFoodId(foodId);
    }

    public Ingredient findById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseGet(Ingredient::new);
    }

    public Ingredient findByName(String name) {
        return ingredientRepository.findIngredientByName(name).orElseGet(Ingredient::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(Ingredient ingredient) {
        return ingredientRepository.save(ingredient).getId();
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .map(
                        ingredient -> {
                            ingredientRepository.deleteById(ingredient.getId());
                            return String.format(INGREDIENT_DELETED.getMessage(), ingredient.getId());
                        }
                )
                .orElseThrow(
                        () -> new IngredientNotFoundException(
                                String.format(INGREDIENT_NOT_FOUND.getMessage(), ingredientId))
                );
    }
}
