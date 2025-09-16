# Use the full Debian-based Temurin JDK image
FROM eclipse-temurin:21-jdk

# Create and change to the app directory.
WORKDIR /app

# Copy local code to the container image.
COPY . ./

# Build the app using Maven installed in the image
RUN ./mvnw -B -DskipTests clean package

# Run the app
CMD ["java", "-Dserver.port=${PORT}", "-jar", "target/*.jar"]
