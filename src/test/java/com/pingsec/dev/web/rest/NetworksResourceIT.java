package com.pingsec.dev.web.rest;

import com.pingsec.dev.RanderEngineApp;
import com.pingsec.dev.config.SecurityBeanOverrideConfiguration;
import com.pingsec.dev.domain.Networks;
import com.pingsec.dev.repository.NetworksRepository;
import com.pingsec.dev.service.NetworksService;
import com.pingsec.dev.service.dto.NetworksDTO;
import com.pingsec.dev.service.mapper.NetworksMapper;
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
import java.util.List;

import static com.pingsec.dev.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NetworksResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, RanderEngineApp.class})
public class NetworksResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_OTHER = "BBBBBBBBBB";

    @Autowired
    private NetworksRepository networksRepository;

    @Autowired
    private NetworksMapper networksMapper;

    @Autowired
    private NetworksService networksService;

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

    private MockMvc restNetworksMockMvc;

    private Networks networks;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NetworksResource networksResource = new NetworksResource(networksService);
        this.restNetworksMockMvc = MockMvcBuilders.standaloneSetup(networksResource)
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
    public static Networks createEntity(EntityManager em) {
        Networks networks = new Networks()
            .type(DEFAULT_TYPE)
            .other(DEFAULT_OTHER);
        return networks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Networks createUpdatedEntity(EntityManager em) {
        Networks networks = new Networks()
            .type(UPDATED_TYPE)
            .other(UPDATED_OTHER);
        return networks;
    }

    @BeforeEach
    public void initTest() {
        networks = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetworks() throws Exception {
        int databaseSizeBeforeCreate = networksRepository.findAll().size();

        // Create the Networks
        NetworksDTO networksDTO = networksMapper.toDto(networks);
        restNetworksMockMvc.perform(post("/api/networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networksDTO)))
            .andExpect(status().isCreated());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeCreate + 1);
        Networks testNetworks = networksList.get(networksList.size() - 1);
        assertThat(testNetworks.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNetworks.getOther()).isEqualTo(DEFAULT_OTHER);
    }

    @Test
    @Transactional
    public void createNetworksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = networksRepository.findAll().size();

        // Create the Networks with an existing ID
        networks.setId(1L);
        NetworksDTO networksDTO = networksMapper.toDto(networks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworksMockMvc.perform(post("/api/networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNetworks() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        // Get all the networksList
        restNetworksMockMvc.perform(get("/api/networks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networks.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER)));
    }
    
    @Test
    @Transactional
    public void getNetworks() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        // Get the networks
        restNetworksMockMvc.perform(get("/api/networks/{id}", networks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(networks.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER));
    }

    @Test
    @Transactional
    public void getNonExistingNetworks() throws Exception {
        // Get the networks
        restNetworksMockMvc.perform(get("/api/networks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetworks() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        int databaseSizeBeforeUpdate = networksRepository.findAll().size();

        // Update the networks
        Networks updatedNetworks = networksRepository.findById(networks.getId()).get();
        // Disconnect from session so that the updates on updatedNetworks are not directly saved in db
        em.detach(updatedNetworks);
        updatedNetworks
            .type(UPDATED_TYPE)
            .other(UPDATED_OTHER);
        NetworksDTO networksDTO = networksMapper.toDto(updatedNetworks);

        restNetworksMockMvc.perform(put("/api/networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networksDTO)))
            .andExpect(status().isOk());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
        Networks testNetworks = networksList.get(networksList.size() - 1);
        assertThat(testNetworks.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNetworks.getOther()).isEqualTo(UPDATED_OTHER);
    }

    @Test
    @Transactional
    public void updateNonExistingNetworks() throws Exception {
        int databaseSizeBeforeUpdate = networksRepository.findAll().size();

        // Create the Networks
        NetworksDTO networksDTO = networksMapper.toDto(networks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworksMockMvc.perform(put("/api/networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Networks in the database
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNetworks() throws Exception {
        // Initialize the database
        networksRepository.saveAndFlush(networks);

        int databaseSizeBeforeDelete = networksRepository.findAll().size();

        // Delete the networks
        restNetworksMockMvc.perform(delete("/api/networks/{id}", networks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Networks> networksList = networksRepository.findAll();
        assertThat(networksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
