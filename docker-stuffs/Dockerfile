FROM gradle:5.2.1-jdk8-alpine

USER root

RUN apk add postgresql curl

RUN mkdir /run/postgresql && chmod 777 /run/postgresql

USER gradle
