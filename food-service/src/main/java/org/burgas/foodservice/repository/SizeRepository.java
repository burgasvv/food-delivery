package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select s.* from size s join food_size fs on s.id = fs.size_id
                                        where fs.food_id = ?1
                    """
    )
    List<Size> findSizesByFoodId(Long foodId);

    Optional<Size> findSizeBySize(Long size);
}
