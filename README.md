# Voll.med API

API REST da aplicação Voll.med, desenvolvida com Spring Boot, para gerenciamento de médicos, pacientes, usuários e consultas médicas. A API oferece autenticação com JWT, controle de acesso por perfil, persistência em PostgreSQL e versionamento do banco com Flyway.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA e Hibernate
- Spring Security
- PostgreSQL
- Flyway
- Maven
- OpenAPI/Swagger UI

## Pré-requisitos

- JDK 21 configurado no `PATH`
- PostgreSQL em execução
- Um banco de dados PostgreSQL para a aplicação e outro, preferencialmente, para os testes

O projeto inclui o Maven Wrapper (`mvnw` e `mvnw.cmd`), portanto não é necessário instalar o Maven separadamente.

## Variáveis de ambiente

As configurações são carregadas opcionalmente do arquivo `.env`, que não deve ser versionado. Crie esse arquivo na raiz do projeto com os valores do seu ambiente:

```dotenv
DB_URL=url_do_banco_dev
DB_USER=user
DB_PASSWORD=password

DB_URL_TESTE=url_do_banco_teste

DB_URL_PROD=url_do_banco_prod
DB_USER_PROD=user
DB_PASSWORD_PROD=password

JWT_SECRET=uma_chave_secreta_forte
```

| Variável | Uso |
| --- | --- |
| `DB_URL` | URL JDBC do banco usado na execução padrão. |
| `DB_USER` | Usuário do banco usado na execução padrão e nos testes. |
| `DB_PASSWORD` | Senha do banco usado na execução padrão e nos testes. |
| `DB_URL_TESTE` | URL JDBC do banco usado pelo perfil `test`. |
| `DB_URL_PROD` | URL JDBC do banco usado pelo perfil `prod`. |
| `DB_USER_PROD` | Usuário do banco usado pelo perfil `prod`. |
| `DB_PASSWORD_PROD` | Senha do banco usado pelo perfil `prod`. |
| `JWT_SECRET` | Chave usada para assinar e validar os tokens JWT. Defina uma chave forte e exclusiva por ambiente. |

`JWT_SECRET` possui um valor padrão apenas para facilitar o desenvolvimento local. Em ambientes reais, defina a variável explicitamente. As migrações do Flyway são executadas automaticamente na inicialização.

## Executando localmente

Com o PostgreSQL configurado e o `.env` preenchido:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

Por padrão, a aplicação utiliza `application.properties` e fica disponível em `http://localhost:8080`.

Para executar com as configurações de produção:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

No Windows, use `.\mvnw.cmd` no lugar de `./mvnw`.

## Testes

Os testes usam o perfil `test` e exigem um banco PostgreSQL acessível pela variável `DB_URL_TESTE`:

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

## Gerando e executando o JAR

```bash
./mvnw clean package
java -jar target/api-0.0.1-SNAPSHOT.jar
```

Para ativar um perfil ao executar o JAR:

```bash
java -jar target/api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## Documentação da API

Com a aplicação em execução, acesse:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Especificação OpenAPI: `http://localhost:8080/v3/api-docs`

Os endpoints protegidos devem receber um cabeçalho `Authorization` com o esquema `Bearer` e o token JWT. Obtenha o token em `POST /login`.

## Principais endpoints

| Recurso | Operações |
| --- | --- |
| `/login` | Autenticação e emissão do token JWT |
| `/medicos` | Cadastro, consulta, listagem, atualização e inativação de médicos |
| `/pacientes` | Cadastro, consulta, listagem, atualização e inativação de pacientes |
| `/consultas` | Agendamento e cancelamento de consultas |

As operações de alteração e inativação que exigem privilégios administrativos devem ser chamadas por um usuário com a autoridade `ROLE_ADMIN`.

## Estrutura do projeto

- `src/main/java`: código-fonte da aplicação
- `src/main/resources/application*.properties`: configurações por ambiente
- `src/main/resources/db/migration`: scripts de migração do banco
- `src/test`: testes automatizados

## Licença

Este projeto utiliza a licença Apache 2.0.
