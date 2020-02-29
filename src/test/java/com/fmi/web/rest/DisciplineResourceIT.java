package com.fmi.web.rest;

import com.fmi.AssistantCollaboratorApp;
import com.fmi.domain.Discipline;
import com.fmi.repository.DisciplineRepository;
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

import com.fmi.domain.enumeration.Semester;
/**
 * Integration tests for the {@link DisciplineResource} REST controller.
 */
@SpringBootTest(classes = AssistantCollaboratorApp.class)
public class DisciplineResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_HOURS_FOR_LECTURE = 1;
    private static final Integer UPDATED_HOURS_FOR_LECTURE = 2;

    private static final Integer DEFAULT_HOURS_FOR_WORKSHOP = 1;
    private static final Integer UPDATED_HOURS_FOR_WORKSHOP = 2;

    private static final Integer DEFAULT_HOURS_FOR_EXERCISE = 1;
    private static final Integer UPDATED_HOURS_FOR_EXERCISE = 2;

    private static final Semester DEFAULT_SEMESTER = Semester.SUMMER;
    private static final Semester UPDATED_SEMESTER = Semester.WINTER;

    private static final Integer DEFAULT_NUMBER_OF_STUDENTS = 1;
    private static final Integer UPDATED_NUMBER_OF_STUDENTS = 2;

    private static final Boolean DEFAULT_INCOMING_TEST = false;
    private static final Boolean UPDATED_INCOMING_TEST = true;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Mock
    private DisciplineRepository disciplineRepositoryMock;

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

    private MockMvc restDisciplineMockMvc;

    private Discipline discipline;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisciplineResource disciplineResource = new DisciplineResource(disciplineRepository);
        this.restDisciplineMockMvc = MockMvcBuilders.standaloneSetup(disciplineResource)
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
    public static Discipline createEntity(EntityManager em) {
        Discipline discipline = new Discipline()
            .code(DEFAULT_CODE)
            .department(DEFAULT_DEPARTMENT)
            .name(DEFAULT_NAME)
            .hoursForLecture(DEFAULT_HOURS_FOR_LECTURE)
            .hoursForWorkshop(DEFAULT_HOURS_FOR_WORKSHOP)
            .hoursForExercise(DEFAULT_HOURS_FOR_EXERCISE)
            .semester(DEFAULT_SEMESTER)
            .numberOfStudents(DEFAULT_NUMBER_OF_STUDENTS)
            .incomingTest(DEFAULT_INCOMING_TEST);
        return discipline;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discipline createUpdatedEntity(EntityManager em) {
        Discipline discipline = new Discipline()
            .code(UPDATED_CODE)
            .department(UPDATED_DEPARTMENT)
            .name(UPDATED_NAME)
            .hoursForLecture(UPDATED_HOURS_FOR_LECTURE)
            .hoursForWorkshop(UPDATED_HOURS_FOR_WORKSHOP)
            .hoursForExercise(UPDATED_HOURS_FOR_EXERCISE)
            .semester(UPDATED_SEMESTER)
            .numberOfStudents(UPDATED_NUMBER_OF_STUDENTS)
            .incomingTest(UPDATED_INCOMING_TEST);
        return discipline;
    }

    @BeforeEach
    public void initTest() {
        discipline = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscipline() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();

        // Create the Discipline
        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discipline)))
            .andExpect(status().isCreated());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate + 1);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDiscipline.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testDiscipline.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiscipline.getHoursForLecture()).isEqualTo(DEFAULT_HOURS_FOR_LECTURE);
        assertThat(testDiscipline.getHoursForWorkshop()).isEqualTo(DEFAULT_HOURS_FOR_WORKSHOP);
        assertThat(testDiscipline.getHoursForExercise()).isEqualTo(DEFAULT_HOURS_FOR_EXERCISE);
        assertThat(testDiscipline.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testDiscipline.getNumberOfStudents()).isEqualTo(DEFAULT_NUMBER_OF_STUDENTS);
        assertThat(testDiscipline.isIncomingTest()).isEqualTo(DEFAULT_INCOMING_TEST);
    }

    @Test
    @Transactional
    public void createDisciplineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();

        // Create the Discipline with an existing ID
        discipline.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discipline)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDisciplines() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList
        restDisciplineMockMvc.perform(get("/api/disciplines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discipline.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hoursForLecture").value(hasItem(DEFAULT_HOURS_FOR_LECTURE)))
            .andExpect(jsonPath("$.[*].hoursForWorkshop").value(hasItem(DEFAULT_HOURS_FOR_WORKSHOP)))
            .andExpect(jsonPath("$.[*].hoursForExercise").value(hasItem(DEFAULT_HOURS_FOR_EXERCISE)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())))
            .andExpect(jsonPath("$.[*].numberOfStudents").value(hasItem(DEFAULT_NUMBER_OF_STUDENTS)))
            .andExpect(jsonPath("$.[*].incomingTest").value(hasItem(DEFAULT_INCOMING_TEST.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDisciplinesWithEagerRelationshipsIsEnabled() throws Exception {
        DisciplineResource disciplineResource = new DisciplineResource(disciplineRepositoryMock);
        when(disciplineRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDisciplineMockMvc = MockMvcBuilders.standaloneSetup(disciplineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDisciplineMockMvc.perform(get("/api/disciplines?eagerload=true"))
        .andExpect(status().isOk());

        verify(disciplineRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDisciplinesWithEagerRelationshipsIsNotEnabled() throws Exception {
        DisciplineResource disciplineResource = new DisciplineResource(disciplineRepositoryMock);
            when(disciplineRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDisciplineMockMvc = MockMvcBuilders.standaloneSetup(disciplineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDisciplineMockMvc.perform(get("/api/disciplines?eagerload=true"))
        .andExpect(status().isOk());

            verify(disciplineRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get the discipline
        restDisciplineMockMvc.perform(get("/api/disciplines/{id}", discipline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discipline.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.hoursForLecture").value(DEFAULT_HOURS_FOR_LECTURE))
            .andExpect(jsonPath("$.hoursForWorkshop").value(DEFAULT_HOURS_FOR_WORKSHOP))
            .andExpect(jsonPath("$.hoursForExercise").value(DEFAULT_HOURS_FOR_EXERCISE))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()))
            .andExpect(jsonPath("$.numberOfStudents").value(DEFAULT_NUMBER_OF_STUDENTS))
            .andExpect(jsonPath("$.incomingTest").value(DEFAULT_INCOMING_TEST.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscipline() throws Exception {
        // Get the discipline
        restDisciplineMockMvc.perform(get("/api/disciplines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Update the discipline
        Discipline updatedDiscipline = disciplineRepository.findById(discipline.getId()).get();
        // Disconnect from session so that the updates on updatedDiscipline are not directly saved in db
        em.detach(updatedDiscipline);
        updatedDiscipline
            .code(UPDATED_CODE)
            .department(UPDATED_DEPARTMENT)
            .name(UPDATED_NAME)
            .hoursForLecture(UPDATED_HOURS_FOR_LECTURE)
            .hoursForWorkshop(UPDATED_HOURS_FOR_WORKSHOP)
            .hoursForExercise(UPDATED_HOURS_FOR_EXERCISE)
            .semester(UPDATED_SEMESTER)
            .numberOfStudents(UPDATED_NUMBER_OF_STUDENTS)
            .incomingTest(UPDATED_INCOMING_TEST);

        restDisciplineMockMvc.perform(put("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiscipline)))
            .andExpect(status().isOk());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDiscipline.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testDiscipline.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiscipline.getHoursForLecture()).isEqualTo(UPDATED_HOURS_FOR_LECTURE);
        assertThat(testDiscipline.getHoursForWorkshop()).isEqualTo(UPDATED_HOURS_FOR_WORKSHOP);
        assertThat(testDiscipline.getHoursForExercise()).isEqualTo(UPDATED_HOURS_FOR_EXERCISE);
        assertThat(testDiscipline.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testDiscipline.getNumberOfStudents()).isEqualTo(UPDATED_NUMBER_OF_STUDENTS);
        assertThat(testDiscipline.isIncomingTest()).isEqualTo(UPDATED_INCOMING_TEST);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Create the Discipline

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplineMockMvc.perform(put("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discipline)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeDelete = disciplineRepository.findAll().size();

        // Delete the discipline
        restDisciplineMockMvc.perform(delete("/api/disciplines/{id}", discipline.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
