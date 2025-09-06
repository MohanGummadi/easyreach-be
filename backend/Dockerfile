# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy POMs first to leverage layer caching
COPY pom.xml .
COPY backend/pom.xml backend/pom.xml

# Prefetch deps for the backend module using the root reactor
RUN mvn -q -B -f pom.xml -pl backend -am -DskipTests dependency:go-offline

# Copy sources and build
COPY backend/src backend/src
RUN mvn -q -B -f pom.xml -pl backend -am -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/backend/target/*.jar app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
