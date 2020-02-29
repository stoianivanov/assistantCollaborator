package com.fmi.repository;

import com.fmi.domain.LeadRecord;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LeadRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadRecordRepository extends JpaRepository<LeadRecord, Long> {

}
