package com.pingsec.dev.service;

import com.pingsec.dev.service.dto.PortsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pingsec.dev.domain.Ports}.
 */
public interface PortsService {

    /**
     * Save a ports.
     *
     * @param portsDTO the entity to save.
     * @return the persisted entity.
     */
    PortsDTO save(PortsDTO portsDTO);

    /**
     * Get all the ports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PortsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ports.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PortsDTO> findOne(Long id);

    /**
     * Delete the "id" ports.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
