
# Build
#
FROM openjdk:17-alpine
COPY ./target/ministore-1.0-SNAPSHOT.jar ministore-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/ministore-1.0-SNAPSHOT.jar"]


# Set environment variables for database configuration
ENV DB_HOST=localhost
ENV DB_PORT=1433
ENV DB_NAME=MinistoreManagement
ENV DB_USERNAME=sa
ENV DB_PASSWORD=12345


