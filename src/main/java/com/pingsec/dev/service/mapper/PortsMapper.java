package com.pingsec.dev.service.mapper;

import com.pingsec.dev.domain.*;
import com.pingsec.dev.service.dto.PortsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ports} and its DTO {@link PortsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppMapper.class})
public interface PortsMapper extends EntityMapper<PortsDTO, Ports> {

    @Mapping(source = "app.id", target = "appId")
    PortsDTO toDto(Ports ports);

    @Mapping(source = "appId", target = "app")
    Ports toEntity(PortsDTO portsDTO);

    default Ports fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ports ports = new Ports();
        ports.setId(id);
        return ports;
    }
}
