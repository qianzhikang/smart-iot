FROM java:8

COPY *.jar /ac.jar

CMD ["--server.port=9094"]

EXPOSE 9094

ENTRYPOINT ["java","-jar","/ac.jar"]
