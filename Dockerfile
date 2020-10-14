FROM openjdk:15

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /app

COPY /target/stock-quote-manager-0.0.1-SNAPSHOT.jar /app/inatel-1.0.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 8081

CMD java ${ADDITIONAL_OPTS} -jar inatel-1.0.jar --spring.profiles.active${PROFILE}