package com.fmi.web.rest;

import com.fmi.domain.DisciplineRecord;
import com.fmi.repository.DisciplineRecordRepository;
import com.fmi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fmi.domain.DisciplineRecord}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DisciplineRecordResource {

    private final Logger log = LoggerFactory.getLogger(DisciplineRecordResource.class);

    private static final String ENTITY_NAME = "disciplineRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplineRecordRepository disciplineRecordRepository;

    public DisciplineRecordResource(DisciplineRecordRepository disciplineRecordRepository) {
        this.disciplineRecordRepository = disciplineRecordRepository;
    }

    /**
     * {@code POST  /discipline-records} : Create a new disciplineRecord.
     *
     * @param disciplineRecord the disciplineRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplineRecord, or with status {@code 400 (Bad Request)} if the disciplineRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discipline-records")
    public ResponseEntity<DisciplineRecord> createDisciplineRecord(@RequestBody DisciplineRecord disciplineRecord) throws URISyntaxException {
        log.debug("REST request to save DisciplineRecord : {}", disciplineRecord);
        if (disciplineRecord.getId() != null) {
            throw new BadRequestAlertException("A new disciplineRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplineRecord result = disciplineRecordRepository.save(disciplineRecord);
        return ResponseEntity.created(new URI("/api/discipline-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discipline-records} : Updates an existing disciplineRecord.
     *
     * @param disciplineRecord the disciplineRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplineRecord,
     * or with status {@code 400 (Bad Request)} if the disciplineRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplineRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discipline-records")
    public ResponseEntity<DisciplineRecord> updateDisciplineRecord(@RequestBody DisciplineRecord disciplineRecord) throws URISyntaxException {
        log.debug("REST request to update DisciplineRecord : {}", disciplineRecord);
        if (disciplineRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisciplineRecord result = disciplineRecordRepository.save(disciplineRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplineRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discipline-records} : get all the disciplineRecords.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplineRecords in body.
     */
    @GetMapping("/discipline-records")
    public List<DisciplineRecord> getAllDisciplineRecords(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DisciplineRecords");
        return disciplineRecordRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /discipline-records/:id} : get the "id" disciplineRecord.
     *
     * @param id the id of the disciplineRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplineRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discipline-records/{id}")
    public ResponseEntity<DisciplineRecord> getDisciplineRecord(@PathVariable Long id) {
        log.debug("REST request to get DisciplineRecord : {}", id);
        Optional<DisciplineRecord> disciplineRecord = disciplineRecordRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(disciplineRecord);
    }

    /**
     * {@code DELETE  /discipline-records/:id} : delete the "id" disciplineRecord.
     *
     * @param id the id of the disciplineRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discipline-records/{id}")
    public ResponseEntity<Void> deleteDisciplineRecord(@PathVariable Long id) {
        log.debug("REST request to delete DisciplineRecord : {}", id);
        disciplineRecordRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
