package com.pingsec.dev.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.openapi.apis.RbacAuthorizationV1Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Slf4j
@Configuration
public class KubeConfiguration {
    private static final String CONFIG_KEY = "resources/config/cls-f5m05hg3-config"; // 本地的开发环境配置
    private static final String CONFIG_DEBUG_KEY = "resources/config/cls-f5m05hg3-config";
    private final Logger log = LoggerFactory.getLogger(KubeConfiguration.class);

    @Value("${" + CONFIG_KEY + "}")
    private String config;

    @Value("${" + CONFIG_DEBUG_KEY + "}")
    private String debug;

    @Bean
    @Primary
    public ApiClient initK8sApiClient() {
        try {
            InputStream configStream = new ByteArrayInputStream(config.getBytes());
            final ApiClient apiClient = io.kubernetes.client.util.Config.fromConfig(configStream);
            // 5分钟的超时时间
            apiClient.setConnectTimeout(5 * 60 * 1000);
            apiClient.setDebugging(Boolean.valueOf(debug));
//            client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
//            Configuration.setDefaultApiClient(client);
            io.kubernetes.client.openapi.Configuration.setDefaultApiClient(apiClient);
//            io.kubernetes.client.Configuration.setDefaultApiClient(apiClient);
//            ConfigCache.getInstance().addChange((key, value) -> {
//                if (CONFIG_KEY.equals(key)) {
//                    log.warn("[ LEO ] Change k8s config, this change should have no effect.");
//                } else if (CONFIG_DEBUG_KEY.equals(key)) {
//                    debug = value;
//                    apiClient.setDebugging(Boolean.valueOf(value));
//                }
//            });
            log.info("[ K8s ] Client init success, server: {} ------------------------->", apiClient.getBasePath());
            return apiClient;
        } catch (Exception e) {
            log.error("[ K8s ] Client init failed! -------------------------->", e);
            return null;
        }
    }

    @Bean
    public CoreV1Api coreV1Api(@Autowired ApiClient apiClient) {
        return new CoreV1Api(apiClient);
    }

    @Bean
    public AppsV1Api appsV1Api(@Autowired ApiClient apiClient) {
        return new AppsV1Api(apiClient);
    }

    @Bean
    public RbacAuthorizationV1Api rbacAuthorizationV1Api(@Autowired ApiClient apiClient) {
        return new RbacAuthorizationV1Api(apiClient);
    }

    @Bean
    public ExtensionsV1beta1Api extensionsV1beta1Api(@Autowired ApiClient apiClient) {
        return new ExtensionsV1beta1Api(apiClient);
    }



//    @Bean
//    public ApiClient config() throws IOException{
//        // file path to your KubeConfig
//        String kubeConfigPath = "~/.kube/config";
//
//        // loading the out-of-cluster config, a kubeconfig from file-system
//        ApiClient client;
//
//        client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
//        Configuration.setDefaultApiClient(client);
//        return client;
//    }

//    // file path to your KubeConfig
//    String kubeConfigPath = "~/.kube/config";
//
//    // loading the out-of-cluster config, a kubeconfig from file-system
//    ApiClient client;
//
//    {
//        try {
//            client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
//            Configuration.setDefaultApiClient(client);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // set the global default api-client to the in-cluster one from above


//    // the CoreV1Api loads default api-client from global configuration.
//    CoreV1Api api = new CoreV1Api();
//
//    // invokes the CoreV1Api client
//    V1PodList list;
//
//    {
//        try {
//            list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
//            for(V1Pod item: list.getItems()) {
//                System.out.println(item.getMetadata().getName());
//            }
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//    }

}
