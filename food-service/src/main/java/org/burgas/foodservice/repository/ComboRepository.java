package org.burgas.foodservice.repository;

import org.burgas.foodservice.entity.Combo;
import org.burgas.foodservice.dto.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {

    Optional<Combo> findComboByName(String name);

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
    void deleteMediaInCombo(Long mediaId);
}
