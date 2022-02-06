FROM eclipse-temurin:17-jre-alpine
ENV TZ Europe/Berlin
ENV LANG ru_RU.UTF-8
ENV LANGUAGE ru_RU:en
ENV LC_ALL ru_RU.UTF-8
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
WORKDIR /opt
ADD build/libs/resnyx-bot.jar /opt/resnyx-bot.jar
EXPOSE 8080
CMD ["/bin/sh", "-c", "java $JAVA_OPTS -Dfile.encoding=UTF-8 -jar resnyx-bot.jar --host=mongo"]
