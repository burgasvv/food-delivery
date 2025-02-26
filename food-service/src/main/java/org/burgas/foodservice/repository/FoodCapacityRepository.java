package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.FoodCapacity;
import org.burgas.foodservice.entity.FoodCapacityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCapacityRepository extends JpaRepository<FoodCapacity, FoodCapacityPK> {

    void deleteFoodCapacitiesByFoodId(Long foodId);

    Optional<FoodCapacity> findFoodCapacityByCapacityIdAndFoodId(Long capacityId, Long foodId);
}
