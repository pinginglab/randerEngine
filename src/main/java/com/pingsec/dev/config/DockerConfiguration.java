package com.pingsec.dev.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;

public class DockerConfiguration {
    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//        .withDockerHost("tcp://localhost:2376")
        .withDockerHost("unix:///var/run/docker.sock")
//        .withDockerHost("tcp://docker.somewhere.tld:2376")
        .withDockerTlsVerify(true)
        .withDockerCertPath("/home/user/.docker")
//        .withRegistryUsername(registryUser)
//        .withRegistryPassword(registryPass)
//        .withRegistryEmail(registryMail)
//        .withRegistryUrl(registryUrl)
        .build();

    // using jaxrs/jersey implementation here (netty impl is also available)
    DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
        .withReadTimeout(1000)
        .withConnectTimeout(1000)
        .withMaxTotalConnections(100)
        .withMaxPerRouteConnections(10);

    DockerClient dockerClient = DockerClientBuilder.getInstance(config)
        .withDockerCmdExecFactory(dockerCmdExecFactory)
        .build();
}
