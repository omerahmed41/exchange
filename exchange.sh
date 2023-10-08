#!/bin/bash
export SPRING_LOGGING_LEVEL_ROOT=OFF
export SPRING_PROFILES_ACTIVE=command

# Run the JAR file and pass all script arguments to Java program
./mvnw spring-boot:run -Dcheckstyle.skip=true -DskipTests -D"spring-boot.run.arguments=--command=true, $@" -Dspring.profiles.active=command > /dev/null 2>&1
#./mvnw spring-boot:run -Dcheckstyle.skip=true -DskipTests -D"spring-boot.run.arguments=--command=true, $@" -Dspring.profiles.active=command

if [ -f "output.txt" ]; then
  cat "output.txt"
fi

if [ -f "error.txt" ]; then
  cat "error.txt"
fi
