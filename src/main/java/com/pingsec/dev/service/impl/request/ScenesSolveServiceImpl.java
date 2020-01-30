package com.pingsec.dev.service.impl.request;

import com.pingsec.dev.service.ScenesService;
import com.pingsec.dev.service.ScenesSolveService;
import com.pingsec.dev.service.dto.ScenesDTO;
import com.pingsec.dev.service.dto.request.ScenesSolveDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class ScenesSolveServiceImpl implements ScenesSolveService {
    private final ScenesService scenesService;

    public ScenesSolveServiceImpl(ScenesService scenesService) {
        this.scenesService = scenesService;
    }

    @Override
    public ScenesDTO scenesSolveToscenes(ScenesSolveDTO scenesSolveDTO) {
        ScenesDTO scenesDTO = new ScenesDTO();
        Instant instant = Instant.now();
        scenesDTO.setApp(scenesSolveDTO.getApp());
        scenesDTO.setName(scenesSolveDTO.getName());
        scenesDTO.setCreater(scenesSolveDTO.getCreater());
        scenesDTO.setType(scenesSolveDTO.getType());
        scenesDTO.setCreateTime(instant);
        // 默认有效期1小时
        scenesDTO.setWaitingTime(instant.plus(1, ChronoUnit.HOURS));
        return scenesDTO;
    }
}
