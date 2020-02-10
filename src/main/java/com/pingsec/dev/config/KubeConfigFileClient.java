package com.pingsec.dev.config;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileReader;
import java.io.IOException;

public class KubeConfigFileClient {

    // file path to your KubeConfig
    String kubeConfigPath = "~/.kube/config";

    // loading the out-of-cluster config, a kubeconfig from file-system
    ApiClient client;

    {
        try {
            client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
            Configuration.setDefaultApiClient(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // set the global default api-client to the in-cluster one from above


    // the CoreV1Api loads default api-client from global configuration.
    CoreV1Api api = new CoreV1Api();

    // invokes the CoreV1Api client
    V1PodList list;

    {
        try {
            list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
            for(V1Pod item: list.getItems()) {
                System.out.println(item.getMetadata().getName());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
