FROM rockylinux/rockylinux
MAINTAINER Nguyen Van Hieu <ngvanhieu112233@gmail.com>

RUN yum update -y && yum install -y \
  perl \
  glibc-langpack-en \
  sudo

RUN ln -s -f /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime

ENV LANGUAGE=en_US.UTF-8

ENV LC_ALL=en_US.UTF-8

VOLUME ["/etc/my.cnf.d", "/var/log/mariadb", "/var/lib/mysql"]

COPY createdb.sh /opt/

ENTRYPOINT ["/bin/bash", "/opt/createdb.sh", "-d"]