FROM khipu/openjdk17-alpine
ENV CONFIG_SERVICE_URL ""
ENV APP_FILE time-keeping-service-1.0-SNAPSHOT.jar
ENV APP_HOME /app
EXPOSE 8089
COPY target/$APP_FILE $APP_HOME/
COPY target/classes/log4j2.yml $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE --configServiceUrl=$CONFIG_SERVICE_URL"]