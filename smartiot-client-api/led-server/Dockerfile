FROM java:8

COPY *.jar /led.jar

CMD ["--server.port=9092"]

EXPOSE 9092

ENTRYPOINT ["java","-jar","/led.jar"]
