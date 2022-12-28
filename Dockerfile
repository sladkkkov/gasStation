FROM eclipse-temurin:17-jdk-jammy

EXPOSE 8080
ADD /target/gasStation-0.0.1-SNAPSHOT.jar gasStation-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","gasStation-0.0.1-SNAPSHOT.jar"]