package com.fmi.repository;

import com.fmi.domain.ClassType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClassType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassTypeRepository extends JpaRepository<ClassType, Long> {

}
