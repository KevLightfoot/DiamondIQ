
FROM tomcat:9.0-jdk11-temurin

# Download the MySQL driver used by the original project
ADD https://repo.maven.apache.org/maven2/com/mysql/mysql-connector-j/8.0.32/mysql-connector-j-8.0.32.jar /usr/local/tomcat/lib/mysql-connector-j.jar

# Copy the existing JSP web application
COPY src/main/webapp/ /usr/local/tomcat/webapps/DiamondIQ/

# Copy and compile the existing Java source
COPY src/main/java/ /tmp/DiamondIQ/src/main/java/

RUN mkdir -p /usr/local/tomcat/webapps/DiamondIQ/WEB-INF/classes \
    && find /tmp/DiamondIQ/src/main/java -name "*.java" > /tmp/sources.txt \
    && javac -cp "/usr/local/tomcat/lib/*" \
       -d /usr/local/tomcat/webapps/DiamondIQ/WEB-INF/classes \
       @/tmp/sources.txt \
    && rm -rf /tmp/DiamondIQ

EXPOSE 8080

CMD ["catalina.sh", "run"]
