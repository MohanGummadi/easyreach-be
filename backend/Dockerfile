# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy backend module only (Dockerfile is in backend/)
COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -q -B -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render sets $PORT; default to 8080 locally
ENV PORT=8080
EXPOSE 8080

# Make Spring Boot bind to $PORT (required on Render)
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
