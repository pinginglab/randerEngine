package com.pingsec.dev.service;

import com.pingsec.dev.service.dto.AppDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pingsec.dev.domain.App}.
 */
public interface AppService {

    /**
     * Save a app.
     *
     * @param appDTO the entity to save.
     * @return the persisted entity.
     */
    AppDTO save(AppDTO appDTO);

    /**
     * Get all the apps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppDTO> findAll(Pageable pageable);
    /**
     * Get all the AppDTO where Tasks is {@code null}.
     *
     * @return the list of entities.
     */
    List<AppDTO> findAllWhereTasksIsNull();


    /**
     * Get the "id" app.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppDTO> findOne(Long id);

    /**
     * Delete the "id" app.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
