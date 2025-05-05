FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven project files and source
COPY pom.xml .
COPY src ./src

# Build the app
RUN mvn clean package

# Use a smaller runtime image
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built app from previous stage
COPY --from=build /app/target/Product-1.0.jar .

CMD ["java", "-jar", "Product-1.0.jar"]
