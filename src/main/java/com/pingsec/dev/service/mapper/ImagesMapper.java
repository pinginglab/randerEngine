package com.pingsec.dev.service.mapper;

import com.pingsec.dev.domain.*;
import com.pingsec.dev.service.dto.ImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Images} and its DTO {@link ImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImagesMapper extends EntityMapper<ImagesDTO, Images> {


    @Mapping(target = "app", ignore = true)
    Images toEntity(ImagesDTO imagesDTO);

    default Images fromId(Long id) {
        if (id == null) {
            return null;
        }
        Images images = new Images();
        images.setId(id);
        return images;
    }
}
