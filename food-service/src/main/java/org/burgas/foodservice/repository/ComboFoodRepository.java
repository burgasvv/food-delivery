package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.ComboFood;
import org.burgas.foodservice.entity.ComboFoodPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComboFoodRepository extends JpaRepository<ComboFood, ComboFoodPK> {

    void deleteComboFoodByComboId(Long comboId);

    Optional<ComboFood> findComboFoodByComboIdAndFoodId(Long comboId, Long foodId);
}
