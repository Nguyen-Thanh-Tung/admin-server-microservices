mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/employee-service thanhtungtvg95/employee-service &&
docker push thanhtungtvg95/employee-service