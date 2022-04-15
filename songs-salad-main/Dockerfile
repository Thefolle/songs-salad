FROM eclipse-temurin

# Building with Docker implies that source files are copied into the image, which is useless; moreover, Docker is intended
# for deployment purposes, not building ones
COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT java -jar ./app.jar