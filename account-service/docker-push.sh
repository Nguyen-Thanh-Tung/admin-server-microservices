mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/account-service thanhtungtvg95/account-service &&
docker push thanhtungtvg95/account-service &&
docker rmi admin-server-microservices/account-service thanhtungtvg95/account-service