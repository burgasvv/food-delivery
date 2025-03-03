package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.FoodSize;
import org.burgas.foodservice.entity.FoodSizePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodSizeRepository extends JpaRepository<FoodSize, FoodSizePK> {

    void deleteFoodSizesByFoodId(Long foodId);

    Optional<FoodSize> findFoodSizeByFoodIdAndSizeId(Long foodId, Long sizeId);
}
