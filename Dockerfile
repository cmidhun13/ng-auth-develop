# Start with a base image containing Java runtime 
FROM openjdk:11-jre-slim
 
# Add Maintainer Info 
LABEL maintainer="akhan@szellsgroup.com,simore@szellsgroup.com"
 
# Add a volume pointing to /tmp 
VOLUME /tmp 
 
# Make port 8061 available to the world outside this container 
EXPOSE 8061 
 
# The application's jar file 
ARG JAR_FILE=./build/libs/ng-auth-0.0.1-SNAPSHOT.jar
 
# Add the application's jar to the container 
ADD ${JAR_FILE} ng-auth.jar 
 
# Run the jar file 
ENTRYPOINT ["java","-Dserver.port=8061","-Djava.security.egd=file:/dev/./urandom","-jar","/ng-auth.jar"]