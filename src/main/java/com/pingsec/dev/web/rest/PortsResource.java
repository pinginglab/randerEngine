package com.pingsec.dev.web.rest;

import com.pingsec.dev.service.PortsService;
import com.pingsec.dev.web.rest.errors.BadRequestAlertException;
import com.pingsec.dev.service.dto.PortsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pingsec.dev.domain.Ports}.
 */
@RestController
@RequestMapping("/api")
public class PortsResource {

    private final Logger log = LoggerFactory.getLogger(PortsResource.class);

    private static final String ENTITY_NAME = "randerEnginePorts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortsService portsService;

    public PortsResource(PortsService portsService) {
        this.portsService = portsService;
    }

    /**
     * {@code POST  /ports} : Create a new ports.
     *
     * @param portsDTO the portsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portsDTO, or with status {@code 400 (Bad Request)} if the ports has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ports")
    public ResponseEntity<PortsDTO> createPorts(@RequestBody PortsDTO portsDTO) throws URISyntaxException {
        log.debug("REST request to save Ports : {}", portsDTO);
        if (portsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortsDTO result = portsService.save(portsDTO);
        return ResponseEntity.created(new URI("/api/ports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ports} : Updates an existing ports.
     *
     * @param portsDTO the portsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portsDTO,
     * or with status {@code 400 (Bad Request)} if the portsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ports")
    public ResponseEntity<PortsDTO> updatePorts(@RequestBody PortsDTO portsDTO) throws URISyntaxException {
        log.debug("REST request to update Ports : {}", portsDTO);
        if (portsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PortsDTO result = portsService.save(portsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ports} : get all the ports.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ports in body.
     */
    @GetMapping("/ports")
    public ResponseEntity<List<PortsDTO>> getAllPorts(Pageable pageable) {
        log.debug("REST request to get a page of Ports");
        Page<PortsDTO> page = portsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ports/:id} : get the "id" ports.
     *
     * @param id the id of the portsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ports/{id}")
    public ResponseEntity<PortsDTO> getPorts(@PathVariable Long id) {
        log.debug("REST request to get Ports : {}", id);
        Optional<PortsDTO> portsDTO = portsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(portsDTO);
    }

    /**
     * {@code DELETE  /ports/:id} : delete the "id" ports.
     *
     * @param id the id of the portsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ports/{id}")
    public ResponseEntity<Void> deletePorts(@PathVariable Long id) {
        log.debug("REST request to delete Ports : {}", id);
        portsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
