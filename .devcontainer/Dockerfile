FROM tomcat:9.0-jdk11-openjdk

# Install necessary tools (e.g., Maven)
RUN apt-get update && apt-get install -y maven

# Copy your project files into the container
COPY . /usr/local/tomcat/webapps/myapp/

# Set the working directory
WORKDIR /usr/local/tomcat/webapps/myapp/

# Build the WAR file (if using Maven)
RUN mvn clean package

# Deploy the WAR file (if using Maven)
RUN mv target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat's port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]