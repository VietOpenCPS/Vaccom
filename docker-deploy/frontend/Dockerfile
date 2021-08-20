FROM rockylinux/rockylinux
MAINTAINER Nguyen Van Hieu <ngvanhieu112233@gmail.com>

RUN yum update -y && yum install -y \
  glibc-langpack-en \
  crontabs wget nginx python38

RUN ln -s -f /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime

ENV LANGUAGE=en_US.UTF-8

ENV LC_ALL=en_US.UTF-8

VOLUME ["/etc/letsencrypt", "/var/log/nginx", "/etc/nginx/conf.d", "/var/log/letsencrypt"]

EXPOSE 443 80

COPY start.sh /opt/

ENTRYPOINT ["/bin/bash", "/opt/start.sh", "-d"]