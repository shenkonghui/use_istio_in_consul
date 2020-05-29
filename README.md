# use_istio_in_consul



1. 安装

   安装的架构图如下

   ![image.png](http://ww1.sinaimg.cn/mw690/654fc08fly1gf9dey83tij20cm05b3z7.jpg)

   ```
   cd install
   docker-compose up -d
   ```

打开consul页面查看是否正常

![image.png](http://ww1.sinaimg.cn/mw690/654fc08fly1gf9cxdecrvj21h00fatbs.jpg)

2. 安装crd

   配置一下kubcondig

   install crd

   ```
   kubectl apply -f crd/
   ```

   

3. 安装bookinfo

   ```
   cd bookinfo
   docker-compose -f bookinfo.yaml up -d 
   ```

   打开product-page网页验证是否安装成功

   xxx:9081/productpage

   ![image.png](http://ww1.sinaimg.cn/large/654fc08fly1gf9d0ok7yej21ha0cxtc5.jpg)

4. 安装sidecar

```
cd bookinfo/templates
docker-compose  up -d 
```

按照上一步，验证productpage是否正常

4. 验证istio功能

   

```
cd bookinfo
kubectl apply -f destination-rule-all.yaml
kubectl apply -f virtual-service-all-v1.yaml
```

然后查看是否全部是否都请求v1版本的



##  问题

####  安装sidecar以前bookinfo可以打开，安装完以后无法打开

查看日志

Invalid path: /etc/certs/root-cert.pem
virtualInbound: Invalid path: /etc/certs/root-cert.pem

同时进入sidecar中发现15006端口没有打开

![image.png](http://ww1.sinaimg.cn/mw690/654fc08fly1gf9cso5vkdj20qb053416.jpg)



https://github.com/istio/istio/issues/18832
1.5.2版本需要tls，切换成版本为1.4.5



#### pilot报错

pkg/mod/k8s.io/client-
go@v0.17.2/tools/cache/reflector.go:105: Failed to list *crd.IstioNetworkingV1Alpha3Envoyfilters: the server could not find the requested resource (get envoyfilters.networking.istio.io)

将api-service版本切换为1.7.2 ，并安装crd



#### sidecar报错

Didn't find a registered implementation for name: 'envoy.transport_sockets.tls', virtualInbound: Didn't find a registered implementation for name: 'envoy.transport_sockets.tls'

保持istio-proxy 与 pilot版本一致