FROM openjdk:21.04-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .

# Copy the source code
COPY src ./src

# Package the application
RUN ["mvn", "clean", "package"]

# Specify the command to run the application
CMD ["java", "-jar", "target/Product-1.0.jar"] 

