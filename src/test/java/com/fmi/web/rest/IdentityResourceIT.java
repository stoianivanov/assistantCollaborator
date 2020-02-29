package com.fmi.web.rest;

import com.fmi.AssistantCollaboratorApp;
import com.fmi.domain.Identity;
import com.fmi.repository.IdentityRepository;
import com.fmi.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.fmi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IdentityResource} REST controller.
 */
@SpringBootTest(classes = AssistantCollaboratorApp.class)
public class IdentityResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCIENCE_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_SCIENCE_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_JOB = "AAAAAAAAAA";
    private static final String UPDATED_JOB = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_E_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_E_MAIL = "BBBBBBBBBB";

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIdentityMockMvc;

    private Identity identity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IdentityResource identityResource = new IdentityResource(identityRepository);
        this.restIdentityMockMvc = MockMvcBuilders.standaloneSetup(identityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identity createEntity(EntityManager em) {
        Identity identity = new Identity()
            .fullName(DEFAULT_FULL_NAME)
            .scienceDegree(DEFAULT_SCIENCE_DEGREE)
            .education(DEFAULT_EDUCATION)
            .job(DEFAULT_JOB)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .eMail(DEFAULT_E_MAIL);
        return identity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identity createUpdatedEntity(EntityManager em) {
        Identity identity = new Identity()
            .fullName(UPDATED_FULL_NAME)
            .scienceDegree(UPDATED_SCIENCE_DEGREE)
            .education(UPDATED_EDUCATION)
            .job(UPDATED_JOB)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .eMail(UPDATED_E_MAIL);
        return identity;
    }

    @BeforeEach
    public void initTest() {
        identity = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdentity() throws Exception {
        int databaseSizeBeforeCreate = identityRepository.findAll().size();

        // Create the Identity
        restIdentityMockMvc.perform(post("/api/identities")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isCreated());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeCreate + 1);
        Identity testIdentity = identityList.get(identityList.size() - 1);
        assertThat(testIdentity.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testIdentity.getScienceDegree()).isEqualTo(DEFAULT_SCIENCE_DEGREE);
        assertThat(testIdentity.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testIdentity.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testIdentity.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testIdentity.geteMail()).isEqualTo(DEFAULT_E_MAIL);
    }

    @Test
    @Transactional
    public void createIdentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = identityRepository.findAll().size();

        // Create the Identity with an existing ID
        identity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentityMockMvc.perform(post("/api/identities")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIdentities() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        // Get all the identityList
        restIdentityMockMvc.perform(get("/api/identities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identity.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].scienceDegree").value(hasItem(DEFAULT_SCIENCE_DEGREE)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].job").value(hasItem(DEFAULT_JOB)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL)));
    }
    
    @Test
    @Transactional
    public void getIdentity() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        // Get the identity
        restIdentityMockMvc.perform(get("/api/identities/{id}", identity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(identity.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.scienceDegree").value(DEFAULT_SCIENCE_DEGREE))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.job").value(DEFAULT_JOB))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.eMail").value(DEFAULT_E_MAIL));
    }

    @Test
    @Transactional
    public void getNonExistingIdentity() throws Exception {
        // Get the identity
        restIdentityMockMvc.perform(get("/api/identities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdentity() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        int databaseSizeBeforeUpdate = identityRepository.findAll().size();

        // Update the identity
        Identity updatedIdentity = identityRepository.findById(identity.getId()).get();
        // Disconnect from session so that the updates on updatedIdentity are not directly saved in db
        em.detach(updatedIdentity);
        updatedIdentity
            .fullName(UPDATED_FULL_NAME)
            .scienceDegree(UPDATED_SCIENCE_DEGREE)
            .education(UPDATED_EDUCATION)
            .job(UPDATED_JOB)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .eMail(UPDATED_E_MAIL);

        restIdentityMockMvc.perform(put("/api/identities")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdentity)))
            .andExpect(status().isOk());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
        Identity testIdentity = identityList.get(identityList.size() - 1);
        assertThat(testIdentity.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testIdentity.getScienceDegree()).isEqualTo(UPDATED_SCIENCE_DEGREE);
        assertThat(testIdentity.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testIdentity.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testIdentity.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testIdentity.geteMail()).isEqualTo(UPDATED_E_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingIdentity() throws Exception {
        int databaseSizeBeforeUpdate = identityRepository.findAll().size();

        // Create the Identity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentityMockMvc.perform(put("/api/identities")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(identity)))
            .andExpect(status().isBadRequest());

        // Validate the Identity in the database
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdentity() throws Exception {
        // Initialize the database
        identityRepository.saveAndFlush(identity);

        int databaseSizeBeforeDelete = identityRepository.findAll().size();

        // Delete the identity
        restIdentityMockMvc.perform(delete("/api/identities/{id}", identity.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Identity> identityList = identityRepository.findAll();
        assertThat(identityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
