package com.fmi.web.rest;

import com.fmi.AssistantCollaboratorApp;
import com.fmi.domain.ClassType;
import com.fmi.repository.ClassTypeRepository;
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
 * Integration tests for the {@link ClassTypeResource} REST controller.
 */
@SpringBootTest(classes = AssistantCollaboratorApp.class)
public class ClassTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ClassTypeRepository classTypeRepository;

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

    private MockMvc restClassTypeMockMvc;

    private ClassType classType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassTypeResource classTypeResource = new ClassTypeResource(classTypeRepository);
        this.restClassTypeMockMvc = MockMvcBuilders.standaloneSetup(classTypeResource)
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
    public static ClassType createEntity(EntityManager em) {
        ClassType classType = new ClassType()
            .type(DEFAULT_TYPE);
        return classType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassType createUpdatedEntity(EntityManager em) {
        ClassType classType = new ClassType()
            .type(UPDATED_TYPE);
        return classType;
    }

    @BeforeEach
    public void initTest() {
        classType = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassType() throws Exception {
        int databaseSizeBeforeCreate = classTypeRepository.findAll().size();

        // Create the ClassType
        restClassTypeMockMvc.perform(post("/api/class-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classType)))
            .andExpect(status().isCreated());

        // Validate the ClassType in the database
        List<ClassType> classTypeList = classTypeRepository.findAll();
        assertThat(classTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ClassType testClassType = classTypeList.get(classTypeList.size() - 1);
        assertThat(testClassType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createClassTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classTypeRepository.findAll().size();

        // Create the ClassType with an existing ID
        classType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassTypeMockMvc.perform(post("/api/class-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classType)))
            .andExpect(status().isBadRequest());

        // Validate the ClassType in the database
        List<ClassType> classTypeList = classTypeRepository.findAll();
        assertThat(classTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClassTypes() throws Exception {
        // Initialize the database
        classTypeRepository.saveAndFlush(classType);

        // Get all the classTypeList
        restClassTypeMockMvc.perform(get("/api/class-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getClassType() throws Exception {
        // Initialize the database
        classTypeRepository.saveAndFlush(classType);

        // Get the classType
        restClassTypeMockMvc.perform(get("/api/class-types/{id}", classType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingClassType() throws Exception {
        // Get the classType
        restClassTypeMockMvc.perform(get("/api/class-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassType() throws Exception {
        // Initialize the database
        classTypeRepository.saveAndFlush(classType);

        int databaseSizeBeforeUpdate = classTypeRepository.findAll().size();

        // Update the classType
        ClassType updatedClassType = classTypeRepository.findById(classType.getId()).get();
        // Disconnect from session so that the updates on updatedClassType are not directly saved in db
        em.detach(updatedClassType);
        updatedClassType
            .type(UPDATED_TYPE);

        restClassTypeMockMvc.perform(put("/api/class-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassType)))
            .andExpect(status().isOk());

        // Validate the ClassType in the database
        List<ClassType> classTypeList = classTypeRepository.findAll();
        assertThat(classTypeList).hasSize(databaseSizeBeforeUpdate);
        ClassType testClassType = classTypeList.get(classTypeList.size() - 1);
        assertThat(testClassType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingClassType() throws Exception {
        int databaseSizeBeforeUpdate = classTypeRepository.findAll().size();

        // Create the ClassType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassTypeMockMvc.perform(put("/api/class-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classType)))
            .andExpect(status().isBadRequest());

        // Validate the ClassType in the database
        List<ClassType> classTypeList = classTypeRepository.findAll();
        assertThat(classTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassType() throws Exception {
        // Initialize the database
        classTypeRepository.saveAndFlush(classType);

        int databaseSizeBeforeDelete = classTypeRepository.findAll().size();

        // Delete the classType
        restClassTypeMockMvc.perform(delete("/api/class-types/{id}", classType.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassType> classTypeList = classTypeRepository.findAll();
        assertThat(classTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
