package com.fmi.repository;

import com.fmi.domain.DisciplineRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DisciplineRecord entity.
 */
@Repository
public interface DisciplineRecordRepository extends JpaRepository<DisciplineRecord, Long> {

    @Query(value = "select distinct disciplineRecord from DisciplineRecord disciplineRecord left join fetch disciplineRecord.lectos left join fetch disciplineRecord.directions",
        countQuery = "select count(distinct disciplineRecord) from DisciplineRecord disciplineRecord")
    Page<DisciplineRecord> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct disciplineRecord from DisciplineRecord disciplineRecord left join fetch disciplineRecord.lectos left join fetch disciplineRecord.directions")
    List<DisciplineRecord> findAllWithEagerRelationships();

    @Query("select disciplineRecord from DisciplineRecord disciplineRecord left join fetch disciplineRecord.lectos left join fetch disciplineRecord.directions where disciplineRecord.id =:id")
    Optional<DisciplineRecord> findOneWithEagerRelationships(@Param("id") Long id);

}
