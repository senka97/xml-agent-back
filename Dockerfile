FROM openjdk:8-jdk-alpine
COPY ./target/team19-0.0.1.jar ./
CMD ["java","-jar","team19-0.0.1.jar"]
