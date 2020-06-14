package com.fmi.web.rest;

import com.fmi.domain.Identity;
import com.fmi.domain.dto.RecordDto;
import com.fmi.repository.IdentityRepository;
import com.fmi.service.IdentityService;
import com.fmi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fmi.domain.Identity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IdentityResource {

    private final Logger log = LoggerFactory.getLogger(IdentityResource.class);

    private static final String ENTITY_NAME = "identity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdentityRepository identityRepository;


    private final IdentityService identityService;

    @Autowired
    public IdentityResource(IdentityRepository identityRepository, IdentityService identityService) {

        this.identityRepository = identityRepository;
        this.identityService = identityService;
    }

    /**
     * {@code POST  /identities} : Create a new identity.
     *
     * @param identity the identity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new identity, or with status {@code 400 (Bad Request)} if the identity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/identities")
    public ResponseEntity<Identity> createIdentity(@RequestBody Identity identity) throws URISyntaxException {
        log.debug("REST request to save Identity : {}", identity);
        if (identity.getId() != null) {
            throw new BadRequestAlertException("A new identity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Identity result = identityRepository.save(identity);
        return ResponseEntity.created(new URI("/api/identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/identities/lectors")
    public ResponseEntity<List<RecordDto>> addLecotrs(@RequestBody List<RecordDto> recordDtos) {
        System.out.println("Rabottiiiiii");

        this.identityService.createIdentities(recordDtos);
        return ResponseEntity.ok()
                .body(recordDtos);

    }

    /**
     * {@code PUT  /identities} : Updates an existing identity.
     *
     * @param identity the identity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identity,
     * or with status {@code 400 (Bad Request)} if the identity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the identity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/identities")
    public ResponseEntity<Identity> updateIdentity(@RequestBody Identity identity) throws URISyntaxException {
        log.debug("REST request to update Identity : {}", identity);
        if (identity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Identity result = identityRepository.save(identity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, identity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /identities} : get all the identities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of identities in body.
     */
    @GetMapping("/identities")
    public List<Identity> getAllIdentities() {
        log.debug("REST request to get all Identities");
        return identityRepository.findAll();
    }

    /**
     * {@code GET  /identities/:id} : get the "id" identity.
     *
     * @param id the id of the identity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the identity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/identities/{id}")
    public ResponseEntity<Identity> getIdentity(@PathVariable Long id) {
        log.debug("REST request to get Identity : {}", id);
        Optional<Identity> identity = identityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(identity);
    }

    /**
     * {@code DELETE  /identities/:id} : delete the "id" identity.
     *
     * @param id the id of the identity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/identities/{id}")
    public ResponseEntity<Void> deleteIdentity(@PathVariable Long id) {
        log.debug("REST request to delete Identity : {}", id);
        identityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
