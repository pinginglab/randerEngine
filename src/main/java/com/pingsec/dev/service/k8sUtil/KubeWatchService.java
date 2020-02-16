package com.pingsec.dev.service.k8sUtil;

import com.google.gson.JsonObject;
import io.kubernetes.client.openapi.models.V1PodList;

import java.util.LinkedList;

public interface KubeWatchService {
    LinkedList<JsonObject> getK8SNameSpaceList();
    V1PodList getK8SPodsForAllNamespaces();
}
