package com.fmi.web.rest;

import com.fmi.domain.DisciplineType;
import com.fmi.repository.DisciplineTypeRepository;
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
 * REST controller for managing {@link com.fmi.domain.DisciplineType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DisciplineTypeResource {

    private final Logger log = LoggerFactory.getLogger(DisciplineTypeResource.class);

    private static final String ENTITY_NAME = "disciplineType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplineTypeRepository disciplineTypeRepository;

    public DisciplineTypeResource(DisciplineTypeRepository disciplineTypeRepository) {
        this.disciplineTypeRepository = disciplineTypeRepository;
    }

    /**
     * {@code POST  /discipline-types} : Create a new disciplineType.
     *
     * @param disciplineType the disciplineType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplineType, or with status {@code 400 (Bad Request)} if the disciplineType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discipline-types")
    public ResponseEntity<DisciplineType> createDisciplineType(@RequestBody DisciplineType disciplineType) throws URISyntaxException {
        log.debug("REST request to save DisciplineType : {}", disciplineType);
        if (disciplineType.getId() != null) {
            throw new BadRequestAlertException("A new disciplineType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplineType result = disciplineTypeRepository.save(disciplineType);
        return ResponseEntity.created(new URI("/api/discipline-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discipline-types} : Updates an existing disciplineType.
     *
     * @param disciplineType the disciplineType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplineType,
     * or with status {@code 400 (Bad Request)} if the disciplineType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplineType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discipline-types")
    public ResponseEntity<DisciplineType> updateDisciplineType(@RequestBody DisciplineType disciplineType) throws URISyntaxException {
        log.debug("REST request to update DisciplineType : {}", disciplineType);
        if (disciplineType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisciplineType result = disciplineTypeRepository.save(disciplineType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplineType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discipline-types} : get all the disciplineTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplineTypes in body.
     */
    @GetMapping("/discipline-types")
    public List<DisciplineType> getAllDisciplineTypes() {
        log.debug("REST request to get all DisciplineTypes");
        return disciplineTypeRepository.findAll();
    }

    /**
     * {@code GET  /discipline-types/:id} : get the "id" disciplineType.
     *
     * @param id the id of the disciplineType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplineType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discipline-types/{id}")
    public ResponseEntity<DisciplineType> getDisciplineType(@PathVariable Long id) {
        log.debug("REST request to get DisciplineType : {}", id);
        Optional<DisciplineType> disciplineType = disciplineTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disciplineType);
    }

    /**
     * {@code DELETE  /discipline-types/:id} : delete the "id" disciplineType.
     *
     * @param id the id of the disciplineType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discipline-types/{id}")
    public ResponseEntity<Void> deleteDisciplineType(@PathVariable Long id) {
        log.debug("REST request to delete DisciplineType : {}", id);
        disciplineTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
