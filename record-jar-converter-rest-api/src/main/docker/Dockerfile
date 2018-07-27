FROM pierrezemb/gostatic
MAINTAINER Martin Panzer <postremus1996@googlemail.com>
HEALTHCHECK CMD wget -q 127.0.0.1:8043/record-jar-converter-rest-api/ || exit 1
EXPOSE 8043

ADD maven/target/${output.directory} /srv/http/${jboss.web.context}/