FROM khipu/openjdk17-alpine
ENV CONFIG_SERVICE_URL ""
ENV APP_FILE employee-service-1.0-SNAPSHOT.jar
ENV APP_HOME /app
EXPOSE 8084
COPY target/$APP_FILE $APP_HOME/
COPY target/classes/log4j2.yml $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
ENV TZ Asia/Ho_Chi_Minh
CMD ["exec java -jar $APP_FILE --configServiceUrl=$CONFIG_SERVICE_URL"]