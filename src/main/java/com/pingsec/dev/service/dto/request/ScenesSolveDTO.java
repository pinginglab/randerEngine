package com.pingsec.dev.service.dto.request;

import java.io.Serializable;

// 设计这个接收类用来屏蔽用户直接写入内部消息，需要进过中间件进行分析过滤
// 过滤的内容可以可以看对应的service这个类
public class ScenesSolveDTO implements Serializable {
    private Long id;
    private String name;
    private String creater;
    private String type;
    private Long extendTime;
    private String app;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExtendTime() {
        return extendTime;
    }

    public void setExtendTime(Long extendTime) {
        this.extendTime = extendTime;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
