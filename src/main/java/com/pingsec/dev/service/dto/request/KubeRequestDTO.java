package com.pingsec.dev.service.dto.request;

import io.kubernetes.client.openapi.models.V1Container;

import java.io.Serializable;
import java.util.List;

public class KubeRequestDTO implements Serializable {
    private String namespace;
    private Integer replicas;

    public List<V1Container> getContainers() {
        return containers;
    }

    public void setContainers(List<V1Container> containers) {
        this.containers = containers;
    }

    private List<V1Container> containers;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }



    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }
}
