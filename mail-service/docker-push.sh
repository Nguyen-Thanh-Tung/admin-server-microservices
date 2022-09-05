mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/mail-service thanhtungtvg95/mail-service &&
docker push thanhtungtvg95/mail-service