#!/bin/bash
FILE=/opt/vaccom/nohup.out
if [! -f "$FILE"]; then
sleep 1m
cd /opt/vaccom
nohup /usr/bin/java -jar /opt/vaccom/backend/org.vaccom.vcmgt-1.0.0.jar -Dspring-boot.run.jvmArguments="-Duser.timezone=UTC" --spring.config.location=file:/opt/vaccom/application.properties > /opt/vaccom/nohup.out &
else
cd /opt/vaccom
nohup /usr/bin/java -jar /opt/vaccom/backend/org.vaccom.vcmgt-1.0.0.jar -Dspring-boot.run.jvmArguments="-Duser.timezone=UTC" --spring.config.location=file:/opt/vaccom/application.properties > /opt/vaccom/nohup.out &
fi

if [[ $1 == "-d" ]]; then
  while true; do sleep 1000; done
fi

if [[ $1 == "-bash" ]]; then
  /bin/bash
fi
