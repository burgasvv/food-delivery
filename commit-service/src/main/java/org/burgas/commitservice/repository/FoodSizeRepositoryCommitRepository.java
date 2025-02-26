package org.burgas.commitservice.repository;

import org.burgas.foodservice.entity.FoodSize;
import org.burgas.foodservice.repository.FoodSizeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodSizeRepositoryCommitRepository extends FoodSizeRepository {

    Optional<FoodSize> findFoodSizeByFoodIdAndSizeId(Long foodId, Long sizeId);
}
