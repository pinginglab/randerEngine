package com.pingsec.dev.web.rest;

import com.pingsec.dev.RanderEngineApp;
import com.pingsec.dev.config.SecurityBeanOverrideConfiguration;
import com.pingsec.dev.domain.Course;
import com.pingsec.dev.repository.CourseRepository;
import com.pingsec.dev.service.CourseService;
import com.pingsec.dev.service.dto.CourseDTO;
import com.pingsec.dev.service.mapper.CourseMapper;
import com.pingsec.dev.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.pingsec.dev.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CourseResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, RanderEngineApp.class})
public class CourseResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDER = "AAAAAAAAAA";
    private static final String UPDATED_BUILDER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CREATER_ING = "AAAAAAAAAA";
    private static final String UPDATED_CREATER_ING = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_OTHER = "BBBBBBBBBB";

    private static final String DEFAULT_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_SCORE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LATEST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LATEST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

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

    private MockMvc restCourseMockMvc;

    private Course course;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseResource courseResource = new CourseResource(courseService);
        this.restCourseMockMvc = MockMvcBuilders.standaloneSetup(courseResource)
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
    public static Course createEntity(EntityManager em) {
        Course course = new Course()
            .name(DEFAULT_NAME)
            .author(DEFAULT_AUTHOR)
            .builder(DEFAULT_BUILDER)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .createrIng(DEFAULT_CREATER_ING)
            .other(DEFAULT_OTHER)
            .score(DEFAULT_SCORE)
            .latestDate(DEFAULT_LATEST_DATE);
        return course;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Course createUpdatedEntity(EntityManager em) {
        Course course = new Course()
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .builder(UPDATED_BUILDER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .createrIng(UPDATED_CREATER_ING)
            .other(UPDATED_OTHER)
            .score(UPDATED_SCORE)
            .latestDate(UPDATED_LATEST_DATE);
        return course;
    }

    @BeforeEach
    public void initTest() {
        course = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourse.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testCourse.getBuilder()).isEqualTo(DEFAULT_BUILDER);
        assertThat(testCourse.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCourse.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testCourse.getCreaterIng()).isEqualTo(DEFAULT_CREATER_ING);
        assertThat(testCourse.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testCourse.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testCourse.getLatestDate()).isEqualTo(DEFAULT_LATEST_DATE);
    }

    @Test
    @Transactional
    public void createCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course with an existing ID
        course.setId(1L);
        CourseDTO courseDTO = courseMapper.toDto(course);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList
        restCourseMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].builder").value(hasItem(DEFAULT_BUILDER)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].createrIng").value(hasItem(DEFAULT_CREATER_ING.toString())))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].latestDate").value(hasItem(DEFAULT_LATEST_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(course.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.builder").value(DEFAULT_BUILDER))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.createrIng").value(DEFAULT_CREATER_ING.toString()))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.latestDate").value(DEFAULT_LATEST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        Course updatedCourse = courseRepository.findById(course.getId()).get();
        // Disconnect from session so that the updates on updatedCourse are not directly saved in db
        em.detach(updatedCourse);
        updatedCourse
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .builder(UPDATED_BUILDER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .createrIng(UPDATED_CREATER_ING)
            .other(UPDATED_OTHER)
            .score(UPDATED_SCORE)
            .latestDate(UPDATED_LATEST_DATE);
        CourseDTO courseDTO = courseMapper.toDto(updatedCourse);

        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourse.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testCourse.getBuilder()).isEqualTo(UPDATED_BUILDER);
        assertThat(testCourse.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCourse.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testCourse.getCreaterIng()).isEqualTo(UPDATED_CREATER_ING);
        assertThat(testCourse.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testCourse.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testCourse.getLatestDate()).isEqualTo(UPDATED_LATEST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Delete the course
        restCourseMockMvc.perform(delete("/api/courses/{id}", course.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
