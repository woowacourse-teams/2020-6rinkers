FROM openjdk:8

COPY ./back/cocktailpick-api/build/libs/*.jar /deploy/jar/back.jar

ARG GOOGLE_ID
ARG GOOGLE_SECRET

ENV GOOGLE_ID=${GOOGLE_ID}
ENV GOOGLE_SECRET=${GOOGLE_SECRET}

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", "-Dspring.profiles.active=ci", "-Djava.security.egd=file:/dev/./urandom", "-Dlogging.config=classpath:logback/logback-local.xml", "/deploy/jar/back.jar"]
#ENTRYPOINT ["nohup", "java", "-jar", "-Dspring.profiles.active=ci", "-Djava.security.egd=file:/dev/./urandom", "-Dlogging.config=classpath:logback/logback-prod.xml", "/deploy/jar/back.jar", ">", "/dev/null", "&"]
