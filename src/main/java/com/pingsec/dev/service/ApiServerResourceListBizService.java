//package com.pingsec.dev.service;
//
//import com.appscode.voyager.client.models.V1beta1IngressList;
//import com.google.gson.Gson;
//import io.kubernetes.client.openapi.ApiException;
//import io.kubernetes.client.openapi.apis.AppsV1Api;
//import io.kubernetes.client.openapi.apis.CoreV1Api;
//import io.kubernetes.client.openapi.apis.ExtensionsV1beta1Api;
//import io.kubernetes.client.openapi.models.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//// 还不知道这玩意干嘛的，后面再研究研究
//
//@Service
//public class ApiServerResourceListBizService {
//    @Autowired
//    private CoreV1Api api;
//
//    @Autowired
//    private AppsV1Api appsV1Api;
//
//    @Autowired
//    private ExtensionsV1beta1Api extensionsV1beta1Api;
//
//
//    protected String name() {
//        return "ApiServerResourceListBizService";
//    }
//
//    public <T> T getApiServerResponse(String namespace, Class<T> t) throws ApiException {
//        if (t == V1PodList.class) {
//            V1PodList v1PodList = api.listNamespacedPod(namespace, true, null, null, null, null, null, null, null, null);
//            return (T) v1PodList;
//        } else if (t == V1DeploymentList.class) {
//            V1DeploymentList v1DeploymentList = appsV1Api.listNamespacedDeployment(namespace, true, null, null, null, null, null, null, null, null);
//            return (T) v1DeploymentList;
//        } else if (t == V1beta1IngressList.class) {
//            V1beta1IngressList v1beta1IngressList = extensionsV1beta1Api.listNamespacedIngress(namespace, true, null, null, null, null, null, null, null, null);
//            return (T) v1beta1IngressList;
//        } else if (t == V1ServiceList.class) {
//            V1ServiceList v1ServiceList = api.listNamespacedService(namespace, true, null, null, null, null, null, null, null, null);
//            return (T) v1ServiceList;
//        } else if(t == V1ConfigMapList.class) {
//            V1ConfigMapList v1ConfigMapList = api.listNamespacedConfigMap(namespace, true, null, null, null, null, null, null, null, null);
//            return (T) v1ConfigMapList;
//        } else if(t == V1beta1NetworkPolicyList.class) {
//            V1beta1NetworkPolicyList v1beta1NetworkPolicyList = extensionsV1beta1Api.listNamespacedNetworkPolicy(namespace, true, null, null, null, null, null, null, null, null);
//            return (T) v1beta1NetworkPolicyList;
//        } else if(t == V1NodeList.class) {
//            V1NodeList v1NodeList = api.listNode(null,"true",null,null,null,null,null,null,null);
//            return (T) v1NodeList;
//        } else if(t == V1NamespaceList.class) {
//            V1NamespaceList v1NamespaceList = api.listNamespace(null,"true",null,null,null,null,null,null,null );
//            return (T) v1NamespaceList;
//        }
//        return null;
//    }
//
//    public <T> T getApiServerAllResponse(Class<T> t) throws ApiException {
//        if (t == V1PodList.class) {
//            V1PodList v1PodList = api.listPodForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//            return (T) v1PodList;
//        } else if(t == V1ServiceList.class) {
//            V1ServiceList v1ServiceList = api.listServiceForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//            return (T) v1ServiceList;
//        } else if(t == V1EndpointsList.class) {
//            V1EndpointsList v1EndpointsList = api.listEndpointsForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//            return (T) v1EndpointsList;
//        } else if(t == V1ConfigMapList.class) {
//            V1ConfigMapList v1ConfigMapList = api.listConfigMapForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//            return (T) v1ConfigMapList;
//        } else if(t == V1DeploymentList.class) {
//            V1DeploymentList v1DeploymentList = appsV1Api.listDeploymentForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//            return (T) v1DeploymentList;
//        } else if(t == V1beta1IngressList.class) {
//            V1beta1IngressList v1beta1IngressList = extensionsV1beta1Api.listIngressForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//            return (T) v1beta1IngressList;
//        } else if(t == V1beta1NetworkPolicyList.class) {
//            V1beta1NetworkPolicyList v1beta1NetworkPolicyList = extensionsV1beta1Api.listNetworkPolicyForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//            return (T) v1beta1NetworkPolicyList;
//        }
//        return null;
//    }
//
//    public String getApiServerAllNamespaceResponse(String type) {
//        try {
//            String result = "";
//            Gson gson = new Gson();
//            switch (type) {
//                case "namespace":
//                    V1NamespaceList v1NamespaceList = api.listNamespace(null,"true",null,null,null,null,null,null,null );
//                    result = gson.toJson(v1NamespaceList);
//                    break;
//                case "node":
//                    V1NodeList v1NodeList = api.listNode(null,"true",null,null,null,null,null,null,null);
//                    result = gson.toJson(v1NodeList);
//                    break;
//                case "pod":
//                    V1PodList v1PodList = api.listPodForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//                    result = gson.toJson(v1PodList);
//                    break;
//                case "service":
//                    V1ServiceList v1ServiceList = api.listServiceForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//                    result = gson.toJson(v1ServiceList);
//                    break;
//                case "endPoints":
//                    V1EndpointsList v1EndpointsList = api.listEndpointsForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//                    result = gson.toJson(v1EndpointsList);
//                    break;
//                case "configMap":
//                    V1ConfigMapList v1ConfigMapList = api.listConfigMapForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//                    result = gson.toJson(v1ConfigMapList);
//                    break;
//                case "deployment":
//                    V1DeploymentList v1DeploymentList = appsV1Api.listDeploymentForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//                    result = gson.toJson(v1DeploymentList);
//                    break;
//                case "ingress":
//                    V1beta1IngressList v1beta1IngressList = extensionsV1beta1Api.listIngressForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//                    result = gson.toJson(v1beta1IngressList);
//                    break;
//                case "networkPolicy":
//                    V1beta1NetworkPolicyList v1beta1NetworkPolicyList = extensionsV1beta1Api.listNetworkPolicyForAllNamespaces(null,null,true,null,null,"true",null,null,null);
//                    result = gson.toJson(v1beta1NetworkPolicyList);
//                    break;
//                default:
//                    System.out.println("输入参数不正确");
//            }
//            return result;
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
