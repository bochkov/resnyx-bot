FROM openjdk:11-jre
ENV TZ America/New_York
ENV LANG ru_RU.UTF-8
ENV LANGUAGE ru_RU:en
ENV LC_ALL ru_RU.UTF-8
RUN wget -O /usr/local/bin/dumb-init https://github.com/Yelp/dumb-init/releases/download/v1.2.2/dumb-init_1.2.2_amd64 && \
  chmod +x /usr/local/bin/dumb-init
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
WORKDIR /opt
ADD build/libs/resnyx-bot-1.0-SNAPSHOT.jar /opt/resnyx-bot.jar
EXPOSE 8080
ENTRYPOINT ["/usr/local/bin/dumb-init", "--"]
CMD ["sh", "-c", "java $JAVA_OPTS -Dfile.encoding=UTF-8 -jar resnyx-bot.jar --host=mongo"]
