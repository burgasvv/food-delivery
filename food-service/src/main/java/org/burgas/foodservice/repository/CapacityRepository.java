package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.Capacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CapacityRepository extends JpaRepository<Capacity, Long> {

    Optional<Capacity> findCapacityByLiters(Float liters);

    @Query(
            nativeQuery = true,
            value = """
                    select c.* from capacity c join food_capacity fc on c.id = fc.capacity_id
                                        where fc.food_id = ?1
                    """
    )
    List<Capacity> findCapacitiesByFoodId(Long foodId);
}
