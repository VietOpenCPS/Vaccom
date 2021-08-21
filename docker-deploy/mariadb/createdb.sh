#!/bin/sh

FILE=/opt/created_db

if [ ! -f "$FILE" ]; then

echo "install mariadb server"

yum -y install mariadb-server mariadb

echo "start mariadb"
/usr/libexec/mysql-prepare-db-dir %n

sleep 10

sudo -u mysql /usr/libexec/mysqld --basedir=/usr &

sleep 10

echo "set password root"
'/usr/bin/mysqladmin' -u root password ''$MYSQL_ROOT_PASSWORD''

echo "create new database, user"
mysql -u root -p$MYSQL_ROOT_PASSWORD -e "CREATE DATABASE $MYSQL_DATABASE CHARACTER SET utf8 COLLATE utf8_unicode_ci;" \
-e "CREATE USER '$MYSQL_USER'@'localhost' IDENTIFIED BY '$MYSQL_USER_PASSWORD';" \
-e "GRANT ALL PRIVILEGES ON $MYSQL_DATABASE.* TO '$MYSQL_USER'@'localhost';" \
-e "GRANT ALL PRIVILEGES ON $MYSQL_DATABASE.* TO '$MYSQL_USER'@'$BACKEND_IP' IDENTIFIED BY '$MYSQL_USER_PASSWORD' WITH GRANT OPTION;" \
-e "FLUSH PRIVILEGES;"

touch $FILE

else
nohup sudo -u mysql /usr/libexec/mysqld --basedir=/usr &
fi

if [[ $1 == "-d" ]]; then
  while true; do sleep 1000; done
fi

if [[ $1 == "-bash" ]]; then
  /bin/bash
fi