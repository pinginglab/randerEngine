package com.pingsec.dev.service.mapper;

import com.pingsec.dev.domain.*;
import com.pingsec.dev.service.dto.NetworksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Networks} and its DTO {@link NetworksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NetworksMapper extends EntityMapper<NetworksDTO, Networks> {



    default Networks fromId(Long id) {
        if (id == null) {
            return null;
        }
        Networks networks = new Networks();
        networks.setId(id);
        return networks;
    }
}
