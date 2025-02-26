package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.FoodIngredient;
import org.burgas.foodservice.entity.FoodIngredientPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodIngredientRepository extends JpaRepository<FoodIngredient, FoodIngredientPK> {

    void deleteFoodIngredientsByFoodId(Long foodId);
}
