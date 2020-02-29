package com.fmi.repository;

import com.fmi.domain.DisciplineType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DisciplineType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisciplineTypeRepository extends JpaRepository<DisciplineType, Long> {

}
