package com.pingsec.dev.web.rest;

import com.pingsec.dev.service.ScenesService;
import com.pingsec.dev.web.rest.errors.BadRequestAlertException;
import com.pingsec.dev.service.dto.ScenesDTO;

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
 * REST controller for managing {@link com.pingsec.dev.domain.Scenes}.
 */
@RestController
@RequestMapping("/api")
public class ScenesResource {

    private final Logger log = LoggerFactory.getLogger(ScenesResource.class);

    private static final String ENTITY_NAME = "randerEngineScenes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScenesService scenesService;

    public ScenesResource(ScenesService scenesService) {
        this.scenesService = scenesService;
    }

    /**
     * {@code POST  /scenes} : Create a new scenes.
     *
     * @param scenesDTO the scenesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scenesDTO, or with status {@code 400 (Bad Request)} if the scenes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scenes")
    public ResponseEntity<ScenesDTO> createScenes(@RequestBody ScenesDTO scenesDTO) throws URISyntaxException {
        log.debug("REST request to save Scenes : {}", scenesDTO);
        if (scenesDTO.getId() != null) {
            throw new BadRequestAlertException("A new scenes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScenesDTO result = scenesService.save(scenesDTO);
        return ResponseEntity.created(new URI("/api/scenes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scenes} : Updates an existing scenes.
     *
     * @param scenesDTO the scenesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scenesDTO,
     * or with status {@code 400 (Bad Request)} if the scenesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scenesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scenes")
    public ResponseEntity<ScenesDTO> updateScenes(@RequestBody ScenesDTO scenesDTO) throws URISyntaxException {
        log.debug("REST request to update Scenes : {}", scenesDTO);
        if (scenesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScenesDTO result = scenesService.save(scenesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scenesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scenes} : get all the scenes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scenes in body.
     */
    @GetMapping("/scenes")
    public ResponseEntity<List<ScenesDTO>> getAllScenes(Pageable pageable) {
        log.debug("REST request to get a page of Scenes");
        Page<ScenesDTO> page = scenesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scenes/:id} : get the "id" scenes.
     *
     * @param id the id of the scenesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scenesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scenes/{id}")
    public ResponseEntity<ScenesDTO> getScenes(@PathVariable Long id) {
        log.debug("REST request to get Scenes : {}", id);
        Optional<ScenesDTO> scenesDTO = scenesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scenesDTO);
    }

    /**
     * {@code DELETE  /scenes/:id} : delete the "id" scenes.
     *
     * @param id the id of the scenesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scenes/{id}")
    public ResponseEntity<Void> deleteScenes(@PathVariable Long id) {
        log.debug("REST request to delete Scenes : {}", id);
        scenesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
