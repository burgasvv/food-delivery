package org.burgas.commitservice.repository;

import org.burgas.commitservice.entity.ChooseIngredient;
import org.burgas.commitservice.entity.ChooseIngredientPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChooseIngredientRepository extends JpaRepository<ChooseIngredient, ChooseIngredientPK> {

    void deleteChooseIngredientsByChooseId(Long chooseId);

    List<ChooseIngredient> findChooseIngredientsByChooseId(Long chooseId);
}
