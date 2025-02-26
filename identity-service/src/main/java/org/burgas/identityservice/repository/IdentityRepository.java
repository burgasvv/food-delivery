package org.burgas.identityservice.repository;

import org.burgas.identityservice.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long> {

    Optional<Identity> findIdentityByEmail(String email);

    Optional<Identity> findIdentityByUsername(String username);

    @Query(
            nativeQuery = true,
            value = """
                    select a.name from authority a join identity i on a.id = i.authority_id
                                        where i.id = ?1
                    """
    )
    String findAuthorityNameByIdentityId(Long identityId);

    @Query(
            nativeQuery = true,
            value = """
                    select i.authority_id from identity i where i.id = ?1
                    """
    )
    Long findAuthorityIdByIdentityId(Long identityId);
}
