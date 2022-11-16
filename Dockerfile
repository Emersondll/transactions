FROM openjdk:11.0.15-jdk

ENV SPRING_LOGGING_LEVEL INFO
ENV PORT 8080
ENV JVM_XMS 192M
ENV JVM_XMX 448M

ARG JAR_FILE=target/*.jar
# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE ${PORT}

CMD java-   Dspring.level.root=${SPRING_LOGGING_LEVEL} \
            Dserverport=${PORT} \
            Dfile.encoding=UTF-8 \
            -Xms${JVM_XMS} \
            -Xmx${JVM_XMX} \
            -jar app/app.jar