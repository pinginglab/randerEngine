package com.pingsec.dev.service.mapper;

import com.pingsec.dev.domain.*;
import com.pingsec.dev.service.dto.AppDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link App} and its DTO {@link AppDTO}.
 */
@Mapper(componentModel = "spring", uses = {NetworksMapper.class, ImagesMapper.class, ScenesMapper.class})
public interface AppMapper extends EntityMapper<AppDTO, App> {

    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "image.id", target = "imageId")
    @Mapping(source = "scenes.id", target = "scenesId")
    AppDTO toDto(App app);

    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "imageId", target = "image")
    @Mapping(target = "ports", ignore = true)
    @Mapping(target = "removePort", ignore = true)
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
