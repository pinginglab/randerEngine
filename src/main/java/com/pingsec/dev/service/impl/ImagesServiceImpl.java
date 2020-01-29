package com.pingsec.dev.service.impl;

import com.pingsec.dev.service.ImagesService;
import com.pingsec.dev.domain.Images;
import com.pingsec.dev.repository.ImagesRepository;
import com.pingsec.dev.service.dto.ImagesDTO;
import com.pingsec.dev.service.mapper.ImagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Images}.
 */
@Service
@Transactional
public class ImagesServiceImpl implements ImagesService {

    private final Logger log = LoggerFactory.getLogger(ImagesServiceImpl.class);

    private final ImagesRepository imagesRepository;

    private final ImagesMapper imagesMapper;

    public ImagesServiceImpl(ImagesRepository imagesRepository, ImagesMapper imagesMapper) {
        this.imagesRepository = imagesRepository;
        this.imagesMapper = imagesMapper;
    }

    /**
     * Save a images.
     *
     * @param imagesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImagesDTO save(ImagesDTO imagesDTO) {
        log.debug("Request to save Images : {}", imagesDTO);
        Images images = imagesMapper.toEntity(imagesDTO);
        images = imagesRepository.save(images);
        return imagesMapper.toDto(images);
    }

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imagesRepository.findAll(pageable)
            .map(imagesMapper::toDto);
    }


    /**
     * Get one images by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImagesDTO> findOne(Long id) {
        log.debug("Request to get Images : {}", id);
        return imagesRepository.findById(id)
            .map(imagesMapper::toDto);
    }

    /**
     * Delete the images by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Images : {}", id);
        imagesRepository.deleteById(id);
    }
}
