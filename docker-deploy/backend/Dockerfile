FROM rockylinux/rockylinux
MAINTAINER Nguyen Van Hieu <ngvanhieu112233@gmail.com>

RUN yum update -y && yum install -y \
  glibc-langpack-en

COPY jre8-latest.rpm /opt/

RUN yum localinstall /opt/jre8-latest.rpm -y && rm -rf /opt/jre8-latest.rpm

RUN ln -s -f /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime

ENV LANGUAGE=en_US.UTF-8

ENV LC_ALL=en_US.UTF-8