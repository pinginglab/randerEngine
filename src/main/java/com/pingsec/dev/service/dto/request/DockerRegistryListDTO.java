package com.pingsec.dev.service.dto.request;

import java.io.Serializable;
import java.util.LinkedList;

// 作为和registry直接交互的类
// 开发的时候会存在不安全的因素，将会有敏感操作，需要谨慎不要留下后门api
public class DockerRegistryListDTO implements Serializable {
    private LinkedList<String> repositoriesList;

    public LinkedList<String> getRepositoriesList() {
        return repositoriesList;
    }

    public void setRepositoriesList(LinkedList<String> repositoriesList) {
        this.repositoriesList = repositoriesList;
    }
}
