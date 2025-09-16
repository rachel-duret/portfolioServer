FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY . ./

# Install bash and core utilities
RUN apk add --no-cache bash coreutils

# Make mvnw executable and build
RUN chmod +x ./mvnw && ./mvnw -B -DskipTests clean package

CMD ["bash", "-c", "java -Dserver.port=$PORT -jar target/*.jar"]
