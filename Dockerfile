# Étape de construction (Build)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# On compile en ignorant les tests et en forçant l'encodage UTF-8 pour les accents
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8

# Étape d'exécution (Run)
FROM eclipse-temurin:21-alpine
WORKDIR /app
# On récupère le fichier .jar généré
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]