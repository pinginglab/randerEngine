package com.pingsec.dev.service.Opt;

import com.pingsec.dev.repository.ImagesRepository;
import com.pingsec.dev.service.ImagesService;
import com.pingsec.dev.service.dto.request.DockerRegistryListDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// 获取本机镜像的中间件
// 2020-01-31 代码逻辑完成，由于直接操作docker-regrister有些危险
//
@Service
public class DockerOptService {
    private RestTemplate restTemplate;

//    DockerClient dockerClient = dockerConfiguration.getDockerClient();

    private ImagesRepository imagesRepository;
    private ImagesService imagesService;

    public DockerOptService(ImagesRepository imagesRepository, ImagesService imagesService) {
        this.imagesRepository = imagesRepository;
        this.imagesService = imagesService;
    }

    // 获取主机上所有镜像的数据
    private void getAllImage() {
        DockerRegistryListDTO dockerRegistryListDTO = restTemplate.getForObject("http://127.0.0.1:5000/v2/_catalog", DockerRegistryListDTO.class);
        for(String i:dockerRegistryListDTO.getRepositoriesList()){
            System.out.println(i);
        }
    }

    // 5s同步一次数据
    @Scheduled(fixedRate=5000)
    public void syncImage(){
        getAllImage();
//        List<Image> images = getAllImage();
//        for(Image i:images){
//            ImagesDTO imagesDTO = new ImagesDTO();
//            Instant instant = Instant.now();
//            // {type}-{power}-{author}-{title}:{version}
//            String[] temp = i.getRepoTags().toString().split("-");
//            imagesDTO.setCreater(temp[2]);
//            imagesDTO.setName(temp[3]);
//            // 描述的内容后续可以在前端进行追加
//            imagesDTO.setDescription(temp[0]+"|"+temp[1]);
//            // 本意是想即时更新，现在觉得也行吧，延迟最多5s
//            imagesDTO.setCreatTime(instant);
//            // 唯一值校验
//            if(imagesRepository.findAllByHashCodeEquals(String.valueOf(imagesDTO.hashCode())).isEmpty()) {
//                // 检查哈希值确定唯一性
//                imagesDTO.setHashCode(String.valueOf(imagesDTO.hashCode()));
//                imagesService.save(imagesDTO);
//            }
//        }
    }
}
