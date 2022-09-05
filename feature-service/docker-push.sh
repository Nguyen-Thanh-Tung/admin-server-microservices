mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/feature-service thanhtungtvg95/feature-service &&
docker push thanhtungtvg95/feature-service