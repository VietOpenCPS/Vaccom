### How to start with Docker compose
##### Require : Install docker-compose for your server
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