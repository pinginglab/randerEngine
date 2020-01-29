package com.pingsec.dev.web.rest;

import com.pingsec.dev.RanderEngineApp;
import com.pingsec.dev.config.SecurityBeanOverrideConfiguration;
import com.pingsec.dev.domain.Ports;
import com.pingsec.dev.repository.PortsRepository;
import com.pingsec.dev.service.PortsService;
import com.pingsec.dev.service.dto.PortsDTO;
import com.pingsec.dev.service.mapper.PortsMapper;
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
 * Integration tests for the {@link PortsResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, RanderEngineApp.class})
public class PortsResourceIT {

    private static final String DEFAULT_HOST_PORT = "AAAAAAAAAA";
    private static final String UPDATED_HOST_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTAINER_PORT = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINER_PORT = "BBBBBBBBBB";

    @Autowired
    private PortsRepository portsRepository;

    @Autowired
    private PortsMapper portsMapper;

    @Autowired
    private PortsService portsService;

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

    private MockMvc restPortsMockMvc;

    private Ports ports;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PortsResource portsResource = new PortsResource(portsService);
        this.restPortsMockMvc = MockMvcBuilders.standaloneSetup(portsResource)
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
    public static Ports createEntity(EntityManager em) {
        Ports ports = new Ports()
            .hostPort(DEFAULT_HOST_PORT)
            .containerPort(DEFAULT_CONTAINER_PORT);
        return ports;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ports createUpdatedEntity(EntityManager em) {
        Ports ports = new Ports()
            .hostPort(UPDATED_HOST_PORT)
            .containerPort(UPDATED_CONTAINER_PORT);
        return ports;
    }

    @BeforeEach
    public void initTest() {
        ports = createEntity(em);
    }

    @Test
    @Transactional
    public void createPorts() throws Exception {
        int databaseSizeBeforeCreate = portsRepository.findAll().size();

        // Create the Ports
        PortsDTO portsDTO = portsMapper.toDto(ports);
        restPortsMockMvc.perform(post("/api/ports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portsDTO)))
            .andExpect(status().isCreated());

        // Validate the Ports in the database
        List<Ports> portsList = portsRepository.findAll();
        assertThat(portsList).hasSize(databaseSizeBeforeCreate + 1);
        Ports testPorts = portsList.get(portsList.size() - 1);
        assertThat(testPorts.getHostPort()).isEqualTo(DEFAULT_HOST_PORT);
        assertThat(testPorts.getContainerPort()).isEqualTo(DEFAULT_CONTAINER_PORT);
    }

    @Test
    @Transactional
    public void createPortsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = portsRepository.findAll().size();

        // Create the Ports with an existing ID
        ports.setId(1L);
        PortsDTO portsDTO = portsMapper.toDto(ports);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortsMockMvc.perform(post("/api/ports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ports in the database
        List<Ports> portsList = portsRepository.findAll();
        assertThat(portsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPorts() throws Exception {
        // Initialize the database
        portsRepository.saveAndFlush(ports);

        // Get all the portsList
        restPortsMockMvc.perform(get("/api/ports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ports.getId().intValue())))
            .andExpect(jsonPath("$.[*].hostPort").value(hasItem(DEFAULT_HOST_PORT)))
            .andExpect(jsonPath("$.[*].containerPort").value(hasItem(DEFAULT_CONTAINER_PORT)));
    }
    
    @Test
    @Transactional
    public void getPorts() throws Exception {
        // Initialize the database
        portsRepository.saveAndFlush(ports);

        // Get the ports
        restPortsMockMvc.perform(get("/api/ports/{id}", ports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ports.getId().intValue()))
            .andExpect(jsonPath("$.hostPort").value(DEFAULT_HOST_PORT))
            .andExpect(jsonPath("$.containerPort").value(DEFAULT_CONTAINER_PORT));
    }

    @Test
    @Transactional
    public void getNonExistingPorts() throws Exception {
        // Get the ports
        restPortsMockMvc.perform(get("/api/ports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePorts() throws Exception {
        // Initialize the database
        portsRepository.saveAndFlush(ports);

        int databaseSizeBeforeUpdate = portsRepository.findAll().size();

        // Update the ports
        Ports updatedPorts = portsRepository.findById(ports.getId()).get();
        // Disconnect from session so that the updates on updatedPorts are not directly saved in db
        em.detach(updatedPorts);
        updatedPorts
            .hostPort(UPDATED_HOST_PORT)
            .containerPort(UPDATED_CONTAINER_PORT);
        PortsDTO portsDTO = portsMapper.toDto(updatedPorts);

        restPortsMockMvc.perform(put("/api/ports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portsDTO)))
            .andExpect(status().isOk());

        // Validate the Ports in the database
        List<Ports> portsList = portsRepository.findAll();
        assertThat(portsList).hasSize(databaseSizeBeforeUpdate);
        Ports testPorts = portsList.get(portsList.size() - 1);
        assertThat(testPorts.getHostPort()).isEqualTo(UPDATED_HOST_PORT);
        assertThat(testPorts.getContainerPort()).isEqualTo(UPDATED_CONTAINER_PORT);
    }

    @Test
    @Transactional
    public void updateNonExistingPorts() throws Exception {
        int databaseSizeBeforeUpdate = portsRepository.findAll().size();

        // Create the Ports
        PortsDTO portsDTO = portsMapper.toDto(ports);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortsMockMvc.perform(put("/api/ports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ports in the database
        List<Ports> portsList = portsRepository.findAll();
        assertThat(portsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePorts() throws Exception {
        // Initialize the database
        portsRepository.saveAndFlush(ports);

        int databaseSizeBeforeDelete = portsRepository.findAll().size();

        // Delete the ports
        restPortsMockMvc.perform(delete("/api/ports/{id}", ports.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ports> portsList = portsRepository.findAll();
        assertThat(portsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
