package com.fmi.web.rest;

import com.fmi.domain.LeadRecord;
import com.fmi.repository.LeadRecordRepository;
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
 * REST controller for managing {@link com.fmi.domain.LeadRecord}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LeadRecordResource {

    private final Logger log = LoggerFactory.getLogger(LeadRecordResource.class);

    private static final String ENTITY_NAME = "leadRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeadRecordRepository leadRecordRepository;

    public LeadRecordResource(LeadRecordRepository leadRecordRepository) {
        this.leadRecordRepository = leadRecordRepository;
    }

    /**
     * {@code POST  /lead-records} : Create a new leadRecord.
     *
     * @param leadRecord the leadRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leadRecord, or with status {@code 400 (Bad Request)} if the leadRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lead-records")
    public ResponseEntity<LeadRecord> createLeadRecord(@RequestBody LeadRecord leadRecord) throws URISyntaxException {
        log.debug("REST request to save LeadRecord : {}", leadRecord);
        if (leadRecord.getId() != null) {
            throw new BadRequestAlertException("A new leadRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeadRecord result = leadRecordRepository.save(leadRecord);
        return ResponseEntity.created(new URI("/api/lead-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lead-records} : Updates an existing leadRecord.
     *
     * @param leadRecord the leadRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leadRecord,
     * or with status {@code 400 (Bad Request)} if the leadRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leadRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lead-records")
    public ResponseEntity<LeadRecord> updateLeadRecord(@RequestBody LeadRecord leadRecord) throws URISyntaxException {
        log.debug("REST request to update LeadRecord : {}", leadRecord);
        if (leadRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeadRecord result = leadRecordRepository.save(leadRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leadRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lead-records} : get all the leadRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leadRecords in body.
     */
    @GetMapping("/lead-records")
    public List<LeadRecord> getAllLeadRecords() {
        log.debug("REST request to get all LeadRecords");
        return leadRecordRepository.findAll();
    }

    /**
     * {@code GET  /lead-records/:id} : get the "id" leadRecord.
     *
     * @param id the id of the leadRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leadRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lead-records/{id}")
    public ResponseEntity<LeadRecord> getLeadRecord(@PathVariable Long id) {
        log.debug("REST request to get LeadRecord : {}", id);
        Optional<LeadRecord> leadRecord = leadRecordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leadRecord);
    }

    /**
     * {@code DELETE  /lead-records/:id} : delete the "id" leadRecord.
     *
     * @param id the id of the leadRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lead-records/{id}")
    public ResponseEntity<Void> deleteLeadRecord(@PathVariable Long id) {
        log.debug("REST request to delete LeadRecord : {}", id);
        leadRecordRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
