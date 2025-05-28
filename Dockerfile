FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven project files and source
COPY pom.xml .
COPY src ./src

# Build the app
RUN mvn clean package

# Use a Java runtime base image
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy the built app from previous stage
COPY --from=build /app/target/Product-1.0.jar app.jar
COPY src/main/resources/Application.yaml /app/Application.yaml

# Expose the port your app runs on
EXPOSE 8080

ENV SPRING_CONFIG_LOCATION=file:/app/Application.yaml

# Set environment variables (can be overridden at runtime)
ENV MONGO_HOST=192.168.10.43
ENV MONGO_PORT=27017
ENV MONGO_USERNAME=jeff
ENV MONGO_PASSWORD=mypasssword

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
