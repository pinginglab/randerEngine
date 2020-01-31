package com.pingsec.dev.service.impl.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pingsec.dev.domain.Images;
import com.pingsec.dev.repository.ImagesRepository;
import com.pingsec.dev.service.*;
import com.pingsec.dev.service.dto.*;
import com.pingsec.dev.service.dto.request.ScenesSolveDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Optional;

// 解析用户创建环境的请求
// 端口生成由系统自动指定，网络环境和镜像由用户指定
// 用户提交创建场景的需求之后，并不会理解创建成功，需要提交表单到后台，记录到数据库中
// 至于对于是否做持久化处理，需要后续根据业务再考虑
@Service
@Transactional
public class ScenesSolveServiceImpl implements ScenesSolveService {
    private final Logger log = LoggerFactory.getLogger(ScenesSolveServiceImpl.class);
    private final PortsService portsService;
    private final NetworksService networksService;
    private final AppService appService;
    private final TasksService tasksService;

    private ImagesRepository imagesRepository;

    private Gson gson = new Gson();

    Type typeOfMapString = new TypeToken<HashMap<String, String>>() {}.getType();

    public ScenesSolveServiceImpl(PortsService portsService,
                                  NetworksService networksService,
                                  AppService appService,
                                  TasksService tasksService,
                                  ImagesRepository imagesRepository) {
        this.portsService = portsService;
        this.networksService = networksService;
        this.appService = appService;
        this.tasksService = tasksService;
        this.imagesRepository = imagesRepository;
    }

    @Override
    public ScenesDTO scenesSolveToscenes(ScenesSolveDTO scenesSolveDTO) {
        ScenesDTO scenesDTO = new ScenesDTO();
        NetworksDTO networksDTO = new NetworksDTO();
        PortsDTO portsDTO = new PortsDTO();
        AppDTO appDTO = new AppDTO();
        TasksDTO tasksDTO = new TasksDTO();
        Instant instant = Instant.now();
        HashMap<String, String> bodyMap = gson.fromJson(scenesSolveDTO.getBody(), typeOfMapString);

        // 查询所需要的镜像是否存在
        String imageName = scenesSolveDTO.getImage();
        Optional<Images> imagesOptional = imagesRepository.findFirstByName(imageName);
        if (imagesOptional.isPresent()) {
            // 如果查询不到这个镜像则结束这个请求
            log.info("创建的镜像不存在： {}", imageName);
            return null;
        } else {
            // 默认有效期1小时
            scenesDTO.setWaitingTime(instant.plus(1, ChronoUnit.HOURS));
            // app对应的是应用的名字，目前属于冗余的字段
            scenesDTO.setApp(scenesSolveDTO.getApp());
            scenesDTO.setName(scenesSolveDTO.getName());
            scenesDTO.setCreater(scenesSolveDTO.getCreater());
            scenesDTO.setType(scenesSolveDTO.getType());
            scenesDTO.setCreateTime(instant);
            appDTO.setImage(imagesOptional.get().getName());
            appDTO.setScenesId(scenesDTO.getId());
            appDTO.setPort(bodyMap.get("hostPort"));
            appDTO.setEnvironment(bodyMap.get("environment"));
            networksDTO.setType(scenesSolveDTO.getNetwork());
            networksService.save(networksDTO);
            appDTO.setNetworksId(networksDTO.getId());
            // 用户指定将要映射的端口
            portsDTO.setHostPort(bodyMap.get("hostPort"));
            portsDTO.setAppId(appDTO.getId());
            portsDTO.setContainerPort(imagesOptional.get().getPort());
            portsService.save(portsDTO);
            tasksDTO.setCreateTime(instant);
            // 记录是谁的提交的单
            tasksDTO.setName(scenesSolveDTO.getCreater());
            tasksDTO.setAppId(appDTO.getId());
            tasksService.save(tasksDTO);
            appService.save(appDTO);
            return scenesDTO;
        }
    }
}
