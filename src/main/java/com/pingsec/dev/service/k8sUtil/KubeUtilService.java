package com.pingsec.dev.service.k8sUtil;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1Service;

import java.io.IOException;

public interface KubeUtilService {
    Object loadYaml(String path) throws IOException;
    V1Pod createPod(String nameSpace, V1Pod body) throws ApiException;
    void deletePod(String nameSpace, String podName) throws Exception;
    void createService(String nameSpace, V1Service sv) throws ApiException;
    void deleteService(String nameSpace, String serviceName) throws Exception;
    V1Deployment createDeployment(String nameSpace, V1Deployment body) throws ApiException;
    void deleteDeployment(String nameSpace, String deployeName) throws ApiException;
}
