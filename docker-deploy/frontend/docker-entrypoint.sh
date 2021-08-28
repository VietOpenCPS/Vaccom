#!/bin/sh
# vim:sw=4:ts=4:et

FILE=/usr/sbin/certbot-auto

if [ ! -f "$FILE" ]; then

mkdir /run/sshd
/usr/sbin/sshd -D &

wget https://raw.githubusercontent.com/certbot/certbot/7f0fa18c570942238a7de73ed99945c3710408b4/letsencrypt-auto-source/letsencrypt-auto -O /usr/sbin/certbot-auto
chmod +x /usr/sbin/certbot-auto
/usr/sbin/certbot-auto certonly --nginx --agree-tos -m "$CERTBOT_EMAIL" -n -d $DOMAIN_LIST

cat > /etc/cron.daily/letsencrypt-renew <<EOF
#!/bin/bash

#renew cert
/usr/sbin/certbot-auto renew --nginx
EOF

cat > /etc/nginx/conf.d/default.conf <<EOF
server {
	listen 80;
	server_name $DOMAIN;
	#enforce https
	return 301 https://$server_name$request_uri;
}

server {
	listen 443 ssl;
    server_name $DOMAIN;
    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;
    }

    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
	ssl_certificate /etc/letsencrypt/live/$DOMAIN/fullchain.pem;
	ssl_certificate_key /etc/letsencrypt/live/$DOMAIN/privkey.pem;
	access_log /var/log/nginx/$DOMAIN-access.log;
	error_log /var/log/nginx/$DOMAIN-error.log;
}
EOF

cat > /opt/change_root_password.txt <<EOF
$CONTAINER_ROOT_PASSWORD
$CONTAINER_ROOT_PASSWORD
EOF
# Change password root
passwd < /opt/change_root_password.txt
rm -f /opt/change_root_password.txt
# Permit root login
sed -i "s|#PermitRootLogin prohibit-password|PermitRootLogin yes|" /etc/ssh/sshd_config
ssh-keygen -t rsa -f ~/.ssh/id_rsa -q -P ""
#source of key(s) to be installed: "/root/.ssh/id_rsa.pub"
sshpass -p "$CONTAINER_ROOT_PASSWORD" ssh-copy-id root@vaccom-proxy
#copy key to vaccom-proxy
sshpass -p "$CONTAINER_ROOT_PASSWORD" ssh-copy-id root@vaccom-proxy
# Sync certificate SSL to vaccom-proxy
rsync -av /etc/letsencrypt/archive/$DOMAIN/fullchain1.pem root@vaccom-proxy:/etc/nginx/ssl/fullchain.pem
rsync -av /etc/letsencrypt/archive/$DOMAIN/privkey1.pem root@vaccom-proxy:/etc/nginx/ssl/privkey.pem


/usr/sbin/cron -n &
else
/usr/sbin/sshd -D &
/usr/sbin/cron -n &
fi



set -e

if [ -z "${NGINX_ENTRYPOINT_QUIET_LOGS:-}" ]; then
    exec 3>&1
else
    exec 3>/dev/null
fi

if [ "$1" = "nginx" -o "$1" = "nginx-debug" ]; then
    if /usr/bin/find "/docker-entrypoint.d/" -mindepth 1 -maxdepth 1 -type f -print -quit 2>/dev/null | read v; then
        echo >&3 "$0: /docker-entrypoint.d/ is not empty, will attempt to perform configuration"

        echo >&3 "$0: Looking for shell scripts in /docker-entrypoint.d/"
        find "/docker-entrypoint.d/" -follow -type f -print | sort -V | while read -r f; do
            case "$f" in
                *.sh)
                    if [ -x "$f" ]; then
                        echo >&3 "$0: Launching $f";
                        "$f"
                    else
                        # warn on shell scripts without exec bit
                        echo >&3 "$0: Ignoring $f, not executable";
                    fi
                    ;;
                *) echo >&3 "$0: Ignoring $f";;
            esac
        done

        echo >&3 "$0: Configuration complete; ready for start up"
    else
        echo >&3 "$0: No files found in /docker-entrypoint.d/, skipping configuration"
    fi
fi
exec "$@"


