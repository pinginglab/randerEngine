package com.pingsec.dev.service.impl.k8sopt;

import com.pingsec.dev.service.k8sopt.KubeWatch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KubeWatchImpl implements KubeWatch {
//    public KubeWatchImpl() {
//    }
//
//    CoreV1Api api = new CoreV1Api();
//    public KubeWatchImpl(ApiClient client, OkHttpClient httpClient, CoreV1Api api) {
//        this.client = client;
//        this.httpClient = httpClient;
//        this.api = api;
//    }
//
//    ApiClient client = new ApiClient();
//    // 设置超时时间
//    OkHttpClient httpClient =
//        client.getHttpClient().newBuilder().readTimeout(0, TimeUnit.SECONDS).build();
//
//    public ApiClient getClient() {
//        return client;
//    }
//
//    public void setClient(ApiClient client) {
//        try {
//            client = Config.defaultClient();
//            client.setHttpClient(httpClient);
//            Configuration.setDefaultApiClient(client);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.client = client;
//    }

//    @Scheduled(fixedRate=5000)
//    public void getK8SNameSpaceList() {
//        Watch<V1Namespace> watch;
//        try {
//            watch = Watch.createWatch(
//                client,
//                api.listNamespaceCall(null, null, null, null, null, 5, null, null, Boolean.TRUE, null),
//                new TypeToken<Watch.Response<V1Namespace>>() {
//                }.getType());
//            try {
//                for (Watch.Response<V1Namespace> item : watch) {
//                    System.out.printf("%s : %s%n", item.type, item.object.getMetadata().getName());
//                }
//            } finally {
//                watch.close();
//            }
//        } catch (ApiException | IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Scheduled(fixedRate=5000)
    public void getK8SPodsList() {
        try {
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);

            CoreV1Api api = new CoreV1Api();
            V1PodList list =
                api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
            for (V1Pod item : list.getItems()) {
                System.out.println(item.getMetadata().getName());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
