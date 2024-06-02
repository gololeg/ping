FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn clean install -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 2222
ENTRYPOINT ["java", "-jar", "/app/*.jar"]