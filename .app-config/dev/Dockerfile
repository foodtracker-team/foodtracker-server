FROM maven:3.3-jdk-8 AS build
WORKDIR /opt/app
COPY . .
RUN mvn clean package -P dev

FROM openjdk:8-jdk-alpine
WORKDIR /opt/app/
COPY --from=build /opt/app/core/target/core.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]