#
# Build
#
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package

#
# Package stage
#
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080

# Install database driver
RUN apk add --no-cache curl
RUN mkdir -p /opt/jdbc
WORKDIR /opt/jdbc
RUN curl -o sqljdbc.jar https://repo1.maven.org/maven2/com/microsoft/sqlserver/mssql-jdbc/9.4.0.jre17-preview/sqljdbc-9.4.0.jre17-preview.jar

# Set environment variables for database configuration
ENV DB_HOST=localhost
ENV DB_PORT=1433
ENV DB_NAME=MinistoreManagement
ENV DB_USERNAME=sa
ENV DB_PASSWORD=12345
ENTRYPOINT ["java", "-cp", "/app/app.jar:/opt/jdbc/sqljdbc.jar", "com..sitesquad.ministore.MinistoreApplication"]
