FROM khipu/openjdk17-alpine
ENV CONFIG_SERVICE_URL ""
ENV APP_FILE user-log-service-1.0-SNAPSHOT.jar
ENV APP_HOME /app
EXPOSE 8092
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
ENV TZ Asia/Ho_Chi_Minh
CMD ["exec java -jar $APP_FILE --configServiceUrl=$CONFIG_SERVICE_URL"]