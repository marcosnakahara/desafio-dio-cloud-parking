## Após instalação do docker
### Para executar o docker com o PostgreSQL
docker run --name parking-db -p 5432:5432 -e POSTGRES_DB=parking -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123 -d postgres:10-alpine

### Comandos para parar e iniciar o serviço, respectivamente
docker stop parking-db
docker start parking-db