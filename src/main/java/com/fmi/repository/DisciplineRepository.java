package com.fmi.repository;

import com.fmi.domain.Discipline;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Discipline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

}
