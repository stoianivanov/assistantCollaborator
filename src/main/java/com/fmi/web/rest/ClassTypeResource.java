package com.fmi.web.rest;

import com.fmi.domain.ClassType;
import com.fmi.repository.ClassTypeRepository;
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
 * REST controller for managing {@link com.fmi.domain.ClassType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClassTypeResource {

    private final Logger log = LoggerFactory.getLogger(ClassTypeResource.class);

    private static final String ENTITY_NAME = "classType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassTypeRepository classTypeRepository;

    public ClassTypeResource(ClassTypeRepository classTypeRepository) {
        this.classTypeRepository = classTypeRepository;
    }

    /**
     * {@code POST  /class-types} : Create a new classType.
     *
     * @param classType the classType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classType, or with status {@code 400 (Bad Request)} if the classType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-types")
    public ResponseEntity<ClassType> createClassType(@RequestBody ClassType classType) throws URISyntaxException {
        log.debug("REST request to save ClassType : {}", classType);
        if (classType.getId() != null) {
            throw new BadRequestAlertException("A new classType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassType result = classTypeRepository.save(classType);
        return ResponseEntity.created(new URI("/api/class-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-types} : Updates an existing classType.
     *
     * @param classType the classType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classType,
     * or with status {@code 400 (Bad Request)} if the classType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-types")
    public ResponseEntity<ClassType> updateClassType(@RequestBody ClassType classType) throws URISyntaxException {
        log.debug("REST request to update ClassType : {}", classType);
        if (classType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassType result = classTypeRepository.save(classType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-types} : get all the classTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classTypes in body.
     */
    @GetMapping("/class-types")
    public List<ClassType> getAllClassTypes() {
        log.debug("REST request to get all ClassTypes");
        return classTypeRepository.findAll();
    }

    /**
     * {@code GET  /class-types/:id} : get the "id" classType.
     *
     * @param id the id of the classType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-types/{id}")
    public ResponseEntity<ClassType> getClassType(@PathVariable Long id) {
        log.debug("REST request to get ClassType : {}", id);
        Optional<ClassType> classType = classTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classType);
    }

    /**
     * {@code DELETE  /class-types/:id} : delete the "id" classType.
     *
     * @param id the id of the classType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-types/{id}")
    public ResponseEntity<Void> deleteClassType(@PathVariable Long id) {
        log.debug("REST request to delete ClassType : {}", id);
        classTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
