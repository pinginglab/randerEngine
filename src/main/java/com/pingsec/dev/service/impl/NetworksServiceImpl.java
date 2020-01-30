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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    *  Get all the networks where App is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<NetworksDTO> findAllWhereAppIsNull() {
        log.debug("Request to get all networks where App is null");
        return StreamSupport
            .stream(networksRepository.findAll().spliterator(), false)
            .filter(networks -> networks.getApp() == null)
            .map(networksMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
