package com.pingsec.dev.service.Opt;

import org.springframework.stereotype.Service;

// 作为k8s脚本生产工具，根据配置生成相关的yaml文件进行存储后考虑自动化生成
// TODO: 后续需要进行重构，成为“作坊”的核心组成部分

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

@Service
public class YamlOptService {
    // 1. 允许通过http的方式进行创建
    // 2. 通过消息队列对场景的创建
    // 3. 通过读取数据库的形式对场景进行创建
    // 无论采用什么方式都应该对创建操作写进数据库中

}
