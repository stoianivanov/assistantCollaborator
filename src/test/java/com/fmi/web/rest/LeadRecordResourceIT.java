package com.fmi.web.rest;

import com.fmi.AssistantCollaboratorApp;
import com.fmi.domain.LeadRecord;
import com.fmi.repository.LeadRecordRepository;
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

import com.fmi.domain.enumeration.CourseType;
/**
 * Integration tests for the {@link LeadRecordResource} REST controller.
 */
@SpringBootTest(classes = AssistantCollaboratorApp.class)
public class LeadRecordResourceIT {

    private static final Integer DEFAULT_MAX_HOURS_FOR_LECTURE = 1;
    private static final Integer UPDATED_MAX_HOURS_FOR_LECTURE = 2;

    private static final Integer DEFAULT_MAX_HOURS_FOR_EXERCISE = 1;
    private static final Integer UPDATED_MAX_HOURS_FOR_EXERCISE = 2;

    private static final Integer DEFAULT_MAX_HOURS_FOR_WORKSHOP = 1;
    private static final Integer UPDATED_MAX_HOURS_FOR_WORKSHOP = 2;

    private static final Integer DEFAULT_COURSE = 1;
    private static final Integer UPDATED_COURSE = 2;

    private static final CourseType DEFAULT_TYPE = CourseType.LECTURE;
    private static final CourseType UPDATED_TYPE = CourseType.EXARCISE;

    @Autowired
    private LeadRecordRepository leadRecordRepository;

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

    private MockMvc restLeadRecordMockMvc;

