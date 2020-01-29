package com.pingsec.dev.service.impl;

import com.pingsec.dev.service.PortsService;
import com.pingsec.dev.domain.Ports;
import com.pingsec.dev.repository.PortsRepository;
import com.pingsec.dev.service.dto.PortsDTO;
import com.pingsec.dev.service.mapper.PortsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ports}.
 */
@Service
@Transactional
public class PortsServiceImpl implements PortsService {

    private final Logger log = LoggerFactory.getLogger(PortsServiceImpl.class);

    private final PortsRepository portsRepository;

    private final PortsMapper portsMapper;

    public PortsServiceImpl(PortsRepository portsRepository, PortsMapper portsMapper) {
        this.portsRepository = portsRepository;
        this.portsMapper = portsMapper;
    }

    /**
     * Save a ports.
     *
     * @param portsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PortsDTO save(PortsDTO portsDTO) {
        log.debug("Request to save Ports : {}", portsDTO);
        Ports ports = portsMapper.toEntity(portsDTO);
        ports = portsRepository.save(ports);
        return portsMapper.toDto(ports);
    }

    /**
     * Get all the ports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PortsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ports");
        return portsRepository.findAll(pageable)
            .map(portsMapper::toDto);
    }


    /**
     * Get one ports by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PortsDTO> findOne(Long id) {
        log.debug("Request to get Ports : {}", id);
        return portsRepository.findById(id)
            .map(portsMapper::toDto);
    }

    /**
     * Delete the ports by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ports : {}", id);
        portsRepository.deleteById(id);
    }
}
