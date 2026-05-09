# Gestão de Veículos

Aplicação Spring Boot para gerenciamento de veículos, marcas e modelos, com API REST e interface estática simples.

## Requisitos

- Java 17
- Maven 3.9+ ou o wrapper do projeto
- MySQL rodando localmente

## Configuração do banco

O projeto usa as credenciais definidas em `src/main/resources/application.properties`:

- banco: `gestao_veiculos`
- usuário: `gestao`
- senha: `gestao`

Antes de subir a aplicação, crie o banco no MySQL:

```sql
CREATE DATABASE gestao_veiculos;
CREATE USER 'gestao'@'localhost' IDENTIFIED BY 'gestao';
GRANT ALL PRIVILEGES ON gestao_veiculos.* TO 'gestao'@'localhost';
FLUSH PRIVILEGES;
```

Se o usuário já existir, ajuste apenas a senha e os privilégios conforme o seu ambiente.

## Como executar

Na raiz do projeto, rode:

```bash
./mvnw spring-boot:run
```

No Windows, use:

```powershell
mvnw.cmd spring-boot:run
```

Depois disso, a aplicação fica disponível em `http://localhost:8080`.

## Como testar

Execute a suíte de testes com:

```bash
./mvnw test
```

No Windows:

```powershell
mvnw.cmd test
```

## Estrutura da API

Os principais endpoints REST ficam em:

- `GET /api/marcas`
- `POST /api/marcas`
- `GET /api/modelos`
- `POST /api/modelos`
- `GET /api/veiculos`
- `POST /api/veiculos`
- `PUT /api/veiculos/{id}`
- `DELETE /api/veiculos/{id}`

## Interface

A pasta `src/main/resources/static/` contém a interface web estática:

- `index.html`
- `style.css`
- `app.js`

Ao subir a aplicação, abra a página inicial no navegador para acessar a interface.