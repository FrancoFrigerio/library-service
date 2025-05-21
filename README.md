After git pull:
>In auth-servce run -> docker build -f Dockerfile -t library-auth-service:latest

>In library-service run -> docker build -f Dockerfile -t library-resource-service:latest

>In front run -> docker build -f Dockerfile -t library-front-service:latest

>docker network create oaut-net

>docker run --network oaut-net --rm -p 9090:9090 --name auth-server localhost/library-auth-service:latest

>docker run --rm --network oaut-net -p 8081:8081 localhost/library-resource-service:latest

>docker run --rm --network oaut-net -p 5500:80 localhost/library-front-service:latest

In memory users:
| User | Password    | Rol    |
| :---:   | :---: | :---: |
| mauricio@hotmail.com | mypassword   | ADMIN |
| pablo@hotmail.com | mypassword   | USER |

