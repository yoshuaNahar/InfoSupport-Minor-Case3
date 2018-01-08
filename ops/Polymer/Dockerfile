# Apache base image
FROM httpd:2.4
ARG DIRECTORY

COPY ./${DIRECTORY}/build/es5-bundled/ /usr/local/apache2/htdocs/
EXPOSE 80
