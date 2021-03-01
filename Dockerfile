FROM maven:3.6.3 AS MAVEN_BUILD

COPY /. /.

RUN mvn clean package

FROM openjdk

COPY --from=MAVEN_BUILD /target/DemoRecipe-0.0.1-SNAPSHOT.jar /demo.jar

EXPOSE 8080

CMD ["java", "-jar", "demo.jar"]