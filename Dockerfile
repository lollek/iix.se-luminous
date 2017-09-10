FROM java:8-alpine
MAINTAINER Olle Kvarnström <olle+luminous@iix.se>

ADD target/uberjar/iix.se-luminous.jar /iix.se-luminous/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/iix.se-luminous/app.jar"]
