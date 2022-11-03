mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/location-service thanhtungtvg95/location-service &&
docker push thanhtungtvg95/location-service &&
docker rmi admin-server-microservices/location-service thanhtungtvg95/location-service