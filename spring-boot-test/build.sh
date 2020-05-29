mvn package  -Dmaven.test.skip=true 
docker build  -t registry.cn-hangzhou.aliyuncs.com/shenkonghui/sc-client:0 .
docker push registry.cn-hangzhou.aliyuncs.com/shenkonghui/sc-client:0
#kubectl delete -f  app.yaml
#kubectl apply -f app.yaml
