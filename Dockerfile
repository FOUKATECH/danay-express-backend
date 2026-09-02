# --- Étape 1 : build ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# --- Étape 2 : image d'exécution ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S danay && adduser -S danay -G danay
COPY --from=build /app/target/*.jar app.jar
USER danay
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
