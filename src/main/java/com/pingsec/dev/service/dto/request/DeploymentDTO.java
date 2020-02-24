package com.pingsec.dev.service.dto.request;

import io.kubernetes.client.openapi.models.V1Container;

import java.io.Serializable;
import java.util.List;

public class DeploymentDTO implements Serializable {
    private String namespace;
    private Integer replicas;
    private List<V1Container> containers;
    private String metadata_name;
    private String metadata_namespace;
    private String labels_workLayer;
    private String remark;

    public List<V1Container> getContainers() {
        return containers;
    }

    public void setContainers(List<V1Container> containers) {
        this.containers = containers;
    }


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

    public String getMetadata_name() {
        return metadata_name;
    }

    public void setMetadata_name(String metadata_name) {
        this.metadata_name = metadata_name;
    }

    public String getMetadata_namespace() {
        return metadata_namespace;
    }

    public void setMetadata_namespace(String metadata_namespace) {
        this.metadata_namespace = metadata_namespace;
    }

    public String getLabels_workLayer() {
        return labels_workLayer;
    }

    public void setLabels_workLayer(String labels_workLayer) {
        this.labels_workLayer = labels_workLayer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
