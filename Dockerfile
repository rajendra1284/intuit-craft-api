FROM amazoncorretto:17.0.7-alpine

VOLUME /tmp

COPY ./build/libs/intuit-craft-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar", "args"]