FROM khipu/openjdk17-alpine

RUN mkdir /app

RUN addgroup -g 1001 -S comit

RUN adduser -S crd -u 1001

COPY target/*.jar /app/app.jar

WORKDIR /app

RUN chown -R crd:comit /app

ENV TZ Asia/Ho_Chi_Minh

ENTRYPOINT ["java", "-jar", "app.jar"]