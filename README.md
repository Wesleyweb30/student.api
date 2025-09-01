# 🎓 Student API

Uma API RESTful desenvolvida com Spring Boot para gerenciar usuários (Alunos, Professores e Instituições) com autenticação JWT e controle de eventos institucionais.

---

## 📚 Funcionalidades

- Cadastro e login de usuários (Aluno, Professor e Instituição)
- Criação, listagem e exclusão de eventos
- Inscrição de alunos em eventos
- Controle de permissões por perfil
- Segurança com JWT
- Atualização de dados do usuário autenticado

---

## 🔐 Permissões por papel

| Rota                             | Aluno | Professor | Instituição | Público |
|----------------------------------|:-----:|:---------:|:-----------:|:-------:|
| `POST /auth/register`           | ✅    | ✅        | ✅          | ✅      |
| `POST /auth/login`              | ✅    | ✅        | ✅          | ✅      |
| `GET /eventos`                  | ✅    | ✅        | ✅          | ✅      |
| `GET /eventos/{id}`             | ✅    | ✅        | ✅          | ✅      |
| `POST /eventos`                 | ❌    | ❌        | ✅          | ❌      |
| `DELETE /eventos/{id}`          | ✅    | ✅        | ✅          | ❌      |
| `POST /eventos/{id}/inscrever`  | ✅    | ❌        | ❌          | ❌      |
| `PUT /usuarios/aluno/{id}`      | ✅    | ❌        | ❌          | ❌      |
| `PUT /usuarios/professor/{id}`  | ❌    | ✅        | ❌          | ❌      |
| `GET /usuarios/alunos`          | ❌    | ❌        | ✅          | ❌      |
| `GET /usuarios/professores`     | ❌    | ❌        | ✅          | ❌      |
| `GET /usuarios/me`              | ✅    | ✅        | ✅          | ❌      |

---

## 🛠 Tecnologias

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Maven
- JPA (Hibernate)
- MYSQL

---

## 🚀 Como executar

1. Clone o repositório:

```bash
git clone https://github.com/Wesleyweb30/student-api.git
cd student-api
```

2. Instale as dependências e execute:

```bash
./mvnw spring-boot:run
```
3. Import os Endpoint:
```
Arquivo thunder-collection
```

4. Acesse a API em:
```
http://localhost:8080
```

---

## 🧪 Testes

Você pode testar as rotas usando ferramentas como Thunder Cliente, Postman, Insomnia ou o Swagger (se configurado).

---

## 🔒 Segurança

A autenticação é feita via JWT. Ao realizar o login, o token JWT será retornado e deverá ser enviado no cabeçalho `Authorization` das requisições protegidas:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 📁 Estrutura de diretórios

```
src
├── controller
├── dto
├── entity
├── repository
├── config
├── service
└── util
```

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT.
