package com.pingsec.dev.web.rest;

import com.pingsec.dev.RanderEngineApp;
import com.pingsec.dev.config.SecurityBeanOverrideConfiguration;
import com.pingsec.dev.domain.Scenes;
import com.pingsec.dev.repository.ScenesRepository;
import com.pingsec.dev.service.ScenesService;
import com.pingsec.dev.service.dto.ScenesDTO;
import com.pingsec.dev.service.mapper.ScenesMapper;
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
 * Integration tests for the {@link ScenesResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, RanderEngineApp.class})
public class ScenesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATER = "AAAAAAAAAA";
    private static final String UPDATED_CREATER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_WAITING_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WAITING_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_EXTEND_TIME = 1;
    private static final Integer UPDATED_EXTEND_TIME = 2;

    private static final String DEFAULT_APP = "AAAAAAAAAA";
    private static final String UPDATED_APP = "BBBBBBBBBB";

    @Autowired
    private ScenesRepository scenesRepository;

    @Autowired
    private ScenesMapper scenesMapper;

    @Autowired
    private ScenesService scenesService;

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

    private MockMvc restScenesMockMvc;

    private Scenes scenes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScenesResource scenesResource = new ScenesResource(scenesService);
        this.restScenesMockMvc = MockMvcBuilders.standaloneSetup(scenesResource)
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
    public static Scenes createEntity(EntityManager em) {
        Scenes scenes = new Scenes()
            .name(DEFAULT_NAME)
            .creater(DEFAULT_CREATER)
            .type(DEFAULT_TYPE)
            .createTime(DEFAULT_CREATE_TIME)
            .waitingTime(DEFAULT_WAITING_TIME)
            .extendTime(DEFAULT_EXTEND_TIME)
            .app(DEFAULT_APP);
        return scenes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scenes createUpdatedEntity(EntityManager em) {
        Scenes scenes = new Scenes()
            .name(UPDATED_NAME)
            .creater(UPDATED_CREATER)
            .type(UPDATED_TYPE)
            .createTime(UPDATED_CREATE_TIME)
            .waitingTime(UPDATED_WAITING_TIME)
            .extendTime(UPDATED_EXTEND_TIME)
            .app(UPDATED_APP);
        return scenes;
    }

    @BeforeEach
    public void initTest() {
        scenes = createEntity(em);
    }

    @Test
    @Transactional
    public void createScenes() throws Exception {
        int databaseSizeBeforeCreate = scenesRepository.findAll().size();

        // Create the Scenes
        ScenesDTO scenesDTO = scenesMapper.toDto(scenes);
        restScenesMockMvc.perform(post("/api/scenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scenesDTO)))
            .andExpect(status().isCreated());

        // Validate the Scenes in the database
        List<Scenes> scenesList = scenesRepository.findAll();
        assertThat(scenesList).hasSize(databaseSizeBeforeCreate + 1);
        Scenes testScenes = scenesList.get(scenesList.size() - 1);
        assertThat(testScenes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScenes.getCreater()).isEqualTo(DEFAULT_CREATER);
        assertThat(testScenes.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testScenes.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testScenes.getWaitingTime()).isEqualTo(DEFAULT_WAITING_TIME);
        assertThat(testScenes.getExtendTime()).isEqualTo(DEFAULT_EXTEND_TIME);
        assertThat(testScenes.getApp()).isEqualTo(DEFAULT_APP);
    }

    @Test
    @Transactional
    public void createScenesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scenesRepository.findAll().size();

        // Create the Scenes with an existing ID
        scenes.setId(1L);
        ScenesDTO scenesDTO = scenesMapper.toDto(scenes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScenesMockMvc.perform(post("/api/scenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scenesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scenes in the database
        List<Scenes> scenesList = scenesRepository.findAll();
        assertThat(scenesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScenes() throws Exception {
        // Initialize the database
        scenesRepository.saveAndFlush(scenes);

        // Get all the scenesList
        restScenesMockMvc.perform(get("/api/scenes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creater").value(hasItem(DEFAULT_CREATER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].waitingTime").value(hasItem(DEFAULT_WAITING_TIME.toString())))
            .andExpect(jsonPath("$.[*].extendTime").value(hasItem(DEFAULT_EXTEND_TIME)))
            .andExpect(jsonPath("$.[*].app").value(hasItem(DEFAULT_APP)));
    }
    
    @Test
    @Transactional
    public void getScenes() throws Exception {
        // Initialize the database
        scenesRepository.saveAndFlush(scenes);

        // Get the scenes
        restScenesMockMvc.perform(get("/api/scenes/{id}", scenes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scenes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creater").value(DEFAULT_CREATER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.waitingTime").value(DEFAULT_WAITING_TIME.toString()))
            .andExpect(jsonPath("$.extendTime").value(DEFAULT_EXTEND_TIME))
            .andExpect(jsonPath("$.app").value(DEFAULT_APP));
    }

    @Test
    @Transactional
    public void getNonExistingScenes() throws Exception {
        // Get the scenes
        restScenesMockMvc.perform(get("/api/scenes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScenes() throws Exception {
        // Initialize the database
        scenesRepository.saveAndFlush(scenes);

        int databaseSizeBeforeUpdate = scenesRepository.findAll().size();

        // Update the scenes
        Scenes updatedScenes = scenesRepository.findById(scenes.getId()).get();
        // Disconnect from session so that the updates on updatedScenes are not directly saved in db
        em.detach(updatedScenes);
        updatedScenes
            .name(UPDATED_NAME)
            .creater(UPDATED_CREATER)
            .type(UPDATED_TYPE)
            .createTime(UPDATED_CREATE_TIME)
            .waitingTime(UPDATED_WAITING_TIME)
            .extendTime(UPDATED_EXTEND_TIME)
            .app(UPDATED_APP);
        ScenesDTO scenesDTO = scenesMapper.toDto(updatedScenes);

        restScenesMockMvc.perform(put("/api/scenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scenesDTO)))
            .andExpect(status().isOk());

        // Validate the Scenes in the database
        List<Scenes> scenesList = scenesRepository.findAll();
        assertThat(scenesList).hasSize(databaseSizeBeforeUpdate);
        Scenes testScenes = scenesList.get(scenesList.size() - 1);
        assertThat(testScenes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScenes.getCreater()).isEqualTo(UPDATED_CREATER);
        assertThat(testScenes.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testScenes.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testScenes.getWaitingTime()).isEqualTo(UPDATED_WAITING_TIME);
        assertThat(testScenes.getExtendTime()).isEqualTo(UPDATED_EXTEND_TIME);
        assertThat(testScenes.getApp()).isEqualTo(UPDATED_APP);
    }

    @Test
    @Transactional
    public void updateNonExistingScenes() throws Exception {
        int databaseSizeBeforeUpdate = scenesRepository.findAll().size();

        // Create the Scenes
        ScenesDTO scenesDTO = scenesMapper.toDto(scenes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScenesMockMvc.perform(put("/api/scenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scenesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scenes in the database
        List<Scenes> scenesList = scenesRepository.findAll();
        assertThat(scenesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScenes() throws Exception {
        // Initialize the database
        scenesRepository.saveAndFlush(scenes);

        int databaseSizeBeforeDelete = scenesRepository.findAll().size();

        // Delete the scenes
        restScenesMockMvc.perform(delete("/api/scenes/{id}", scenes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scenes> scenesList = scenesRepository.findAll();
        assertThat(scenesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
