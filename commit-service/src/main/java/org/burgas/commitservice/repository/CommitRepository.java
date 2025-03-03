package org.burgas.commitservice.repository;

import org.burgas.commitservice.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select c.* from commit c where token_id = ?1
                    """
    )
    Optional<Commit> findCommitByTokenId(Long tokenId);

    Optional<Commit> findCommitByIdentityId(Long identityId);

    Optional<Commit> findCommitByTokenIdAndClosed(Long tokenId, Boolean closed);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    insert into token(name, value) VALUES (?1, ?2)
                    """
    )
    Integer insertIntoTokenFromCommit(String name, String value);
}
