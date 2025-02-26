package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select i.* from ingredient i join food_ingredient fi on i.id = fi.ingredient_id
                                        where fi.food_id = ?1
                    """
    )
    List<Ingredient> findIngredientsByFoodId(Long foodId);

    Optional<Ingredient> findIngredientByName(String name);
}
