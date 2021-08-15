FROM eclipse-temurin:11

ENV PORT=8080
EXPOSE ${PORT}

COPY /target/battlesnake.jar /opt/app/
CMD java -jar /opt/app/battlesnake.jar
