package com.pingsec.dev.service.Opt;

import com.pingsec.dev.service.ImagesService;
import com.pingsec.dev.service.NetworksService;
import com.pingsec.dev.service.PortsService;
import com.pingsec.dev.service.ScenesService;
import org.springframework.stereotype.Service;

// 作为docker-compose脚本生产工具
// TODO: 后续需要进行重构，成为“作坊”的核心框架
@Service
public class ComposeOptService {
    private ScenesService scenesService;
    private PortsService portsService;
    private NetworksService networksService;
    private ImagesService imagesService;


}
