FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY testng.xml .
RUN mvn dependency:go-offline

FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY --from=build /app .
# Install Chrome and Firefox for local execution within Docker if needed
# Typically we rely on remote Grid, but adding Chrome is good for standalone


ENTRYPOINT ["mvn", "clean", "test", "-DsuiteXmlFile=testng.xml"]
