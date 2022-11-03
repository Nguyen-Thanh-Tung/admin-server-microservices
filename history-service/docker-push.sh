mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/history-service thanhtungtvg95/history-service &&
docker push thanhtungtvg95/history-service &&
docker rmi admin-server-microservices/history-service thanhtungtvg95/history-service