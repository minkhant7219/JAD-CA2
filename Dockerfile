FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/CA2-0.0.1-SNAPSHOT.war app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]