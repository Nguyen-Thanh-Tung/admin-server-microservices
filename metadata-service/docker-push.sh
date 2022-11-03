mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/metadata-service thanhtungtvg95/metadata-service &&
docker push thanhtungtvg95/metadata-service &&
docker rmi admin-server-microservices/metadata-service thanhtungtvg95/metadata-service