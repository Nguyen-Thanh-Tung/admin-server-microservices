mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/config-service thanhtungtvg95/config-service &&
docker push thanhtungtvg95/config-service &&
docker rmi admin-server-microservices/config-service thanhtungtvg95/config-service