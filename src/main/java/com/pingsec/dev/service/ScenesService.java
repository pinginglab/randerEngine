package com.pingsec.dev.service;

import com.pingsec.dev.service.dto.ScenesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pingsec.dev.domain.Scenes}.
 */
public interface ScenesService {

    /**
     * Save a scenes.
     *
     * @param scenesDTO the entity to save.
     * @return the persisted entity.
     */
    ScenesDTO save(ScenesDTO scenesDTO);

    /**
     * Get all the scenes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScenesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" scenes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScenesDTO> findOne(Long id);

    /**
     * Delete the "id" scenes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
