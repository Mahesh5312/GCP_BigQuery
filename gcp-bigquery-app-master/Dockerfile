FROM openjdk:8
VOLUME /tmp
COPY target/bigquery-0.0.1-SNAPSHOT.jar /app.jar
ENV JAVA_OPTS=""
#ENTRYPOINT ["java", "-jar", "/hybrid-gcp-ccp-bigquery-app.jar"]
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]