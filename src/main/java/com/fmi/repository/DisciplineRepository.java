package com.fmi.repository;

import com.fmi.domain.Discipline;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Discipline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    Optional<Discipline> findFirstByDescription(String description);
}
