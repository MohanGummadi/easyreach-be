# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy BOTH the root aggregator POM and the backend module POM
COPY pom.xml .
COPY backend/pom.xml backend/pom.xml

# Pre-fetch dependencies for the backend module (and any parents/modules it needs)
RUN mvn -q -B -f backend/pom.xml -pl backend -am -DskipTests dependency:go-offline

# Copy sources and build the backend jar
COPY backend/src backend/src
RUN mvn -q -B -f backend/pom.xml -pl backend -am -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/backend/target/*.jar app.jar

# Render will pass PORT; default to 8080 if not provided
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
