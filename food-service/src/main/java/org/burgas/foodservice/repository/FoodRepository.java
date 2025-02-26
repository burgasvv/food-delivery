package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findFoodByName(String name);

    @Query(
            nativeQuery = true,
            value = """
                    select f.* from food f join combo_food cf on f.id = cf.food_id
                                        where cf.combo_id = ?1
                    """
    )
    List<Food> findFoodsByComboId(Long comboId);
}
