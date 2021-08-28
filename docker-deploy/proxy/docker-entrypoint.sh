#!/bin/sh
# vim:sw=4:ts=4:et

FILE=/opt/default.conf

if [ -f "$FILE" ]; then
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

# Create month cron to update ssl
cat > /etc/cron.monthly/update_ssl <<EOF
#!/bin/bash
# sync certificate from container vaccom-frontend.
sshpass -p "$CONTAINER_ROOT_PASSWORD" scp root@vaccom-frontend:/etc/letsencrypt/archive/$DOMAIN/fullchain1.pem /etc/nginx/ssl/fullchain.pem
sshpass -p "$CONTAINER_ROOT_PASSWORD" scp root@vaccom-frontend:/etc/letsencrypt/archive/$DOMAIN/privkey1.pem /etc/nginx/ssl/privkey.pem

#reload nginx.
nginx -s reload
EOF

# Start services
mkdir /etc/nginx/ssl
mkdir /run/sshd
/usr/sbin/sshd -D &
/usr/sbin/cron -n &
sleep 100m
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


