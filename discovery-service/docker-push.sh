mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/discovery-service thanhtungtvg95/discovery-service &&
docker push thanhtungtvg95/discovery-service