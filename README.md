# JHipster-generated Kubernetes configuration

## Preparation

You will need to push your image to a registry. If you have not done so, use the following commands to tag and push the images:

```
$ docker image tag randerengine ccr.ccs.tencentyun.com/pingsec/compose/randerengine
$ docker push ccr.ccs.tencentyun.com/pingsec/compose/randerengine
```

## Deployment

You can deploy all your apps by running the below bash command:

```
./kubectl-apply.sh
```

## Exploring your services

```

## Scaling your deployments

You can scale your apps using

```

\$ kubectl scale deployment <app-name> --replicas <replica-count>

```

## zero-downtime deployments

The default way to update a running app in kubernetes, is to deploy a new image tag to your docker registry and then deploy it using

```

\$ kubectl set image deployment/<app-name>-app <app-name>=<new-image>

```

Using livenessProbes and readinessProbe allow you to tell Kubernetes about the state of your applications, in order to ensure availablity of your services. You will need minimum 2 replicas for every application deployment if you want to have zero-downtime deployed.
This is because the rolling upgrade strategy first stops a running replica in order to place a new. Running only one replica, will cause a short downtime during upgrades.

## Monitoring tools

### JHipster console

Your application logs can be found in JHipster console (powered by Kibana). You can find its service details by
```

\$ kubectl get svc jhipster-console

```

* If you have chosen *Ingress*, then you should be able to access Kibana using the given ingress domain.
* If you have chosen *NodePort*, then point your browser to an IP of any of your nodes and use the node port described in the output.
* If you have chosen *LoadBalancer*, then use the IaaS provided LB IP



## Troubleshooting

> my apps doesn't get pulled, because of 'imagePullBackof'

Check the docker registry your Kubernetes cluster is accessing. If you are using a private registry, you should add it to your namespace by `kubectl create secret docker-registry` (check the [docs](https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/) for more info)

> my applications are stopped, before they can boot up

This can occur if your cluster has low resource (e.g. Minikube). Increase the `initialDelaySeconds` value of livenessProbe of your deployments

> my applications are starting very slow, despite I have a cluster with many resources

The default setting are optimized for middle-scale clusters. You are free to increase the JAVA_OPTS environment variable, and resource requests and limits to improve the performance. Be careful!
```
