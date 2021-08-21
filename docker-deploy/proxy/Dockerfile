FROM rockylinux/rockylinux
MAINTAINER Nguyen Van Hieu <ngvanhieu112233@gmail.com>

RUN yum update -y && yum install -y \
  glibc-langpack-en \
  nginx

RUN ln -s -f /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime

ENV LANGUAGE=en_US.UTF-8

ENV LC_ALL=en_US.UTF-8

VOLUME ["/var/log/nginx", "/etc/nginx/conf.d"]

EXPOSE 8080

COPY start.sh /opt/

COPY template/proxy-vaccom.conf /opt

ENTRYPOINT ["/bin/bash", "/opt/start.sh", "-d"]