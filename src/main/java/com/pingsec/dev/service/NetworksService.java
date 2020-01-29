package com.pingsec.dev.service;

import com.pingsec.dev.service.dto.NetworksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pingsec.dev.domain.Networks}.
 */
public interface NetworksService {

    /**
     * Save a networks.
     *
     * @param networksDTO the entity to save.
     * @return the persisted entity.
     */
    NetworksDTO save(NetworksDTO networksDTO);

    /**
     * Get all the networks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NetworksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" networks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NetworksDTO> findOne(Long id);

    /**
     * Delete the "id" networks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
