FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD ./target/hr-worker-0.0.1-SNAPSHOT.jar hr-worker.jar
ENTRYPOINT ["java","-jar","/hr-worker.jar"]