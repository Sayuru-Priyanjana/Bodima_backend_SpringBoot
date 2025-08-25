# Step 1: Use Maven + JDK image for build
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copy source code
COPY . .

# Build jar
RUN mvn clean package -DskipTests

# Step 2: Use lightweight JDK image for runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
