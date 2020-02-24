package com.pingsec.dev.service.dto.request;

import io.kubernetes.client.custom.IntOrString;

import java.io.Serializable;

public class ServiceDTO implements Serializable {
    private String metadata_name;
    private String metadata_namespace;
    private String labels_workLayer;
    private String spec_type;
    private Integer spec_ports_port;
    private IntOrString spec_ports_targetPort;
    private String spec_ports_protocol;
    private Integer spec_ports_nodePort;
    private String remark;

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

    public String getSpec_type() {
        return spec_type;
    }

    public void setSpec_type(String spec_type) {
        this.spec_type = spec_type;
    }

    public String getSpec_ports_protocol() {
        return spec_ports_protocol;
    }

    public void setSpec_ports_protocol(String spec_ports_protocol) {
        this.spec_ports_protocol = spec_ports_protocol;
    }

    public Integer getSpec_ports_port() {
        return spec_ports_port;
    }

    public void setSpec_ports_port(Integer spec_ports_port) {
        this.spec_ports_port = spec_ports_port;
    }

    public IntOrString getSpec_ports_targetPort() {
        return spec_ports_targetPort;
    }

    public void setSpec_ports_targetPort(IntOrString spec_ports_targetPort) {
        this.spec_ports_targetPort = spec_ports_targetPort;
    }

    public Integer getSpec_ports_nodePort() {
        return spec_ports_nodePort;
    }

    public void setSpec_ports_nodePort(Integer spec_ports_nodePort) {
        this.spec_ports_nodePort = spec_ports_nodePort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
