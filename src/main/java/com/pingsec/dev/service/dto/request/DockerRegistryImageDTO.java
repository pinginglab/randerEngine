package com.pingsec.dev.service.dto.request;

import java.io.Serializable;
import java.util.LinkedList;

// 获取某个镜像的所有标签
// 同样的，着一些系列的操作都应该避开delete操作
public class DockerRegistryImageDTO implements Serializable {
    private String name;
    private LinkedList<String> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getTags() {
        return tags;
    }

    public void setTags(LinkedList<String> tags) {
        this.tags = tags;
    }
}
