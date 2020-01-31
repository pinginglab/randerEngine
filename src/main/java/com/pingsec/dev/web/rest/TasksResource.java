package com.pingsec.dev.web.rest;

import com.pingsec.dev.service.TasksService;
import com.pingsec.dev.service.dto.TasksDTO;
import com.pingsec.dev.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pingsec.dev.domain.Tasks}.
 */
@RestController
@RequestMapping("/api")
public class TasksResource {

    private final Logger log = LoggerFactory.getLogger(TasksResource.class);

    private static final String ENTITY_NAME = "randerEngineTasks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TasksService tasksService;

    public TasksResource(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    /**
     * {@code POST  /tasks} : Create a new tasks.
     *
     * @param tasksDTO the tasksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tasksDTO, or with status {@code 400 (Bad Request)} if the tasks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasks")
    public ResponseEntity<TasksDTO> createTasks(@RequestBody TasksDTO tasksDTO) throws URISyntaxException {
        log.debug("REST request to save Tasks : {}", tasksDTO);
        if (tasksDTO.getId() != null) {
            throw new BadRequestAlertException("A new tasks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TasksDTO result = tasksService.save(tasksDTO);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks} : Updates an existing tasks.
     *
     * @param tasksDTO the tasksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tasksDTO,
     * or with status {@code 400 (Bad Request)} if the tasksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tasksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks")
    public ResponseEntity<TasksDTO> updateTasks(@RequestBody TasksDTO tasksDTO) throws URISyntaxException {
        log.debug("REST request to update Tasks : {}", tasksDTO);
        if (tasksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TasksDTO result = tasksService.save(tasksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tasksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tasks} : get all the tasks.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasks in body.
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<TasksDTO>> getAllTasks(Pageable pageable) {
        log.debug("REST request to get a page of Tasks");
        Page<TasksDTO> page = tasksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tasks/:id} : get the "id" tasks.
     *
     * @param id the id of the tasksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tasksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TasksDTO> getTasks(@PathVariable Long id) {
        log.debug("REST request to get Tasks : {}", id);
        Optional<TasksDTO> tasksDTO = tasksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tasksDTO);
    }

    /**
     * {@code DELETE  /tasks/:id} : delete the "id" tasks.
     *
     * @param id the id of the tasksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTasks(@PathVariable Long id) {
        log.debug("REST request to delete Tasks : {}", id);
        tasksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
