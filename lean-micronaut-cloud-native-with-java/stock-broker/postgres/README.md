# Dockerized Postgres

* https://hub.docker.com/_/postgres

## Ephemeral Postgres Instances

* This is the quickest way to get started:

```

docker run --name my-postgres -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=mn-stock-broker -p 5432:5432 -d postgres:12.4

```
* docker ps -- list processes
* User: postgres
* Password: secret
* Database: mn-stock-broker
* docker stop ID_CONTAINER


## Docker Compose
* Execute from root directory:
```
docker-compose -f ./postgres/stack.yml up
```
* Enter http://localhost:8081