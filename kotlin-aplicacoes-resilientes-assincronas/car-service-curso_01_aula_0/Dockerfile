FROM maven:3.8.3-openjdk-17-slim AS build

WORKDIR /app

COPY . /app

RUN mvn clean package

FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

# Run the jar file
CMD ["java", "-jar", "app.jar"]
