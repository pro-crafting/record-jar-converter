FROM openjdk:8-jre-alpine
MAINTAINER Martin Panzer <postremus1996@googlemail.com>
ENTRYPOINT java -Djava.net.preferIPv4Stack=true -jar /opt/${file.hollow-jar} /opt/${file.hollow-app}
EXPOSE 8080

ADD /maven/${file.hollow-jar} /opt/
ADD /maven/${file.hollow-app} /opt/