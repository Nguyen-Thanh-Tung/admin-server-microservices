mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/camera-service thanhtungtvg95/camera-service &&
docker push thanhtungtvg95/camera-service &&
docker rmi admin-server-microservices/camera-service thanhtungtvg95/camera-service