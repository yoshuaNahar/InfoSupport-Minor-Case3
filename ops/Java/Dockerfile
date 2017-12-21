FROM frolvlad/alpine-oraclejdk8:slim
ARG DIRECTORY

ADD ./ops/wait-for.sh wait-for.sh
ADD ./${DIRECTORY}/target/*.jar app.jar
