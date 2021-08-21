### How to start with Docker compose
##### Require : 
##### + Install docker-compose for your server
##### + Download java-jre8 at https://www.oracle.com/java/technologies/javase-jre8-downloads.html (Product / File Description: Linux x64 RPM Package) Then rename file download to jre8-latest.rpm and copy to docker-deploy/backend
```bash
$ git clone https://github.com/VietOpenCPS/Vaccom.git && cd docker-deploy
```
##### Edit variable in docker-compose.yml then run docker-compose

```bash
$ docker-compose up -d
```
(and WAIT...)

##### Command check status
```bash
$ docker-compose status
```
##### Command list container
```bash
$ docker-compose ps
```

##### Command check logs
```bash
$ docker-compose logs -f