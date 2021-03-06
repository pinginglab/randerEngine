package com.pingsec.dev.web.rest;

import com.pingsec.dev.service.NetworksService;
import com.pingsec.dev.web.rest.errors.BadRequestAlertException;
import com.pingsec.dev.service.dto.NetworksDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.pingsec.dev.domain.Networks}.
 */
@RestController
@RequestMapping("/api")
public class NetworksResource {

    private final Logger log = LoggerFactory.getLogger(NetworksResource.class);

    private static final String ENTITY_NAME = "randerEngineNetworks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetworksService networksService;

    public NetworksResource(NetworksService networksService) {
        this.networksService = networksService;
    }

    /**
     * {@code POST  /networks} : Create a new networks.
     *
     * @param networksDTO the networksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new networksDTO, or with status {@code 400 (Bad Request)} if the networks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/networks")
    public ResponseEntity<NetworksDTO> createNetworks(@RequestBody NetworksDTO networksDTO) throws URISyntaxException {
        log.debug("REST request to save Networks : {}", networksDTO);
        if (networksDTO.getId() != null) {
            throw new BadRequestAlertException("A new networks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetworksDTO result = networksService.save(networksDTO);
        return ResponseEntity.created(new URI("/api/networks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /networks} : Updates an existing networks.
     *
     * @param networksDTO the networksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated networksDTO,
     * or with status {@code 400 (Bad Request)} if the networksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the networksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/networks")
    public ResponseEntity<NetworksDTO> updateNetworks(@RequestBody NetworksDTO networksDTO) throws URISyntaxException {
        log.debug("REST request to update Networks : {}", networksDTO);
        if (networksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NetworksDTO result = networksService.save(networksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, networksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /networks} : get all the networks.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of networks in body.
     */
    @GetMapping("/networks")
    public ResponseEntity<List<NetworksDTO>> getAllNetworks(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("app-is-null".equals(filter)) {
            log.debug("REST request to get all Networkss where app is null");
            return new ResponseEntity<>(networksService.findAllWhereAppIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Networks");
        Page<NetworksDTO> page = networksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /networks/:id} : get the "id" networks.
     *
     * @param id the id of the networksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the networksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/networks/{id}")
    public ResponseEntity<NetworksDTO> getNetworks(@PathVariable Long id) {
        log.debug("REST request to get Networks : {}", id);
        Optional<NetworksDTO> networksDTO = networksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(networksDTO);
    }

    /**
     * {@code DELETE  /networks/:id} : delete the "id" networks.
     *
     * @param id the id of the networksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/networks/{id}")
    public ResponseEntity<Void> deleteNetworks(@PathVariable Long id) {
        log.debug("REST request to delete Networks : {}", id);
        networksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
