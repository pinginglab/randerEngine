package com.pingsec.dev.service.mapper;

import com.pingsec.dev.domain.*;
import com.pingsec.dev.service.dto.ScenesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scenes} and its DTO {@link ScenesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScenesMapper extends EntityMapper<ScenesDTO, Scenes> {


    @Mapping(target = "apps", ignore = true)
    @Mapping(target = "removeApp", ignore = true)
    Scenes toEntity(ScenesDTO scenesDTO);

    default Scenes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scenes scenes = new Scenes();
        scenes.setId(id);
        return scenes;
    }
}
