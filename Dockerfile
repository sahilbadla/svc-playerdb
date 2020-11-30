FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD build/libs/playerdb-0.0.1-SNAPSHOT.jar playerdb.jar

# uncomment this to run with local profile
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring-boot.run.profiles=local","-jar","/playerdb.jar"]

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/playerdb.jar"]