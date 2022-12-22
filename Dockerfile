FROM java:8
EXPOSE 8080
ADD /target/gasStation-0.0.1-SNAPSHOT.jar.jar gasStation-0.0.1-SNAPSHOT.jar.jar
ENTRYPOINT ["java","-jar","gasStation-0.0.1-SNAPSHOT.jar.jar"]