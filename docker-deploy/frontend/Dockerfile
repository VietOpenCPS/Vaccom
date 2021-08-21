FROM nginx

RUN apt-get update -y && apt-get install wget cron -y

COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh