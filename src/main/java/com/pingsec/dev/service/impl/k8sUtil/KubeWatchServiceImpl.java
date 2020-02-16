package com.pingsec.dev.service.impl.k8sUtil;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pingsec.dev.service.k8sUtil.KubeWatchService;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static java.lang.Boolean.TRUE;


// TODO: 接口设计不规范注定会给后续的开发留下坑，一时之间没想好贵方设计，先这么用，后续根据需求，进行接口规范化设计
// 这个类相当于kubectl中的“get ** ** ”这个操作
@Service
public class KubeWatchServiceImpl implements KubeWatchService {
    private final Logger log = LoggerFactory.getLogger(KubeWatchServiceImpl.class);

//    @Scheduled(fixedRate=5000)
    public void getK8SAllNameSpaceList() {
        ApiClient client = null;
        try {
            client = Config.defaultClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        Watch<V1Namespace> watch = null;
        try {
            watch = Watch.createWatch(
                client,
                api.listNamespaceCall( null, null, null, null, null, 5, null, null, Boolean.TRUE, null),
                new TypeToken<Watch.Response<V1Namespace>>(){}.getType());
        } catch (ApiException e) {
            e.printStackTrace();
        }

        for (Watch.Response<V1Namespace> item : watch) {
            System.out.printf("%s : %s%n", item.type, item.object.getMetadata().getName());
        }
    }



    public LinkedList<JsonObject> getK8SNameSpaceList() {
        LinkedList<JsonObject> nameSpaceList = new LinkedList<>();
//    相当于kubectl get namespaces
        try {
            ApiClient client = Config.defaultClient();
            // infinite timeout
            OkHttpClient httpClient =
                client.getHttpClient().newBuilder().readTimeout(0, TimeUnit.SECONDS).build();
            client.setHttpClient(httpClient);
            Configuration.setDefaultApiClient(client);
            CoreV1Api api = new CoreV1Api();

            Watch<V1Namespace> watch =
                null;
            try {
                watch = Watch.createWatch(
                    client,
                    api.listNamespaceCall(null, null, null, null, null, 5, null, null, TRUE, null),
                    new TypeToken<Watch.Response<V1Namespace>>() {}.getType());
                for (Watch.Response<V1Namespace> item : watch) {
                    JsonObject temp = new JsonObject();
                    temp.addProperty(item.type, item.object.getMetadata().getName());
                    nameSpaceList.add(temp);
//                    System.out.printf("%s : %s%n", item.type, item.object.getMetadata().getName());
                }
                return nameSpaceList;
            } catch (ApiException e) {
                log.error("API 异常: {}", e.toString());
//                e.printStackTrace();
            } finally {
                try {
                    watch.close();
                } catch (IOException e) {
                    log.error("IO 异常: watch 无法关闭{}", e.toString());
//                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            log.error("IO 异常: {}", e.toString());
//            e.printStackTrace();
        }
        return nameSpaceList;
    }

    public V1PodList getK8SPodsForAllNamespaces() {
        try {
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);

            CoreV1Api api = new CoreV1Api();
            V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
            return list;
//            for (V1Pod item : list.getItems()) {
//                System.out.println(item.getMetadata().getName());
//            }
        } catch (ApiException e) {
            log.error("API 异常: {}", e.toString());
//            e.printStackTrace();
        } catch (IOException e) {
            log.error("IO 异常: {}", e.toString());
//            e.printStackTrace();
        }
        return null;
    }
}
