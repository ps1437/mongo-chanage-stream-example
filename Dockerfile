# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR .

# Copy the JAR file into the container
COPY target/mongo-stream.jar /app/mongo-stream.jar

# Expose the port your application will run on (e.g., 8080)
EXPOSE 8080

# Run the JAR file using the Java runtime
ENTRYPOINT ["java", "-jar", "mongo-stream.jar"]
