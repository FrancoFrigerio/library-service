After git pull:
>In auth-servce run -> docker build -f Dockerfile -t library-auth-service:latest

>In library-service run -> docker build -f Dockerfile -t library-resource-service:latest

>In front run -> docker build -f Dockerfile -t library-front-service:latest

>run docker compose up

Go to http://127.0.0.1:5500 ant try to user oauth 2 authentication

In memory users:
| User | Password    | Rol    |
| :---:   | :---: | :---: |
| mauricio@hotmail.com | mypassword   | ADMIN |
| pablo@hotmail.com | mypassword   | USER |

