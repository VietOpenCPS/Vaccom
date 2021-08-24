### How to start with Docker compose
##### Require : Install docker-compose for your server

```bash
$ git clone https://github.com/VietOpenCPS/Vaccom.git && cd docker-deploy
```
##### Edit all variable in .env then run docker-compose

##### Necessary: Before run "docker-compose up -d" Need to do the following:
* Download java-jre8 at https://www.oracle.com/java/technologies/javase-jre8-downloads.html (Product / File Description: Linux x64 RPM Package) Then rename file download to jre8-latest.rpm and copy to docker-deploy/backend/
* Rename the built jar file to org.vaccom.vcmgt-1.0.0.jar and copy to docker-deploy/backend/vaccom/backend/
* Copy code frontend to docker-deploy/frontend/code/

##### 
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