mvn clean install &&
docker-compose build &&
docker tag admin-server-microservices/organization-service thanhtungtvg95/organization-service &&
docker push thanhtungtvg95/organization-service