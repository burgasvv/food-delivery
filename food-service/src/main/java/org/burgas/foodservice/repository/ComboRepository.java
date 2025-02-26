package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {

    Optional<Combo> findComboByName(String name);
}
