#!/bin/bash
FILE=/opt/vaccom/nohup.out
if [ ! -f "$FILE" ]; then
sleep 1m
sed -i "s|db_name|$MYSQL_DATABASE|" /opt/vaccom/application.properties
sed -i "s|db_user|$MYSQL_USER|" /opt/vaccom/application.properties
sed -i "s|db_password|$MYSQL_PASSWORD|" /opt/vaccom/application.properties
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
