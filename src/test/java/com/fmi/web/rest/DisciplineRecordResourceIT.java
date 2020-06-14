package com.fmi.web.rest;

import com.fmi.AssistantCollaboratorApp;
import com.fmi.domain.DisciplineRecord;
import com.fmi.repository.DisciplineRecordRepository;
import com.fmi.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.fmi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DisciplineRecordResource} REST controller.
 */
@SpringBootTest(classes = AssistantCollaboratorApp.class)
public class DisciplineRecordResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORM = "AAAAAAAAAA";
    private static final String UPDATED_FORM = "BBBBBBBBBB";

    private static final Integer DEFAULT_COURSE = 1;
    private static final Integer UPDATED_COURSE = 2;

    private static final Integer DEFAULT_STREAM = 1;
    private static final Integer UPDATED_STREAM = 2;

    private static final Integer DEFAULT_GROUP = 1;
    private static final Integer UPDATED_GROUP = 2;

    private static final Integer DEFAULT_HOURS_FOR_LECTURE = 1;
    private static final Integer UPDATED_HOURS_FOR_LECTURE = 2;

    private static final Integer DEFAULT_HOURS_FOR_WORKSHOP = 1;
    private static final Integer UPDATED_HOURS_FOR_WORKSHOP = 2;

    private static final Integer DEFAULT_HOURS_FOR_EXERCISE = 1;
    private static final Integer UPDATED_HOURS_FOR_EXERCISE = 2;

    private static final Integer DEFAULT_NUMBER_OF_STUDENTS = 1;
    private static final Integer UPDATED_NUMBER_OF_STUDENTS = 2;

    @Autowired
    private DisciplineRecordRepository disciplineRecordRepository;

    @Mock
    private DisciplineRecordRepository disciplineRecordRepositoryMock;

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

    private MockMvc restDisciplineRecordMockMvc;

    private DisciplineRecord disciplineRecord;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisciplineRecordResource disciplineRecordResource = new DisciplineRecordResource(disciplineRecordRepository);
        this.restDisciplineRecordMockMvc = MockMvcBuilders.standaloneSetup(disciplineRecordResource)
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
    public static DisciplineRecord createEntity(EntityManager em) {
        DisciplineRecord disciplineRecord = new DisciplineRecord()
            .code(DEFAULT_CODE)
            .department(DEFAULT_DEPARTMENT)
            .name(DEFAULT_NAME)
            .form(DEFAULT_FORM)
            .course(DEFAULT_COURSE)
            .stream(DEFAULT_STREAM)
            .group(DEFAULT_GROUP)
            .hoursForLecture(DEFAULT_HOURS_FOR_LECTURE)
            .hoursForWorkshop(DEFAULT_HOURS_FOR_WORKSHOP)
            .hoursForExercise(DEFAULT_HOURS_FOR_EXERCISE)
            .numberOfStudents(DEFAULT_NUMBER_OF_STUDENTS);
        return disciplineRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisciplineRecord createUpdatedEntity(EntityManager em) {
        DisciplineRecord disciplineRecord = new DisciplineRecord()
            .code(UPDATED_CODE)
            .department(UPDATED_DEPARTMENT)
            .name(UPDATED_NAME)
            .form(UPDATED_FORM)
            .course(UPDATED_COURSE)
            .stream(UPDATED_STREAM)
            .group(UPDATED_GROUP)
            .hoursForLecture(UPDATED_HOURS_FOR_LECTURE)
            .hoursForWorkshop(UPDATED_HOURS_FOR_WORKSHOP)
            .hoursForExercise(UPDATED_HOURS_FOR_EXERCISE)
            .numberOfStudents(UPDATED_NUMBER_OF_STUDENTS);
        return disciplineRecord;
    }

    @BeforeEach
    public void initTest() {
        disciplineRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisciplineRecord() throws Exception {
        int databaseSizeBeforeCreate = disciplineRecordRepository.findAll().size();

        // Create the DisciplineRecord
        restDisciplineRecordMockMvc.perform(post("/api/discipline-records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineRecord)))
            .andExpect(status().isCreated());

        // Validate the DisciplineRecord in the database
        List<DisciplineRecord> disciplineRecordList = disciplineRecordRepository.findAll();
        assertThat(disciplineRecordList).hasSize(databaseSizeBeforeCreate + 1);
        DisciplineRecord testDisciplineRecord = disciplineRecordList.get(disciplineRecordList.size() - 1);
        assertThat(testDisciplineRecord.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDisciplineRecord.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testDisciplineRecord.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDisciplineRecord.getForm()).isEqualTo(DEFAULT_FORM);
        assertThat(testDisciplineRecord.getCourse()).isEqualTo(DEFAULT_COURSE);
        assertThat(testDisciplineRecord.getStream()).isEqualTo(DEFAULT_STREAM);
        assertThat(testDisciplineRecord.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testDisciplineRecord.getHoursForLecture()).isEqualTo(DEFAULT_HOURS_FOR_LECTURE);
        assertThat(testDisciplineRecord.getHoursForWorkshop()).isEqualTo(DEFAULT_HOURS_FOR_WORKSHOP);
        assertThat(testDisciplineRecord.getHoursForExercise()).isEqualTo(DEFAULT_HOURS_FOR_EXERCISE);
        assertThat(testDisciplineRecord.getNumberOfStudents()).isEqualTo(DEFAULT_NUMBER_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void createDisciplineRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplineRecordRepository.findAll().size();

        // Create the DisciplineRecord with an existing ID
        disciplineRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplineRecordMockMvc.perform(post("/api/discipline-records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineRecord)))
            .andExpect(status().isBadRequest());

        // Validate the DisciplineRecord in the database
        List<DisciplineRecord> disciplineRecordList = disciplineRecordRepository.findAll();
        assertThat(disciplineRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDisciplineRecords() throws Exception {
        // Initialize the database
        disciplineRecordRepository.saveAndFlush(disciplineRecord);

        // Get all the disciplineRecordList
        restDisciplineRecordMockMvc.perform(get("/api/discipline-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplineRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].form").value(hasItem(DEFAULT_FORM)))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE)))
            .andExpect(jsonPath("$.[*].stream").value(hasItem(DEFAULT_STREAM)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].hoursForLecture").value(hasItem(DEFAULT_HOURS_FOR_LECTURE)))
            .andExpect(jsonPath("$.[*].hoursForWorkshop").value(hasItem(DEFAULT_HOURS_FOR_WORKSHOP)))
            .andExpect(jsonPath("$.[*].hoursForExercise").value(hasItem(DEFAULT_HOURS_FOR_EXERCISE)))
            .andExpect(jsonPath("$.[*].numberOfStudents").value(hasItem(DEFAULT_NUMBER_OF_STUDENTS)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDisciplineRecordsWithEagerRelationshipsIsEnabled() throws Exception {
        DisciplineRecordResource disciplineRecordResource = new DisciplineRecordResource(disciplineRecordRepositoryMock);
        when(disciplineRecordRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDisciplineRecordMockMvc = MockMvcBuilders.standaloneSetup(disciplineRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDisciplineRecordMockMvc.perform(get("/api/discipline-records?eagerload=true"))
        .andExpect(status().isOk());

        verify(disciplineRecordRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDisciplineRecordsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DisciplineRecordResource disciplineRecordResource = new DisciplineRecordResource(disciplineRecordRepositoryMock);
            when(disciplineRecordRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDisciplineRecordMockMvc = MockMvcBuilders.standaloneSetup(disciplineRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDisciplineRecordMockMvc.perform(get("/api/discipline-records?eagerload=true"))
        .andExpect(status().isOk());

            verify(disciplineRecordRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDisciplineRecord() throws Exception {
        // Initialize the database
        disciplineRecordRepository.saveAndFlush(disciplineRecord);

        // Get the disciplineRecord
        restDisciplineRecordMockMvc.perform(get("/api/discipline-records/{id}", disciplineRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disciplineRecord.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.form").value(DEFAULT_FORM))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE))
            .andExpect(jsonPath("$.stream").value(DEFAULT_STREAM))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.hoursForLecture").value(DEFAULT_HOURS_FOR_LECTURE))
            .andExpect(jsonPath("$.hoursForWorkshop").value(DEFAULT_HOURS_FOR_WORKSHOP))
            .andExpect(jsonPath("$.hoursForExercise").value(DEFAULT_HOURS_FOR_EXERCISE))
            .andExpect(jsonPath("$.numberOfStudents").value(DEFAULT_NUMBER_OF_STUDENTS));
    }

    @Test
    @Transactional
    public void getNonExistingDisciplineRecord() throws Exception {
        // Get the disciplineRecord
        restDisciplineRecordMockMvc.perform(get("/api/discipline-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisciplineRecord() throws Exception {
        // Initialize the database
        disciplineRecordRepository.saveAndFlush(disciplineRecord);

        int databaseSizeBeforeUpdate = disciplineRecordRepository.findAll().size();

        // Update the disciplineRecord
        DisciplineRecord updatedDisciplineRecord = disciplineRecordRepository.findById(disciplineRecord.getId()).get();
        // Disconnect from session so that the updates on updatedDisciplineRecord are not directly saved in db
        em.detach(updatedDisciplineRecord);
        updatedDisciplineRecord
            .code(UPDATED_CODE)
            .department(UPDATED_DEPARTMENT)
            .name(UPDATED_NAME)
            .form(UPDATED_FORM)
            .course(UPDATED_COURSE)
            .stream(UPDATED_STREAM)
            .group(UPDATED_GROUP)
            .hoursForLecture(UPDATED_HOURS_FOR_LECTURE)
            .hoursForWorkshop(UPDATED_HOURS_FOR_WORKSHOP)
            .hoursForExercise(UPDATED_HOURS_FOR_EXERCISE)
            .numberOfStudents(UPDATED_NUMBER_OF_STUDENTS);

        restDisciplineRecordMockMvc.perform(put("/api/discipline-records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisciplineRecord)))
            .andExpect(status().isOk());

        // Validate the DisciplineRecord in the database
        List<DisciplineRecord> disciplineRecordList = disciplineRecordRepository.findAll();
        assertThat(disciplineRecordList).hasSize(databaseSizeBeforeUpdate);
        DisciplineRecord testDisciplineRecord = disciplineRecordList.get(disciplineRecordList.size() - 1);
        assertThat(testDisciplineRecord.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDisciplineRecord.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testDisciplineRecord.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDisciplineRecord.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testDisciplineRecord.getCourse()).isEqualTo(UPDATED_COURSE);
        assertThat(testDisciplineRecord.getStream()).isEqualTo(UPDATED_STREAM);
        assertThat(testDisciplineRecord.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testDisciplineRecord.getHoursForLecture()).isEqualTo(UPDATED_HOURS_FOR_LECTURE);
        assertThat(testDisciplineRecord.getHoursForWorkshop()).isEqualTo(UPDATED_HOURS_FOR_WORKSHOP);
        assertThat(testDisciplineRecord.getHoursForExercise()).isEqualTo(UPDATED_HOURS_FOR_EXERCISE);
        assertThat(testDisciplineRecord.getNumberOfStudents()).isEqualTo(UPDATED_NUMBER_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingDisciplineRecord() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRecordRepository.findAll().size();

        // Create the DisciplineRecord

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplineRecordMockMvc.perform(put("/api/discipline-records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineRecord)))
            .andExpect(status().isBadRequest());

        // Validate the DisciplineRecord in the database
        List<DisciplineRecord> disciplineRecordList = disciplineRecordRepository.findAll();
        assertThat(disciplineRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisciplineRecord() throws Exception {
        // Initialize the database
        disciplineRecordRepository.saveAndFlush(disciplineRecord);

        int databaseSizeBeforeDelete = disciplineRecordRepository.findAll().size();

        // Delete the disciplineRecord
        restDisciplineRecordMockMvc.perform(delete("/api/discipline-records/{id}", disciplineRecord.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DisciplineRecord> disciplineRecordList = disciplineRecordRepository.findAll();
        assertThat(disciplineRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
