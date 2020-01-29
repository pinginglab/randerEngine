package com.pingsec.dev.web.rest;

import com.pingsec.dev.RanderEngineApp;
import com.pingsec.dev.config.SecurityBeanOverrideConfiguration;
import com.pingsec.dev.domain.Images;
import com.pingsec.dev.repository.ImagesRepository;
import com.pingsec.dev.service.ImagesService;
import com.pingsec.dev.service.dto.ImagesDTO;
import com.pingsec.dev.service.mapper.ImagesMapper;
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
 * Integration tests for the {@link ImagesResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, RanderEngineApp.class})
public class ImagesResourceIT {

    private static final String DEFAULT_CREATER = "AAAAAAAAAA";
    private static final String UPDATED_CREATER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREAT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREAT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private ImagesService imagesService;

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

    private MockMvc restImagesMockMvc;

    private Images images;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImagesResource imagesResource = new ImagesResource(imagesService);
        this.restImagesMockMvc = MockMvcBuilders.standaloneSetup(imagesResource)
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
    public static Images createEntity(EntityManager em) {
        Images images = new Images()
            .creater(DEFAULT_CREATER)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .creatTime(DEFAULT_CREAT_TIME);
        return images;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Images createUpdatedEntity(EntityManager em) {
        Images images = new Images()
            .creater(UPDATED_CREATER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .creatTime(UPDATED_CREAT_TIME);
        return images;
    }

    @BeforeEach
    public void initTest() {
        images = createEntity(em);
    }

    @Test
    @Transactional
    public void createImages() throws Exception {
        int databaseSizeBeforeCreate = imagesRepository.findAll().size();

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);
        restImagesMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeCreate + 1);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getCreater()).isEqualTo(DEFAULT_CREATER);
        assertThat(testImages.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImages.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testImages.getCreatTime()).isEqualTo(DEFAULT_CREAT_TIME);
    }

    @Test
    @Transactional
    public void createImagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imagesRepository.findAll().size();

        // Create the Images with an existing ID
        images.setId(1L);
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagesMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList
        restImagesMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(images.getId().intValue())))
            .andExpect(jsonPath("$.[*].creater").value(hasItem(DEFAULT_CREATER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].creatTime").value(hasItem(DEFAULT_CREAT_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get the images
        restImagesMockMvc.perform(get("/api/images/{id}", images.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(images.getId().intValue()))
            .andExpect(jsonPath("$.creater").value(DEFAULT_CREATER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.creatTime").value(DEFAULT_CREAT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImages() throws Exception {
        // Get the images
        restImagesMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Update the images
        Images updatedImages = imagesRepository.findById(images.getId()).get();
        // Disconnect from session so that the updates on updatedImages are not directly saved in db
        em.detach(updatedImages);
        updatedImages
            .creater(UPDATED_CREATER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .creatTime(UPDATED_CREAT_TIME);
        ImagesDTO imagesDTO = imagesMapper.toDto(updatedImages);

        restImagesMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isOk());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getCreater()).isEqualTo(UPDATED_CREATER);
        assertThat(testImages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImages.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testImages.getCreatTime()).isEqualTo(UPDATED_CREAT_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagesMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        int databaseSizeBeforeDelete = imagesRepository.findAll().size();

        // Delete the images
        restImagesMockMvc.perform(delete("/api/images/{id}", images.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
