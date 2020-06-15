package com.example.springcloudconsul;

import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@SpringBootApplication
@RestController
public class SpringCloudConsulApplication {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${my.request.name}")
    String requestName;

    @Value(value = "${spring.cloud.consul.discovery.instance-id}")
    String id;

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsulApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "hello world";
    }

    @RequestMapping("/hello")
    public String hello() {
        String response = "";
        // 表示不再去访问
        if (requestName == null || "null".equals(requestName)) {
            System.out.println("use null");
            return "I am " + id;

        }if (requestName.contains("http:")) {
            // 表示直接访问, 通过istio的服务发现
            System.out.println("use istio");
            response = HttpRequest.get(requestName + "/hello").body();
            return response + "&&I am " + id;
        }else {
            //通过discoveryClient服务发现进行访问
            System.out.println("use spring cloud");
            List<ServiceInstance> list = discoveryClient.getInstances(requestName);
            String url = "http://" + list.get(0).getHost() + ":" + list.get(0).getPort() + "/hello";
            response = HttpRequest.get(url).body();
            return response + "&&I am " + id;
        }

    }


}

