package com.pingsec.dev.web.rest;

import com.pingsec.dev.RanderEngineApp;
import com.pingsec.dev.config.SecurityBeanOverrideConfiguration;
import com.pingsec.dev.domain.App;
import com.pingsec.dev.repository.AppRepository;
import com.pingsec.dev.service.AppService;
import com.pingsec.dev.service.dto.AppDTO;
import com.pingsec.dev.service.mapper.AppMapper;
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
import java.util.List;

import static com.pingsec.dev.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, RanderEngineApp.class})
public class AppResourceIT {

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NETWORK = "AAAAAAAAAA";
    private static final String UPDATED_NETWORK = "BBBBBBBBBB";

    private static final String DEFAULT_PORT = "AAAAAAAAAA";
    private static final String UPDATED_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_ENVIROMENT = "AAAAAAAAAA";
    private static final String UPDATED_ENVIROMENT = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_OTHER = "BBBBBBBBBB";

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppService appService;

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

    private MockMvc restAppMockMvc;

    private App app;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppResource appResource = new AppResource(appService);
        this.restAppMockMvc = MockMvcBuilders.standaloneSetup(appResource)
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
    public static App createEntity(EntityManager em) {
        App app = new App()
            .image(DEFAULT_IMAGE)
            .network(DEFAULT_NETWORK)
            .port(DEFAULT_PORT)
            .volume(DEFAULT_VOLUME)
            .enviroment(DEFAULT_ENVIROMENT)
            .other(DEFAULT_OTHER);
        return app;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static App createUpdatedEntity(EntityManager em) {
        App app = new App()
            .image(UPDATED_IMAGE)
            .network(UPDATED_NETWORK)
            .port(UPDATED_PORT)
            .volume(UPDATED_VOLUME)
            .enviroment(UPDATED_ENVIROMENT)
            .other(UPDATED_OTHER);
        return app;
    }

    @BeforeEach
    public void initTest() {
        app = createEntity(em);
    }

    @Test
    @Transactional
    public void createApp() throws Exception {
        int databaseSizeBeforeCreate = appRepository.findAll().size();

        // Create the App
        AppDTO appDTO = appMapper.toDto(app);
        restAppMockMvc.perform(post("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isCreated());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeCreate + 1);
        App testApp = appList.get(appList.size() - 1);
        assertThat(testApp.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testApp.getNetwork()).isEqualTo(DEFAULT_NETWORK);
        assertThat(testApp.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testApp.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testApp.getEnviroment()).isEqualTo(DEFAULT_ENVIROMENT);
        assertThat(testApp.getOther()).isEqualTo(DEFAULT_OTHER);
    }

    @Test
    @Transactional
    public void createAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appRepository.findAll().size();

        // Create the App with an existing ID
        app.setId(1L);
        AppDTO appDTO = appMapper.toDto(app);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppMockMvc.perform(post("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isBadRequest());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApps() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        // Get all the appList
        restAppMockMvc.perform(get("/api/apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(app.getId().intValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].network").value(hasItem(DEFAULT_NETWORK)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)))
            .andExpect(jsonPath("$.[*].enviroment").value(hasItem(DEFAULT_ENVIROMENT.toString())))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER.toString())));
    }
    
    @Test
    @Transactional
    public void getApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        // Get the app
        restAppMockMvc.perform(get("/api/apps/{id}", app.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(app.getId().intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.network").value(DEFAULT_NETWORK))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME))
            .andExpect(jsonPath("$.enviroment").value(DEFAULT_ENVIROMENT.toString()))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApp() throws Exception {
        // Get the app
        restAppMockMvc.perform(get("/api/apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        int databaseSizeBeforeUpdate = appRepository.findAll().size();

        // Update the app
        App updatedApp = appRepository.findById(app.getId()).get();
        // Disconnect from session so that the updates on updatedApp are not directly saved in db
        em.detach(updatedApp);
        updatedApp
            .image(UPDATED_IMAGE)
            .network(UPDATED_NETWORK)
            .port(UPDATED_PORT)
            .volume(UPDATED_VOLUME)
            .enviroment(UPDATED_ENVIROMENT)
            .other(UPDATED_OTHER);
        AppDTO appDTO = appMapper.toDto(updatedApp);

        restAppMockMvc.perform(put("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isOk());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeUpdate);
        App testApp = appList.get(appList.size() - 1);
        assertThat(testApp.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testApp.getNetwork()).isEqualTo(UPDATED_NETWORK);
        assertThat(testApp.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testApp.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testApp.getEnviroment()).isEqualTo(UPDATED_ENVIROMENT);
        assertThat(testApp.getOther()).isEqualTo(UPDATED_OTHER);
    }

    @Test
    @Transactional
    public void updateNonExistingApp() throws Exception {
        int databaseSizeBeforeUpdate = appRepository.findAll().size();

        // Create the App
        AppDTO appDTO = appMapper.toDto(app);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppMockMvc.perform(put("/api/apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appDTO)))
            .andExpect(status().isBadRequest());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        int databaseSizeBeforeDelete = appRepository.findAll().size();

        // Delete the app
        restAppMockMvc.perform(delete("/api/apps/{id}", app.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
