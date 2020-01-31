package com.pingsec.dev.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.pingsec.dev.config.DockerConfiguration;
import com.pingsec.dev.repository.ImagesRepository;
import com.pingsec.dev.service.dto.ImagesDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

// 获取本机镜像的中间件
@Service
public class DockerOptService {

    DockerConfiguration dockerConfiguration = new DockerConfiguration();

    DockerClient dockerClient = dockerConfiguration.getDockerClient();

    private ImagesRepository imagesRepository;
    private ImagesService imagesService;

    public DockerOptService(ImagesRepository imagesRepository, ImagesService imagesService) {
        this.imagesRepository = imagesRepository;
        this.imagesService = imagesService;
    }

    // 获取主机上所有镜像的数据
    private List<Image> getAllImage() {
        List<Image> images = dockerClient.listImagesCmd().withShowAll(true).exec();
        List<Image> result = new LinkedList<>();
        // 根据需要进行过滤
        for(Image i:images){
            if(i.getRepoTags().toString().contains("ctf")||i.getRepoTags().toString().contains("cve"))
                result.add(i);
        }
        return result;
    }

    // 5s同步一次数据
    @Scheduled(fixedRate=5000)
    public void syncImage(){
        List<Image> images = getAllImage();
        for(Image i:images){
            ImagesDTO imagesDTO = new ImagesDTO();
            Instant instant = Instant.now();
            // {type}-{power}-{author}-{title}:{version}
            String[] temp = i.getRepoTags().toString().split("-");
            imagesDTO.setCreater(temp[2]);
            imagesDTO.setName(temp[3]);
            // 描述的内容后续可以在前端进行追加
            imagesDTO.setDescription(temp[0]+"|"+temp[1]);
            // 本意是想即时更新，现在觉得也行吧，延迟最多5s
            imagesDTO.setCreatTime(instant);
            // 唯一值校验
            if(imagesRepository.findAllByHashCodeEquals(String.valueOf(imagesDTO.hashCode())).isEmpty()) {
                // 检查哈希值确定唯一性
                imagesDTO.setHashCode(String.valueOf(imagesDTO.hashCode()));
                imagesService.save(imagesDTO);
            }
        }
    }
}