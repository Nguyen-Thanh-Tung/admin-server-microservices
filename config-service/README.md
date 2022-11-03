### Run Config Service
#### I. Run with source code, jar file
- Add param: -DconfigLocation=/path/to/config/folder to run command
Example: ```java -jar -DconfigLocation=/home/dev/config-service-files config-service.jar```
#### II. Run with docker:
- Add environment variable to run command of docker or add env to docker-compose file
Example: ```docker run -d -p 8088:8088 --env configLocation=/data/config -v "/data/config:/path/to/config/folder" thanhtungtvg95/config-service:latest```
#### III. Run with kubernetes cluster
- Add env to development.yml file
- If run with helm: change configLocation in config-values.yml to /path/to/config/folder
#### IV. Build and push docker hub
- Login to docker hub account thanhtungtvg95
- Run: ```./docker-push.sh```