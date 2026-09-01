# Checkpoint Nível 1

API REST para gestão de usuários, salas e reservas de ambientes. O projeto foi desenvolvido com Spring Boot e usa PostgreSQL como banco de dados principal.

## Visão geral

A aplicação expõe endpoints para:

- cadastrar, listar, buscar, atualizar e excluir usuários;
- cadastrar, listar, buscar e desativar salas;
- criar, listar, buscar e cancelar reservas;
- validar regras de negócio para reservas, como duração mínima, ordem de datas e conflitos de horário.

O projeto está estruturado em camadas, seguindo o padrão MVC + services:

- `controllers`: endpoints HTTP;
- `services`: regras de negócio;
- `repositories`: acesso ao banco com Spring Data JPA;
- `entities`: entidades JPA;
- `dto`: objetos de transferência de dados;
- `validations`: validações específicas de reservas e senha.

## Stack

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL Driver
- Maven
- Docker

## Configuração do banco

A aplicação usa as variáveis de ambiente `DB_URL`, `DB_USER` e `DB_PASSWORD`.

Exemplo de arquivo `.env` na raiz do projeto:

```env
DB_URL=jdbc:postgresql://localhost:5432/checkpoint
DB_USER=postgres
DB_PASSWORD=senhaForte
```

> O arquivo `.env` é carregado automaticamente pela configuração do Spring (`spring.config.import=optional:file:.env[.properties]`).

Certifique-se de ter um PostgreSQL em execução antes de iniciar a aplicação.

## Executando localmente

### 1) Construir o projeto

```bash
./mvnw clean package
```

### 2) Rodar a aplicação

```bash
./mvnw spring-boot:run
```

A API será iniciada com o contexto:

```text
http://localhost:8080/api/v1
```

## Docker

### Gerar a imagem

No diretório raiz do projeto, execute:

```bash
docker build -t checkpoint:1.0 .
```

Esse comando usa o `Dockerfile` do projeto, que faz um build multi-stage com Maven e gera a imagem da aplicação Java.

### Rodar o container

```bash
docker run -d -p 8080:8080 -e DB_URL="jdbc:postgresql://host.docker.internal:5432/checkpoint" -e DB_USER="" -e DB_PASSWORD="" --add-host=host.docker.internal:host-gateway --name checkpoint checkpoint:1.0
```

Explicação dos parâmetros:

- `-p 8080:8080`: expõe a aplicação na porta 8080 do host;
- `-e DB_URL=...`: aponta para o banco PostgreSQL;
- `-e DB_USER` e `-e DB_PASSWORD`: credenciais do banco;
- `--add-host=host.docker.internal:host-gateway`: permite que o container acesse serviços executando no host da máquina;
- `--name checkpoint`: nome do contêiner.

Depois de subir o container, a API ficará disponível em:

```text
http://localhost:8080/api/v1
```

> Se o PostgreSQL estiver em outra máquina ou em outro container, ajuste o valor de `DB_URL` para o host correto.

## Endpoints principais

### Usuários

- `POST /api/v1/users` — criar usuário
- `GET /api/v1/users` — listar usuários paginados
- `GET /api/v1/users/{id}` — buscar usuário por id
- `PUT /api/v1/users/{id}` — atualizar usuário
- `DELETE /api/v1/users/{id}` — excluir usuário

### Salas

- `POST /api/v1/salas/nova` — criar sala
- `GET /api/v1/salas` — listar salas paginadas
- `GET /api/v1/salas/{id}` — buscar sala por id
- `PUT /api/v1/salas/{id}` — atualizar sala
- `DELETE /api/v1/salas/{id}` — desativar sala

### Reservas

- `POST /api/v1/reservas` — criar reserva
- `GET /api/v1/reservas` — listar reservas paginadas
- `GET /api/v1/reservas/{id}` — buscar reserva por id
- `GET /api/v1/reservas/user/{id}` — reservas por usuário
- `GET /api/v1/reservas/sala/{id}` — reservas por sala
- `DELETE /api/v1/reservas/{id}` — cancelar reserva

## Regras de negócio

A API valida:

- senha com mínimo de 8 caracteres, com letras maiúsculas, minúsculas, número e caractere especial;
- data final da reserva deve ser posterior à data inicial;
- duração mínima de 2 horas;
- conflitos de horário para a mesma sala;
- não permitir reserva de sala inativa.

## Testes

Para executar a suíte de testes:

```bash
./mvnw test
```

## Observações

- O projeto usa `ddl-auto=update`, então o esquema do banco pode ser criado/atualizado automaticamente pelo Hibernate.
- A aplicação foi preparada para rodar em ambiente Docker sem exigir alterações no código-fonte.
