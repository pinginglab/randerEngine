package com.pingsec.dev.service.mapper;

import com.pingsec.dev.domain.*;
import com.pingsec.dev.service.dto.AppDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link App} and its DTO {@link AppDTO}.
 */
@Mapper(componentModel = "spring", uses = {NetworksMapper.class, ImagesMapper.class, ScenesMapper.class})
public interface AppMapper extends EntityMapper<AppDTO, App> {

    @Mapping(source = "networks.id", target = "networksId")
    @Mapping(source = "images.id", target = "imagesId")
    @Mapping(source = "scenes.id", target = "scenesId")
    AppDTO toDto(App app);

    @Mapping(source = "networksId", target = "networks")
    @Mapping(source = "imagesId", target = "images")
    @Mapping(target = "ports", ignore = true)
    @Mapping(target = "removePorts", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(source = "scenesId", target = "scenes")
    App toEntity(AppDTO appDTO);

    default App fromId(Long id) {
        if (id == null) {
            return null;
        }
        App app = new App();
        app.setId(id);
        return app;
    }
}
