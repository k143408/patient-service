FROM amazoncorretto:17.0.6-alpine3.17
ARG PROJECT_NAME
ENV PROJECT_NAME ${PROJECT_NAME}
COPY build/libs/${PROJECT_NAME}.jar /opt/service/
WORKDIR /opt/service
ENTRYPOINT ["/bin/sh", "-c", "./${PROJECT_NAME}.jar"]