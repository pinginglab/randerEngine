package com.pingsec.dev.service.impl.k8sUtil;

import com.pingsec.dev.config.KubeConfiguration;
import com.pingsec.dev.service.k8sUtil.KubeUtilService;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


// TODO: 应该需要两种服务创建方式：1. 通过yaml创建；2. 创建service/pod

@Service
public class KubeUtilImpl implements KubeUtilService {
    private final Logger log = LoggerFactory.getLogger(KubeUtilImpl.class);
    private KubeConfiguration kubeConfiguration;

    public KubeUtilImpl(KubeConfiguration kubeConfiguration) {
        this.kubeConfiguration = kubeConfiguration;
        ApiClient client = kubeConfiguration.initK8sApiClient();
        Configuration.setDefaultApiClient(client);
    }

    /**
     * 加载yaml配置文件
     *
     * @param path
     * @throws IOException
     */
    @Override
    public Object loadYaml(String path) throws IOException {
        Reader reader = new FileReader(path);
        return Yaml.load(reader);
    }

    /**
     * 创建pod
     *
     * @param nameSpace ：名称空间
     * @param body      ：pod
     * @return
     * @throws ApiException
     */
    @Override
    public V1Pod createPod(String nameSpace, V1Pod body) throws ApiException {

        return new CoreV1Api().createNamespacedPod(nameSpace, body, String.valueOf(true), "true", null);

    }

    /**
     * 删除pod
     *
     * @param nameSpace
     * @param podName
     * @throws Exception
     */
    @Override
    public void deletePod(String nameSpace, String podName) throws Exception {
        new CoreV1Api().deleteNamespacedPod(podName, nameSpace, "true", "true", null, null, null, null);
    }

    /**
     * 创建service
     *
     * @param nameSpace
     * @param sv
     * @throws ApiException
     */
    @Override
    public void createService(String nameSpace, V1Service sv) throws ApiException {
        new CoreV1Api().createNamespacedService(nameSpace, sv, String.valueOf(true), "true", null);
    }

    /**
     * 删除service
     *
     * @param nameSpace
     * @param serviceName
     * @throws Exception
     */
    @Override
    public void deleteService(String nameSpace, String serviceName) throws Exception {
        new CoreV1Api().deleteNamespacedService(serviceName, nameSpace, String.valueOf(true), "true", null, null, null, null);
    }

    /**
     * 创建deployment
     *
     * @param nameSpace
     * @param body
     * @throws ApiException
     * @return
     */
    @Override
    public V1Deployment createDeployment(String nameSpace, V1Deployment body) throws ApiException {
        return new AppsV1Api().createNamespacedDeployment(nameSpace, body, String.valueOf(true), null, null);
    }

    /**
     * 刪除namespace
     *
     * @param nameSpace
     * @param deployeName
     * @throws ApiException
     */
    @Override
    public void deleteDeployment(String nameSpace, String deployeName) throws ApiException {
        new AppsV1Api().deleteNamespacedDeployment(deployeName, nameSpace, "true", null, null, null, null, null);
    }

    public void main(String[] args) throws Exception {
//        setConfig("config");
        ApiClient client = null;
        try {
            client = Config.defaultClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration.setDefaultApiClient(client);
        Reader reader = new FileReader("datax3.yaml");
        Object load = Yaml.load(reader);
        System.out.println(load.getClass());
        V1Deployment d = (V1Deployment) load;
        d.getMetadata().setName("datax-test");
        deleteDeployment("default", d.getMetadata().getName());

    }
}
