package com.pingsec.dev.service.impl;

import com.pingsec.dev.service.ScenesService;
import com.pingsec.dev.domain.Scenes;
import com.pingsec.dev.repository.ScenesRepository;
import com.pingsec.dev.service.dto.ScenesDTO;
import com.pingsec.dev.service.mapper.ScenesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Scenes}.
 */
@Service
@Transactional
public class ScenesServiceImpl implements ScenesService {

    private final Logger log = LoggerFactory.getLogger(ScenesServiceImpl.class);

    private final ScenesRepository scenesRepository;

    private final ScenesMapper scenesMapper;

    public ScenesServiceImpl(ScenesRepository scenesRepository, ScenesMapper scenesMapper) {
        this.scenesRepository = scenesRepository;
        this.scenesMapper = scenesMapper;
    }

    /**
     * Save a scenes.
     *
     * @param scenesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ScenesDTO save(ScenesDTO scenesDTO) {
        log.debug("Request to save Scenes : {}", scenesDTO);
        Scenes scenes = scenesMapper.toEntity(scenesDTO);
        scenes = scenesRepository.save(scenes);
        return scenesMapper.toDto(scenes);
    }

    /**
     * Get all the scenes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScenesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Scenes");
        return scenesRepository.findAll(pageable)
            .map(scenesMapper::toDto);
    }


    /**
     * Get one scenes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScenesDTO> findOne(Long id) {
        log.debug("Request to get Scenes : {}", id);
        return scenesRepository.findById(id)
            .map(scenesMapper::toDto);
    }

    /**
     * Delete the scenes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scenes : {}", id);
        scenesRepository.deleteById(id);
    }
}
