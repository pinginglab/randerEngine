package com.pingsec.dev.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * apiVersion: apps/v1 # for versions before 1.9.0 use apps/v1beta2
 * kind: Deployment
 * metadata:
 *   name: nginx-deployment
 * spec:
 *   selector:
 *     matchLabels:
 *       app: nginx
 *   replicas: 2 # tells deployment to run 2 pods matching the template
 *   template:
 *     metadata:
 *       labels:
 *         app: nginx
 *     spec:
 *       containers:
 *       - name: nginx
 *         image: nginx:1.7.9
 *         ports:
 *         - containerPort: 80
 */

public class YamlDTO implements Serializable {
    private String apiVersion;
    private String kind;
    private Metadata metadata;
    private Spec spec;


    private class Metadata {
        private String name;
    }

    private class Spec {
        private Selector selector;
        private Replicas replicas;
        private Template template;

        private class Selector{
            private MatchLabels matchLabels;

            private class MatchLabels {
                private String app;
            }
        }


        private class Replicas {
        }

        private class Template {
            private Metadata metadata;
            private Spec spec;
            private class Metadata {
                private Labels labels;

                private class Labels {
                    private App app;

                    private class App {
                        private String appName;
                    }
                }
            }

            // 根据api文档这个地方确实是spec后续看看要怎么修改
            private class Spec {
                private List<Containers> containers;

                private class Containers {
                    private String name;
                    private String image;
                    private List<Ports> ports;

                    private class Ports {
                        private String containerPort;
                    }
                }
            }
        }
    }
}
