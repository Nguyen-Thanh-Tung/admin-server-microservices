mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/user-log-service thanhtungtvg95/user-log-service &&
docker push thanhtungtvg95/user-log-service &&
docker rmi admin-server-microservices/user-log-service thanhtungtvg95/user-log-service