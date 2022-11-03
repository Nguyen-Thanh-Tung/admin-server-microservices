mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/area-restriction-service thanhtungtvg95/area-restriction-service &&
docker push thanhtungtvg95/area-restriction-service &&
docker rmi admin-server-microservices/area-restriction-service thanhtungtvg95/area-restriction-service