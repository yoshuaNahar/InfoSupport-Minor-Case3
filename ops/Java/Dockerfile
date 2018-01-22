FROM openjdk:8
ARG DIRECTORY

# Needed for wait-for.sh
RUN apt-get -q update && apt-get -qy install netcat

ADD ./ops/wait-for.sh wait-for.sh
ADD ./${DIRECTORY}/target/*.jar app.jar
