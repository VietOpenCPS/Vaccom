#!/bin/sh
FILE=/etc/nginx/conf.d/vaccom.conf

if [ ! -f "$FILE" ]; then
echo 'download certbot-auto'
wget https://raw.githubusercontent.com/certbot/certbot/7f0fa18c570942238a7de73ed99945c3710408b4/letsencrypt-auto-source/letsencrypt-auto -O /opt/certbot-auto
chmod +x /opt/certbot-auto

echo -e '#!/bin/bash\n\n# stop nginx\n/usr/sbin/nginx -s stop\n\n# renew cert\nUSE_PYTHON_3=1 /opt/certbot-auto renew\n\n# start nginx\n/usr/sbin/nginx -c /etc/nginx/nginx.conf' > /etc/cron.daily/letsencrypt-renew

echo "create SSL letsencrypt for $DOMAIN"
USE_PYTHON_3=1 /opt/certbot-auto certonly --standalone --agree-tos -m "$CERTBOT_EMAIL" -n -d $DOMAIN_LIST
cat > /etc/nginx/conf.d/vaccom.conf <<EOF
server {
	listen 80;
	server_name domain;
	 #enforce https
	return 301 https://$server_name$request_uri;
}

server {
	listen 443 ssl;
	ssl_certificate /etc/letsencrypt/live/thachban.vaccom.vn/fullchain.pem;
	ssl_certificate_key /etc/letsencrypt/live/thachban.vaccom.vn/privkey.pem;
	access_log /var/log/nginx/domain-access.log;
	error_log /var/log/nginx/domain-error.log;
}
EOF
sed -i "s|domain|$DOMAIN|g" /etc/nginx/conf.d/vaccom.conf
/usr/sbin/crond -n &
/usr/sbin/nginx -c /etc/nginx/nginx.conf

else
/usr/sbin/crond -n &
/usr/sbin/nginx -c /etc/nginx/nginx.conf
fi

if [[ $1 == "-d" ]]; then
  while true; do sleep 1000; done
fi

if [[ $1 == "-bash" ]]; then
  /bin/bash
fi