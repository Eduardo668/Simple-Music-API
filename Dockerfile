FROM amazoncorretto:17-alpine3.14-jdk
COPY . music-api
WORKDIR music-api
ENTRYPOINT ["/bin/sh", "mvnw", "spring-boot:run"]
EXPOSE 8081