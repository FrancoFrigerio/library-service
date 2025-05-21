#a partir de esta imagen base
FROM eclipse-temurin:21.0.3_9-jre-jammy

#descargo el .jar de la aplicacion
ADD https://github.com/FrancoFrigerio/my-library-service/releases/download/source-v1/library-service-0.0.1-SNAPSHOT.jar ./library-resource-service.jar
    

# o si ya tengo el .jar en mi pc puedo:
#COPY graphql-2-jpa.jar.
# cuando inicie el container de esta imagen lanzar el siguiente comando
ENTRYPOINT [ "java","-jar","library-resource-service.jar" ]