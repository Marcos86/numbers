FROM openjdk:21-jdk
LABEL org.opencontainers.image.authors="marcosdelamo86"
COPY numbers-infrastructure/target/numbers-infrastructure-0.0.1-SNAPSHOT.jar numbers-infrastructure/target/numbers-infrastructure-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/numbers-infrastructure/target/numbers-infrastructure-0.0.1-SNAPSHOT.jar"]