package com.fmi.web.rest;

import com.fmi.AssistantCollaboratorApp;
import com.fmi.domain.DisciplineType;
import com.fmi.repository.DisciplineTypeRepository;
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
 * Integration tests for the {@link DisciplineTypeResource} REST controller.
 */
@SpringBootTest(classes = AssistantCollaboratorApp.class)
public class DisciplineTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private DisciplineTypeRepository disciplineTypeRepository;

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

    private MockMvc restDisciplineTypeMockMvc;

    private DisciplineType disciplineType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisciplineTypeResource disciplineTypeResource = new DisciplineTypeResource(disciplineTypeRepository);
        this.restDisciplineTypeMockMvc = MockMvcBuilders.standaloneSetup(disciplineTypeResource)
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
    public static DisciplineType createEntity(EntityManager em) {
        DisciplineType disciplineType = new DisciplineType()
            .type(DEFAULT_TYPE);
        return disciplineType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisciplineType createUpdatedEntity(EntityManager em) {
        DisciplineType disciplineType = new DisciplineType()
            .type(UPDATED_TYPE);
        return disciplineType;
    }

    @BeforeEach
    public void initTest() {
        disciplineType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisciplineType() throws Exception {
        int databaseSizeBeforeCreate = disciplineTypeRepository.findAll().size();

        // Create the DisciplineType
        restDisciplineTypeMockMvc.perform(post("/api/discipline-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineType)))
            .andExpect(status().isCreated());

        // Validate the DisciplineType in the database
        List<DisciplineType> disciplineTypeList = disciplineTypeRepository.findAll();
        assertThat(disciplineTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DisciplineType testDisciplineType = disciplineTypeList.get(disciplineTypeList.size() - 1);
        assertThat(testDisciplineType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createDisciplineTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplineTypeRepository.findAll().size();

        // Create the DisciplineType with an existing ID
        disciplineType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplineTypeMockMvc.perform(post("/api/discipline-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineType)))
            .andExpect(status().isBadRequest());

        // Validate the DisciplineType in the database
        List<DisciplineType> disciplineTypeList = disciplineTypeRepository.findAll();
        assertThat(disciplineTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDisciplineTypes() throws Exception {
        // Initialize the database
        disciplineTypeRepository.saveAndFlush(disciplineType);

        // Get all the disciplineTypeList
        restDisciplineTypeMockMvc.perform(get("/api/discipline-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplineType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getDisciplineType() throws Exception {
        // Initialize the database
        disciplineTypeRepository.saveAndFlush(disciplineType);

        // Get the disciplineType
        restDisciplineTypeMockMvc.perform(get("/api/discipline-types/{id}", disciplineType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disciplineType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingDisciplineType() throws Exception {
        // Get the disciplineType
        restDisciplineTypeMockMvc.perform(get("/api/discipline-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisciplineType() throws Exception {
        // Initialize the database
        disciplineTypeRepository.saveAndFlush(disciplineType);

        int databaseSizeBeforeUpdate = disciplineTypeRepository.findAll().size();

        // Update the disciplineType
        DisciplineType updatedDisciplineType = disciplineTypeRepository.findById(disciplineType.getId()).get();
        // Disconnect from session so that the updates on updatedDisciplineType are not directly saved in db
        em.detach(updatedDisciplineType);
        updatedDisciplineType
            .type(UPDATED_TYPE);

        restDisciplineTypeMockMvc.perform(put("/api/discipline-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisciplineType)))
            .andExpect(status().isOk());

        // Validate the DisciplineType in the database
        List<DisciplineType> disciplineTypeList = disciplineTypeRepository.findAll();
        assertThat(disciplineTypeList).hasSize(databaseSizeBeforeUpdate);
        DisciplineType testDisciplineType = disciplineTypeList.get(disciplineTypeList.size() - 1);
        assertThat(testDisciplineType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDisciplineType() throws Exception {
        int databaseSizeBeforeUpdate = disciplineTypeRepository.findAll().size();

        // Create the DisciplineType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplineTypeMockMvc.perform(put("/api/discipline-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineType)))
            .andExpect(status().isBadRequest());

        // Validate the DisciplineType in the database
        List<DisciplineType> disciplineTypeList = disciplineTypeRepository.findAll();
        assertThat(disciplineTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisciplineType() throws Exception {
        // Initialize the database
        disciplineTypeRepository.saveAndFlush(disciplineType);

        int databaseSizeBeforeDelete = disciplineTypeRepository.findAll().size();

        // Delete the disciplineType
        restDisciplineTypeMockMvc.perform(delete("/api/discipline-types/{id}", disciplineType.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DisciplineType> disciplineTypeList = disciplineTypeRepository.findAll();
        assertThat(disciplineTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
