# Command using kubectl 
## Create kubernetes services
1. ```kubectl apply -f secret/secret.yml```
2. ```kubectl apply -f config-map/config-map.yml```
3. ```kubectl apply -f config-service/k8s-config-service.yml```
4. ```kubectl apply -f discovery-service/k8s-discovery-service.yml```
5. ```kubectl apply -f ./```

## Delete kubernetes services
1. ```kubectl delete -f ./```
2. ```kubectl delete -f discovery-service/discovery-service.yml```
3. ```kubectl delete -f config-service/config-service.yml```
4. ```kubectl delete -f secret/secret.yml```
5. ```kubectl delete -f config-map/config-map.yml```

## Check pods run
1. ```kubectl get all``` (or ```kubectl get pods```)

## Forward port (need port forward for config service, discovery service and gateway service)
1. ```kubectl port-forward --adress 0.0.0.0 <pod-name> <port>```

# Command using helm
1. To create package: ```./package.sh```
## On master server (kubernetes cluster)
1. Pull helm package: ```git clone git@github.com:Nguyen-Thanh-Tung/gsttan-helm-chart.git```
2. Edit files *-value.yaml
3. Install storage volume (for metadata, logs of all service): 
   1. Create persistent volume: ```kubectl apply -f storage-volume/pv-nfs.yaml```
   2. Create persistent volume claim: ```kubectl apply -f storage-volume/pvc-nfs.yaml```
   3. Notice: Setup nfs storage for all node in cluster (nfs server in master node and 2 nfs client in worker node): [guide](https://docs.google.com/document/d/1YQbr-HMDd6I5hBK7FFzJ3f7a-A5RSXspAeZ64jNma3I/edit?usp=sharing)
4. Install config service: ```helm install -f config-values.yaml config-service ./config-service-0.1.0.tgz```
5. Install discovery service: ```helm install -f discovery-values.yaml discovery-service ./discovery-service-0.1.0.tgz```
6. Install all service: ```./start_kubernetes.sh```
   1. To stop all (except discovery, config): ```./stop_kubernetes.sh```
   2. To stop discovery service: ```helm uninstall discovery-service```
   3. To stop config service: ```helm uninstall config-service```