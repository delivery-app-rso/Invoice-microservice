FROM maven:3.8.6-openjdk-18 AS build
COPY ./ /app
WORKDIR /app
RUN mvn --show-version --update-snapshots --batch-mode clean package

FROM eclipse-temurin:18-jre
RUN mkdir /app
WORKDIR /app
COPY --from=build ./app/api/target/invoice-microservice-api-1.0.0-SNAPSHOT.jar /app
COPY --from=build ./app/services/src/main/resources/templates/ /app/templates/
EXPOSE 8080
CMD ["java", "-jar", "invoicing-microservice-api-1.0.0-SNAPSHOT.jar"]