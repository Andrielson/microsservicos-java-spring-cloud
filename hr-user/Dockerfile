FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD ./target/hr-user-0.0.1-SNAPSHOT.jar hr-user.jar
ENTRYPOINT ["java","-jar","/hr-user.jar"]