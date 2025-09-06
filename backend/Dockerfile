# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies
COPY pom.xml .
COPY backend/pom.xml backend/pom.xml
RUN mvn -q -pl backend -am dependency:go-offline

# Copy source and build
COPY backend/src backend/src
RUN mvn -q -pl backend -am package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/backend/target/*.jar app.jar

# Render injects PORT; default to 8080 if not set
ENV PORT=8080
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
