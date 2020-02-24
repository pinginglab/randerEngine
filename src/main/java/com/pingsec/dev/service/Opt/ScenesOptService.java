package com.pingsec.dev.service.Opt;

import com.google.gson.JsonObject;
import com.pingsec.dev.config.KubeConfiguration;
import com.pingsec.dev.service.RedisService;
import com.pingsec.dev.service.dto.ScenesDTO;
import com.pingsec.dev.service.dto.request.DeploymentDTO;
import com.pingsec.dev.service.dto.request.KubeRequestDTO;
import com.pingsec.dev.service.dto.request.ServiceDTO;
import com.pingsec.dev.service.k8sUtil.KubeUtilService;
import com.pingsec.dev.service.k8sUtil.KubeWatchService;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


// TODO: 由于本地研发电脑的dns解析有点问题，暂时链接不上tke集群，先把大致代码框架实现了，后续再测试优化
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
//    ApiClient client = new ApiClient();
    private final Logger log = LoggerFactory.getLogger(ScenesOptService.class);

    public ScenesOptService(KubeWatchService kubeWatchService,
                            KubeUtilService kubeUtilService,
                            RedisService redisService,
                            KubeConfiguration kubeConfiguration) {
        this.kubeWatchService = kubeWatchService;
        this.kubeUtilService = kubeUtilService;
        this.redisService = redisService;
        this.kubeConfiguration = kubeConfiguration;
    }

    private String PREFIX_NS = "PINGSEC_NS";

    @GetMapping("/nslist")
    private HashMap<String, String> getNameSpace() {
        // 获取namespace列表，留下api接口，方便后续进行热更新数据，后续计划前端读数据从redis中读取
        // TODO: 这个操作存在优化空间： 1. 获取列表可以从redis中读取
        HashMap<String, String> result = new HashMap<>();
        LinkedList<JsonObject> linkedList = kubeWatchService.getK8SNameSpaceList();
        linkedList.forEach(item -> {
            System.out.println(item);
            redisService.setHash(PREFIX_NS, "ns", item.get());
            result.put(item.get(), item.get());
        });
        return result;
    }

    @PostMapping("/ns")
    private Map<String, String> createNameSpace(@RequestBody ScenesDTO scenesDTO){
        String nameSpaceName = scenesDTO.getName() + scenesDTO.getCreateTime();
        Map<String, String> message = new HashMap<>();


        V1Namespace body = new V1Namespace();
        V1ObjectMeta meta = new V1ObjectMeta();
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
            V1Namespace result = api.createNamespace(body, String.valueOf(true), null, null);

            message.put("success", "应用命名空间创建成功！ ");
            // 这个地方需要进行格式转换，暂时没有测试，后续根据需要进行测试后转换称json格式
            message.put("detail", result.toString());
        } catch (ApiException e) {
            log.error("Exception when calling CoreV1Api#createNamespace");
            log.error("Status code: {}", e.getCode());
            log.error("Reason: {}", e.getResponseBody());
            log.error("Response headers: {}", e.getResponseHeaders());
            if (e.getCode() == 409) {
                message.put("error", "命名空间已重复！");
            }
            if (e.getCode() == 200) {
                message.put("success", "应用命名空间创建成功！");
            }
            if (e.getCode() == 201) {
                message.put("error", "命名空间已重复！");
            }
            if (e.getCode() == 401) {
                message.put("error", "无权限操作！");
            }
            message.put("error", "应用命名空间创建失败！");
        }
        return message;
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

    /*
    参考材料：https://blog.csdn.net/qq_33398459/article/details/102859652
    在Deployments做这个接口的时候我一直在纠结，是先要做Deployments还是先创建pod容器，
    后来发现Deployments或者是DaemonSet还是ReplicaSet，
    都是pod的管理器也就是说我们创建Deployments就会默认创建容器Pod
     */
    @PostMapping("/deployement")
    private Map<String, String> createDeployement(@RequestBody DeploymentDTO deploymentDTO) {
        Map<String, String> message = new HashMap<>();
        V1Deployment body = new V1Deployment();
        body.setApiVersion("apps/v1");
        body.setKind("Deployment");
        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setNamespace(deploymentDTO.getNamespace());
        meta.setName(deploymentDTO.getNamespace());
        body.setMetadata(meta);
        V1DeploymentSpec spec = new V1DeploymentSpec();
        spec.setReplicas(deploymentDTO.getReplicas());
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
        v1PodSpec.setContainers(deploymentDTO.getContainers());
        templateSpec.setSpec(v1PodSpec);
        spec.setTemplate(templateSpec);
        V1LabelSelector v1LabelSelector = new V1LabelSelector();
        map = new HashMap<>();
        map.put("app", "support");
        v1LabelSelector.setMatchLabels(map);
        spec.setSelector(v1LabelSelector);
        body.setSpec(spec);
        try{
            V1Deployment result = kubeUtilService.createDeployment(deploymentDTO.getNamespace(), body);
            message.put("success", "工作负载创建成功！");
            // 这个地方需要进行格式转换，暂时没有测试，后续根据需要进行测试后转换称json格式
            message.put("detail", result.toString());
        } catch (ApiException e){
            if (e.getCode() == 409) {
                message.put("error", "工作负载创建已重复！");
            } else if (e.getCode() == 200) {
                message.put("success", "工作负载创建成功！");
            } else if (e.getCode() == 201) {
                message.put("error", "工作负载创建已重复！");
            } else if (e.getCode() == 401) {
                message.put("error", "无权限操作！");
            } else {
                message.put("error", "工作负载创建失败！");
            }
            log.error("Exception when calling AppsV1Api#createNamespacedDeployment");
            log.error("Status code: {}", e.getCode());
            log.error("Reason: {}", e.getResponseBody());
            log.error("Response headers: {}", e.getResponseHeaders());
        }
        return message;
    }

    /*
    参考材料：https://blog.csdn.net/dfBeautifulLive/article/details/103084362?utm_source=distribute.pc_relevant.none-task
    */
    @PostMapping("/service")
    private Map<String, String> createService(@RequestBody ServiceDTO serviceDTO) {
        Map<String, String> message = new HashMap<>();
        String nameStr = serviceDTO.getLabels_workLayer() + "-" + serviceDTO.getMetadata_name();


        V1Service body = new V1Service();
        body.setApiVersion("v1");
        body.setKind("Service");

        V1ObjectMeta objectMeta = new V1ObjectMeta();
        objectMeta.setName(nameStr);
        objectMeta.setNamespace(serviceDTO.getMetadata_namespace());
        Map<String, String> annotation = new HashMap<>();
        annotation.put("k8s.eip.work/displayName", serviceDTO.getRemark());
        annotation.put("k8s.eip.work/workload", nameStr);
        objectMeta.setAnnotations(annotation);

        Map<String, String> Labels = new HashMap();
        Labels.put("k8s.eip.work/layer", serviceDTO.getLabels_workLayer());
        Labels.put("k8s.eip.work/name", nameStr);
        objectMeta.setLabels(Labels);

        V1ServiceSpec serviceSpec = new V1ServiceSpec();
        List<V1ServicePort> servicePorts = new ArrayList<>();
        serviceSpec.setType(serviceDTO.getSpec_type());
        V1ServicePort servicePort = new V1ServicePort();
        //servicePort.setName(serviceDTO.getSpec_ports_name());
        servicePort.setPort(serviceDTO.getSpec_ports_port());
        servicePort.setNodePort(serviceDTO.getSpec_ports_nodePort());
        servicePort.setProtocol(serviceDTO.getSpec_ports_protocol());
        servicePort.setTargetPort(serviceDTO.getSpec_ports_targetPort());
        servicePorts.add(servicePort);
        // selector
        Map<String, String> selector = new HashMap<>();
        selector.put("k8s.eip.work/layer", serviceDTO.getLabels_workLayer());
        selector.put("k8s.eip.work/name", nameStr);

        serviceSpec.setPorts(servicePorts);
        serviceSpec.setSelector(selector);
        body.setMetadata(objectMeta);
        body.setSpec(serviceSpec);

        try {
            V1Service result = api.createNamespacedService(serviceDTO.getMetadata_namespace(), body, null, null, null);
            message.put("success", "工作负载服务创建成功！");
            // 这个地方需要进行格式转换，暂时没有测试，后续根据需要进行测试后转换称json格式
            message.put("detail", result.toString());
        } catch (ApiException e) {
            if (e.getCode() == 409) {
                message.put("error", "工作负载服务创建已重复！");
            } else if (e.getCode() == 200) {
                message.put("success", "工作负载服务创建成功！");
            } else if (e.getCode() == 201) {
                message.put("error", "工作负载服务创建已重复！");
            } else if (e.getCode() == 401) {
                message.put("error", "无权限操作！");
            } else if (e.getCode() == 400) {
                message.put("error", "后台参数错误！");
            } else if (e.getCode() == 400) {
                message.put("error", "没有命名空间或没有Deployment！");
            } else {
                message.put("error", "工作负载服务创建失败！");
            }
            log.error("Exception when calling AppsV1Api#createNamespacedDeployment");
            log.error("Status code: {}", e.getCode());
            log.error("Reason: {}", e.getResponseBody());
            log.error("Response headers: {}", e.getResponseHeaders());
        }
        return message;
    }
}