    private LeadRecord leadRecord;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeadRecordResource leadRecordResource = new LeadRecordResource(leadRecordRepository);
        this.restLeadRecordMockMvc = MockMvcBuilders.standaloneSetup(leadRecordResource)
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
    public static LeadRecord createEntity(EntityManager em) {
        LeadRecord leadRecord = new LeadRecord()
            .maxHoursForLecture(DEFAULT_MAX_HOURS_FOR_LECTURE)
            .maxHoursForExercise(DEFAULT_MAX_HOURS_FOR_EXERCISE)
            .maxHoursForWorkshop(DEFAULT_MAX_HOURS_FOR_WORKSHOP)
            .course(DEFAULT_COURSE)
            .type(DEFAULT_TYPE);
        return leadRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeadRecord createUpdatedEntity(EntityManager em) {
        LeadRecord leadRecord = new LeadRecord()
            .maxHoursForLecture(UPDATED_MAX_HOURS_FOR_LECTURE)
            .maxHoursForExercise(UPDATED_MAX_HOURS_FOR_EXERCISE)
            .maxHoursForWorkshop(UPDATED_MAX_HOURS_FOR_WORKSHOP)
            .course(UPDATED_COURSE)
            .type(UPDATED_TYPE);
        return leadRecord;
    }

    @BeforeEach
    public void initTest() {
        leadRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeadRecord() throws Exception {
        int databaseSizeBeforeCreate = leadRecordRepository.findAll().size();

        // Create the LeadRecord
        restLeadRecordMockMvc.perform(post("/api/lead-records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leadRecord)))
            .andExpect(status().isCreated());

        // Validate the LeadRecord in the database
        List<LeadRecord> leadRecordList = leadRecordRepository.findAll();
        assertThat(leadRecordList).hasSize(databaseSizeBeforeCreate + 1);
        LeadRecord testLeadRecord = leadRecordList.get(leadRecordList.size() - 1);
        assertThat(testLeadRecord.getMaxHoursForLecture()).isEqualTo(DEFAULT_MAX_HOURS_FOR_LECTURE);
        assertThat(testLeadRecord.getMaxHoursForExercise()).isEqualTo(DEFAULT_MAX_HOURS_FOR_EXERCISE);
        assertThat(testLeadRecord.getMaxHoursForWorkshop()).isEqualTo(DEFAULT_MAX_HOURS_FOR_WORKSHOP);
        assertThat(testLeadRecord.getCourse()).isEqualTo(DEFAULT_COURSE);
        assertThat(testLeadRecord.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createLeadRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leadRecordRepository.findAll().size();

        // Create the LeadRecord with an existing ID
        leadRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeadRecordMockMvc.perform(post("/api/lead-records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leadRecord)))
            .andExpect(status().isBadRequest());

        // Validate the LeadRecord in the database
        List<LeadRecord> leadRecordList = leadRecordRepository.findAll();
        assertThat(leadRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLeadRecords() throws Exception {
        // Initialize the database
        leadRecordRepository.saveAndFlush(leadRecord);

        // Get all the leadRecordList
        restLeadRecordMockMvc.perform(get("/api/lead-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leadRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].maxHoursForLecture").value(hasItem(DEFAULT_MAX_HOURS_FOR_LECTURE)))
            .andExpect(jsonPath("$.[*].maxHoursForExercise").value(hasItem(DEFAULT_MAX_HOURS_FOR_EXERCISE)))
            .andExpect(jsonPath("$.[*].maxHoursForWorkshop").value(hasItem(DEFAULT_MAX_HOURS_FOR_WORKSHOP)))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getLeadRecord() throws Exception {
        // Initialize the database
        leadRecordRepository.saveAndFlush(leadRecord);

        // Get the leadRecord
        restLeadRecordMockMvc.perform(get("/api/lead-records/{id}", leadRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leadRecord.getId().intValue()))
            .andExpect(jsonPath("$.maxHoursForLecture").value(DEFAULT_MAX_HOURS_FOR_LECTURE))
            .andExpect(jsonPath("$.maxHoursForExercise").value(DEFAULT_MAX_HOURS_FOR_EXERCISE))
            .andExpect(jsonPath("$.maxHoursForWorkshop").value(DEFAULT_MAX_HOURS_FOR_WORKSHOP))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeadRecord() throws Exception {
        // Get the leadRecord
        restLeadRecordMockMvc.perform(get("/api/lead-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeadRecord() throws Exception {
        // Initialize the database
        leadRecordRepository.saveAndFlush(leadRecord);

        int databaseSizeBeforeUpdate = leadRecordRepository.findAll().size();

        // Update the leadRecord
        LeadRecord updatedLeadRecord = leadRecordRepository.findById(leadRecord.getId()).get();
        // Disconnect from session so that the updates on updatedLeadRecord are not directly saved in db
        em.detach(updatedLeadRecord);
        updatedLeadRecord
            .maxHoursForLecture(UPDATED_MAX_HOURS_FOR_LECTURE)
            .maxHoursForExercise(UPDATED_MAX_HOURS_FOR_EXERCISE)
            .maxHoursForWorkshop(UPDATED_MAX_HOURS_FOR_WORKSHOP)
            .course(UPDATED_COURSE)
            .type(UPDATED_TYPE);

        restLeadRecordMockMvc.perform(put("/api/lead-records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeadRecord)))
            .andExpect(status().isOk());

        // Validate the LeadRecord in the database
        List<LeadRecord> leadRecordList = leadRecordRepository.findAll();
        assertThat(leadRecordList).hasSize(databaseSizeBeforeUpdate);
        LeadRecord testLeadRecord = leadRecordList.get(leadRecordList.size() - 1);
        assertThat(testLeadRecord.getMaxHoursForLecture()).isEqualTo(UPDATED_MAX_HOURS_FOR_LECTURE);
        assertThat(testLeadRecord.getMaxHoursForExercise()).isEqualTo(UPDATED_MAX_HOURS_FOR_EXERCISE);
        assertThat(testLeadRecord.getMaxHoursForWorkshop()).isEqualTo(UPDATED_MAX_HOURS_FOR_WORKSHOP);
        assertThat(testLeadRecord.getCourse()).isEqualTo(UPDATED_COURSE);
        assertThat(testLeadRecord.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLeadRecord() throws Exception {
        int databaseSizeBeforeUpdate = leadRecordRepository.findAll().size();

        // Create the LeadRecord

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadRecordMockMvc.perform(put("/api/lead-records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leadRecord)))
            .andExpect(status().isBadRequest());

        // Validate the LeadRecord in the database
        List<LeadRecord> leadRecordList = leadRecordRepository.findAll();
        assertThat(leadRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeadRecord() throws Exception {
        // Initialize the database
        leadRecordRepository.saveAndFlush(leadRecord);

        int databaseSizeBeforeDelete = leadRecordRepository.findAll().size();

        // Delete the leadRecord
        restLeadRecordMockMvc.perform(delete("/api/lead-records/{id}", leadRecord.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeadRecord> leadRecordList = leadRecordRepository.findAll();
        assertThat(leadRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
