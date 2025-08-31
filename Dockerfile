# Multi-stage build for the EasyReach backend

# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -q package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Expect credentials to be supplied at runtime
ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV JWT_SECRET=""

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

