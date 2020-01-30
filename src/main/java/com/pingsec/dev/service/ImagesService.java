package com.pingsec.dev.service;

import com.pingsec.dev.service.dto.ImagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pingsec.dev.domain.Images}.
 */
public interface ImagesService {

    /**
     * Save a images.
     *
     * @param imagesDTO the entity to save.
     * @return the persisted entity.
     */
    ImagesDTO save(ImagesDTO imagesDTO);

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImagesDTO> findAll(Pageable pageable);
    /**
     * Get all the ImagesDTO where App is {@code null}.
     *
     * @return the list of entities.
     */
    List<ImagesDTO> findAllWhereAppIsNull();


    /**
     * Get the "id" images.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImagesDTO> findOne(Long id);

    /**
     * Delete the "id" images.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
