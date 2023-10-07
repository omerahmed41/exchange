#!/bin/bash
export SPRING_LOGGING_LEVEL_ROOT=OFF
export SPRING_PROFILES_ACTIVE=command

# Run the JAR file and pass all script arguments to Java program
./mvnw spring-boot:run -D"spring-boot.run.arguments=--command=true, $@" -Dspring.profiles.active=command > /dev/null 2>&1

cat "output.txt"