#!/bin/sh
# vim:sw=4:ts=4:et

FILE=/usr/sbin/certbot-auto

if [ ! -f "$FILE" ]; then

wget https://raw.githubusercontent.com/certbot/certbot/7f0fa18c570942238a7de73ed99945c3710408b4/letsencrypt-auto-source/letsencrypt-auto -O /usr/sbin/certbot-auto
chmod +x /usr/sbin/certbot-auto
/usr/sbin/certbot-auto certonly --nginx --agree-tos -m "$CERTBOT_EMAIL" -n -d $DOMAIN_LIST

cat > /etc/cron.daily/letsencrypt-renew <<EOF
#!/bin/bash

#renew cert
/usr/sbin/certbot-auto renew --nginx
EOF

mv /opt/default.conf /etc/nginx/conf.d/default.conf
sed -i "s|DOMAIN|$DOMAIN|g" /etc/nginx/conf.d/default.conf

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
sshpass -p "$CONTAINER_ROOT_PASSWORD" ssh-copy-id -o StrictHostKeyChecking=no root@vaccom-proxy
# Sync certificate SSL to vaccom-proxy
scp /etc/letsencrypt/archive/$DOMAIN/fullchain1.pem root@vaccom-proxy:/etc/nginx/ssl/fullchain.pem
scp /etc/letsencrypt/archive/$DOMAIN/privkey1.pem root@vaccom-proxy:/etc/nginx/ssl/privkey.pem
mkdir /run/sshd
/usr/sbin/sshd -D &
ssh root@vaccom-proxy 'pkill sleep'

# Start services
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


