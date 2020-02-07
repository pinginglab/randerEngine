package com.pingsec.dev.service;

import com.pingsec.dev.service.dto.request.DockerRegistryListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@FeignClient(url = "${dockerOpt.url}",name="regiest")
public interface DockerOptService {
    @RequestMapping(value="/decision/person",method= RequestMethod.GET)
    ResponseEntity<DockerRegistryListDTO> getDockerByUrl(String url);
}
