FROM java:8

COPY *.jar /user.jar

CMD ["--server.port=9091"]

EXPOSE 9091

ENTRYPOINT ["java","-jar","/user.jar"]
