package org.burgas.commitservice.repository;

import org.burgas.commitservice.entity.Choose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChooseRepository extends JpaRepository<Choose, Long> {

    List<Choose> findChoosesByCommitId(Long commitId);
}
