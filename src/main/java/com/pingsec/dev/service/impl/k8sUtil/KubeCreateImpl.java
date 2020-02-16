package com.pingsec.dev.service.impl.k8sUtil;

import com.pingsec.dev.service.k8sUtil.KubeCreateService;
import org.springframework.stereotype.Service;

@Service
public class KubeCreateImpl implements KubeCreateService {
//    public void createTest(){
//        try {
//            CoreV1Api api = new CoreV1Api();
//            String serviceYaml =
//                "apiVersion: v1\n" +
//                    "kind: Service\n" +
//                    "metadata:\n" +
//                    "  name:\n" +
//                    "    service-name\n" +
//                    "spec:\n" +
//                    "  ports:\n" +
//                    "  - port: 1000\n" +
//                    "    targetPort: 1000";
//            V1Service service = YamlBugFix.loadAs(serviceYaml, V1Service.class);
//            api.createNamespacedService("default", service, null);
//        } catch (ApiException err) {
//            System.out.println(err.getResponseBody());
//        }
//    }
}
