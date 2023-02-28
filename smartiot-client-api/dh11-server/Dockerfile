FROM java:8

COPY *.jar /dh11.jar

CMD ["--server.port=9093"]

EXPOSE 9093

ENTRYPOINT ["java","-jar","/dh11.jar"]
