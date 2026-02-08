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
RUN apt-get update && apt-get install -y wget gnupg2 \
    && wget -q -O - https://dl-google.com/linux/linux_signing_key.pub | apt-key add - \
    && sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list' \
    && apt-get update && apt-get install -y google-chrome-stable

ENTRYPOINT ["mvn", "clean", "test", "-DsuiteXmlFile=testng.xml"]
