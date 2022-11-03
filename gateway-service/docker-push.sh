mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/gateway-service thanhtungtvg95/gateway-service &&
docker push thanhtungtvg95/gateway-service &&
docker rmi admin-server-microservices/gateway-service thanhtungtvg95/gateway-service