package com.pingsec.dev.service;

import com.pingsec.dev.service.dto.TasksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pingsec.dev.domain.Tasks}.
 */
public interface TasksService {

    /**
     * Save a tasks.
     *
     * @param tasksDTO the entity to save.
     * @return the persisted entity.
     */
    TasksDTO save(TasksDTO tasksDTO);

    /**
     * Get all the tasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TasksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tasks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TasksDTO> findOne(Long id);

    /**
     * Delete the "id" tasks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
