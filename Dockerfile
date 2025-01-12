## Étape 1 : Utiliser une image Tomcat comme base
#FROM tomcat:9.0-jdk17
#
## Étape 2 : Définir l'argument pour le fichier WAR
#ARG WAR_FILE=target/Insta-lite-0.0.1-SNAPSHOT.war
#
## Étape 3 : Copier le fichier WAR dans le répertoire webapps de Tomcat
#COPY ${WAR_FILE} /usr/local/tomcat/webapps/insta-lite.war
#
## Étape 4 : Exposer le port de l'application
#EXPOSE 8089
#
## Étape 5 : Lancer Tomcat
#CMD ["catalina.sh", "run"]

# 1. Utiliser une image officielle avec Java 23
FROM eclipse-temurin:23-jdk-alpine

# 2. Définir le répertoire de travail
WORKDIR /app

# 3. Copier le fichier WAR dans le conteneur
ARG WAR_FILE=target/Insta-lite-0.0.1-SNAPSHOT.war
COPY ${WAR_FILE} app.war

# 4. Exposer le port de l'application
EXPOSE 8089

# 5. Lancer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.war"]
