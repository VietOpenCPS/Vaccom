# Vaccom -  phần mềm hỗ trợ quản lý và tổ chức điểm tiêm chủng Vaccine
#     Copyright (C) 2021 Vaccom Development team

#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU Affero General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     (at your option) any later version.

#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU Affero General Public License for more details.

#     You should have received a copy of the GNU Affero General Public License
#     along with this program.  If not, see <https://www.gnu.org/licenses/>.
FROM rockylinux/rockylinux
MAINTAINER Nguyen Van Hieu <ngvanhieu112233@gmail.com>

RUN yum update -y && yum install -y \
  glibc-langpack-en

COPY jre8-latest.rpm /opt/

RUN yum localinstall /opt/jre8-latest.rpm -y && rm -rf /opt/jre8-latest.rpm

RUN ln -s -f /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime

ENV LANGUAGE=en_US.UTF-8

ENV LC_ALL=en_US.UTF-8

CMD ["/bin/bash", "/opt/vaccom/start.sh", "-d"]