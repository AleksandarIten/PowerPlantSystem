FROM openjdk:11
EXPOSE 8080
ADD target/power-plant-system-0.0.1-SNAPSHOT.jar power-plant-system-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/power-plant-system-0.0.1-SNAPSHOT.jar"]