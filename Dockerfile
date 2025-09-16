# Use Debian-based Eclipse Temurin JDK (full image with shell and utilities)
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Make mvnw executable (if you still want to use it)
RUN chmod +x ./mvnw

# Build the Spring Boot app (skip tests to speed up build)
RUN ./mvnw -B -DskipTests clean package

# Run the app using the dynamically built JAR
CMD ["java", "-Dserver.port=${PORT}", "-jar", "target/*.jar"]
