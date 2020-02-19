package com.pingsec.dev.service.Opt;

import com.google.gson.JsonObject;
import com.pingsec.dev.config.KubeConfiguration;
import com.pingsec.dev.service.RedisService;
import com.pingsec.dev.service.dto.ScenesDTO;
import com.pingsec.dev.service.dto.request.KubeRequestDTO;
import com.pingsec.dev.service.k8sUtil.KubeUtilService;
import com.pingsec.dev.service.k8sUtil.KubeWatchService;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Service
@RestController
@RequestMapping("/opt")
public class ScenesOptService {
    // 1. 允许通过http的方式进行创建
    // 2. 通过消息队列对场景的创建
    // 3. 通过读取数据库的形式对场景进行创建
    // 无论采用什么方式都应该对创建操作写进数据库中
    private KubeWatchService kubeWatchService;
    private KubeUtilService kubeUtilService;
    private RedisService redisService;
    private KubeConfiguration kubeConfiguration;
    CoreV1Api api = new CoreV1Api();
    ApiClient client = new ApiClient();

    public ScenesOptService(KubeWatchService kubeWatchService,
                            KubeUtilService kubeUtilService,
                            RedisService redisService,
                            KubeConfiguration kubeConfiguration,
                            CoreV1Api api, ApiClient client) {
        this.kubeWatchService = kubeWatchService;
        this.kubeUtilService = kubeUtilService;
        this.redisService = redisService;
        this.kubeConfiguration = kubeConfiguration;
        this.api = api;
        this.client = client;
        try {
            client = kubeConfiguration.config();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration.setDefaultApiClient(client);
    }

    private String PREFIX_NS = "PINGSEC_NS";

    @GetMapping("/nslist")
    private HashMap<String, String> getNameSpace(){
        // 获取namespace列表，留下api接口，方便后续进行热更新数据，后续计划前端读数据从redis中读取
        // TODO: 这个操作存在优化空间： 1. 获取列表可以从redis中读取
        HashMap<String, String> result = new HashMap<>();
        LinkedList<JsonObject> linkedList = kubeWatchService.getK8SNameSpaceList();
        linkedList.forEach(item -> {
            System.out.println(item);
            redisService.setHash(PREFIX_NS, "ns", item.get());
            result.put(item.get(),item.get());
        });
        return result;
    }

    @PostMapping("/ns")
    private V1Namespace createNameSpace(@RequestBody ScenesDTO scenesDTO) throws IOException{
        String nameSpaceName = scenesDTO.getName()+scenesDTO.getCreateTime();


        V1Namespace body=new V1Namespace();
        V1ObjectMeta meta=new V1ObjectMeta();
        meta.setNamespace(nameSpaceName);
        meta.setName(nameSpaceName);
        body.setMetadata(meta);



//        body.setApiVersion("apps/v1");
//        body.setKind("Deployment");    
//        V1Deployment body=new V1Deployment();
//        body.setApiVersion("apps/v1");
//        body.setKind("Deployment");
//        V1ObjectMeta meta=new V1ObjectMeta();
//        meta.setNamespace(name);
//        meta.setName(name);
//        body.setMetadata(meta);
//        V1DeploymentSpec spec=new V1DeploymentSpec();
//        spec.setReplicas(1);
//        body.setSpec(spec);
        try {
//            appsV1Api.createNamespacedDeployment(name, body, true, null, null);
            V1Namespace result =api.createNamespace(body, String.valueOf(true), null, null);
            System.out.println(result);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return body;
//        V1Service v1Service = new V1Service();
//        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
//        v1ObjectMeta.setName("nginx-deployment");
//        V1ServiceSpec v1ServiceSpec = new V1ServiceSpec();
//
//        v1Service.setApiVersion("apps/v1");
//        v1Service.setKind("Deployment");
//        v1Service.setMetadata(v1ObjectMeta);
//        v1Service.setSpec(v1ServiceSpec);
//        try {
//            kubeUtilService.createService(nameSpaceName, v1Service);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
    }

    @PostMapping("/pods")
    public void podBuilder() {
        V1Pod pod1 = new V1PodBuilder()
            .withNewMetadata()
            .withName("mypod")
            .endMetadata()
            .withNewSpec()
            .addNewContainer()
            .withName("cnt")
            .endContainer()
            .endSpec()
            .build();

        V1Pod pod2 = new V1Pod()
            .metadata(new V1ObjectMeta().name("mypod"))
            .spec(new V1PodSpec()
                .containers(Arrays.asList(
                    new V1Container().name("cnt")
                    )
                )
            );


//        Assert.assertEquals(pod1, pod2);
    }

    @PostMapping("/service")
    private V1Service createService(@RequestBody KubeRequestDTO kubeRequestDTO) throws ApiException {
        V1Deployment body = new V1Deployment();
        body.setApiVersion("apps/v1");
        body.setKind("Deployment");
        V1ObjectMeta meta=new V1ObjectMeta();
        meta.setNamespace(kubeRequestDTO.getNamespace());
        meta.setName(kubeRequestDTO.getNamespace());
        body.setMetadata(meta);
        V1DeploymentSpec spec = new V1DeploymentSpec();
        spec.setReplicas(kubeRequestDTO.getReplicas());
        V1PodTemplateSpec templateSpec = new V1PodTemplateSpec();
        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
        Map<String, String> map = new HashMap<>();
        map.put("app", "support");
        map.put("version", "version");
        v1ObjectMeta.setLabels(map);
        templateSpec.setMetadata(v1ObjectMeta);
        V1PodSpec v1PodSpec = new V1PodSpec();
        List<V1LocalObjectReference> imagePullSecrets = new ArrayList<>();
        V1LocalObjectReference reference = new V1LocalObjectReference();
        reference.setName("regsecret");
        imagePullSecrets.add(reference);
        v1PodSpec.setImagePullSecrets(imagePullSecrets);
        v1PodSpec.setContainers(kubeRequestDTO.getContainers());
        templateSpec.setSpec(v1PodSpec);
        spec.setTemplate(templateSpec);
        V1LabelSelector v1LabelSelector = new V1LabelSelector();
        map = new HashMap<>();
        map.put("app", "support");
        v1LabelSelector.setMatchLabels(map);
        spec.setSelector(v1LabelSelector);
        body.setSpec(spec);
        kubeUtilService.createDeployment(kubeRequestDTO.getNamespace(), body);
        kubeUtilService.createService(kubeRequestDTO.getNamespace());
    }

}
