mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/time-keeping-service thanhtungtvg95/time-keeping-service &&
docker push thanhtungtvg95/time-keeping-service &&
docker rmi admin-server-microservices/time-keeping-service thanhtungtvg95/time-keeping-service