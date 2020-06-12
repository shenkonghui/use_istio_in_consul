From openjdk:15-jdk
ADD target/spring-cloud-consul-0.0.1-SNAPSHOT.jar  /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-server","-Xms1024M","-Xmx1024M","-jar","/app.jar"]