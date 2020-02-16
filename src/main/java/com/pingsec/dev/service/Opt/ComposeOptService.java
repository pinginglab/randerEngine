package com.pingsec.dev.service.Opt;

import com.pingsec.dev.repository.TasksRepository;
import com.pingsec.dev.service.AppService;
import com.pingsec.dev.service.TasksService;
import org.springframework.stereotype.Service;

// 作为docker-compose脚本生产工具
// 将直接和rancher进行交互工作
// TODO: 后续需要进行重构，成为“作坊”的核心组成部分
@Service
public class ComposeOptService {
    private AppService appService;
    private TasksService tasksService;
    private TasksRepository tasksRepository;

    public ComposeOptService(AppService appService,
                             TasksService tasksService,
                             TasksRepository tasksRepository) {
        this.appService = appService;
        this.tasksService = tasksService;
        this.tasksRepository = tasksRepository;
    }

    // 5s同步一次数据
//    @Scheduled(fixedRate=5000)
//    public void buildCompose(){
//        List<Tasks> tasksList = tasksRepository.findAllByContentNull();
//        for(Tasks i:tasksList){
//
//        }
//    }
}
