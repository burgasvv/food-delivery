package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    insert into media(name, content_type, data) VALUES (?1, ?2, ?3)
                    """
    )
    Integer insertIntoMediaWithMultipartFile(String name, String contentType, byte[] data);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    delete from media m where m.id = ?1
                    """
    )
    void deleteMediaInFood(Long mediaId);
}
