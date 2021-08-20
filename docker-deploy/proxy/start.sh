#!/bin/sh
FILE=/etc/nginx/conf.d/proxy-vaccom.conf

if [ ! -f "$FILE" ]; then
cp -vp /opt/proxy-vaccom.conf /etc/nginx/conf.d/
sed -i "s|domain|$DOMAIN|g" /etc/nginx/conf.d/proxy-vaccom.conf
/usr/sbin/nginx -c /etc/nginx/nginx.conf
rm -rf /opt/proxy-vaccom.conf
else
/usr/sbin/nginx -c /etc/nginx/nginx.conf
fi

if [[ $1 == "-d" ]]; then
  while true; do sleep 1000; done
fi

if [[ $1 == "-bash" ]]; then
  /bin/bash
fi