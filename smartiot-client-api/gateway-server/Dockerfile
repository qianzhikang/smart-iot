FROM java:8

COPY *.jar /gateway.jar

CMD ["--server.port=9999"]

EXPOSE 9999

ENTRYPOINT ["java","-jar","/gateway.jar"]
