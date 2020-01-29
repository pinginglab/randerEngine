package com.pingsec.dev.service.impl;

import com.pingsec.dev.service.NetworksService;
import com.pingsec.dev.domain.Networks;
import com.pingsec.dev.repository.NetworksRepository;
import com.pingsec.dev.service.dto.NetworksDTO;
import com.pingsec.dev.service.mapper.NetworksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Networks}.
 */
@Service
@Transactional
public class NetworksServiceImpl implements NetworksService {

    private final Logger log = LoggerFactory.getLogger(NetworksServiceImpl.class);

    private final NetworksRepository networksRepository;

    private final NetworksMapper networksMapper;

    public NetworksServiceImpl(NetworksRepository networksRepository, NetworksMapper networksMapper) {
        this.networksRepository = networksRepository;
        this.networksMapper = networksMapper;
    }

    /**
     * Save a networks.
     *
     * @param networksDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NetworksDTO save(NetworksDTO networksDTO) {
        log.debug("Request to save Networks : {}", networksDTO);
        Networks networks = networksMapper.toEntity(networksDTO);
        networks = networksRepository.save(networks);
        return networksMapper.toDto(networks);
    }

    /**
     * Get all the networks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NetworksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Networks");
        return networksRepository.findAll(pageable)
            .map(networksMapper::toDto);
    }


    /**
     * Get one networks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NetworksDTO> findOne(Long id) {
        log.debug("Request to get Networks : {}", id);
        return networksRepository.findById(id)
            .map(networksMapper::toDto);
    }

    /**
     * Delete the networks by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Networks : {}", id);
        networksRepository.deleteById(id);
    }
}
