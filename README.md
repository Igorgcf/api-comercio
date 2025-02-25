# Api-comercio - Sistema de Gestão para Comércios

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Api-comercio é uma aplicação backend desenvolvida utilizando o modelo arquitetural monolítico, projetada para oferecer uma solução flexível e completa para a gestão de diversos tipos de comércios.

- Estrutura e Funcionalidades

A aplicação não implementa camadas de segurança ou login, permitindo um acesso simplificado às funcionalidades através de APIs REST e APIs RESTful. As APIs disponíveis incluem:

API RESTful para Usuários: Gerencia e consulta os dados dos usuários cadastrados na aplicação.

APIREST Roles de Usuários: Gerencia as roles (papéis) atribuídas aos usuários, facilitando a configuração de permissões e acessos específicos.

API RESTful para Categorias: Gerencia as categorias dos produtos, permitindo a organização do catálogo de forma eficiente e personalizada para diferentes tipos de comércio.

API RESTful para Produtos: Focada no gerenciamento de produtos, possibilitando criar, atualizar, excluir e consultar informações sobre os itens disponíveis no comércio.

- O que a aplicação proporciona?

Api-comercio foi desenvolvida para atender as necessidades de gerenciamento de diversos tipos de comércio, proporcionando uma plataforma unificada para a administração de usuários, categorias e produtos. Sua arquitetura monolítica e a ausência de camadas de segurança tornam a aplicação leve e fácil de integrar, ideal para comércios que buscam uma solução direta e eficiente.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [Observation](#observation)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/IgorTecnologia/api-comercio.git
```

2. Install dependencies with Maven

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080

## Collection Postman

Download these files and import them into your Postman to use the ready-made HTTP methods along with the already configured environment variables, to perform the requests/responses

[Download Collections](https://github.com/IgorTecnologia/api-comercio/blob/docs-postman/Api-comercio-collection.json)

[Download Environment variables](https://github.com/IgorTecnologia/api-comercio/blob/docs-postman/Local-%20host-environment.json)

## API Endpoints
The API provides the following endpoints:

**GET USERS**
```markdown
GET /users - Retrieve a pagination of all users.
```
```json
{
"content": [
        {
            "id": "6a3f3d90-4abe-4dd8-b150-962109c3884b",
            "firstName": "Gustavo",
            "lastName": "Barros",
            "email": "gustavo@gmail.com",
            "roles": [
                {
                    "id": "76d1a6cc-0e15-4936-84e8-0e59cc800dc1",
                    "authority": "Seller"
                }
            ],
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/users/6a3f3d90-4abe-4dd8-b150-962109c3884b"
                }
            ]
        },
}

```
**GET USERS**
```markdown
GET /users/firstName/{firstName} - Retrieve a list of users filtered by firstName.
Exemple: GET /users/firstName/Igor
```
```json
   [
    {
        "id": "6a3f3d90-4abe-4dd8-b150-962109c3884b",
        "firstName": "Gustavo",
        "lastName": "Barros",
        "email": "gustavo@gmail.com",
        "roles": [
            {
                "id": "76d1a6cc-0e15-4936-84e8-0e59cc800dc1",
                "authority": "Seller"
            }
        ],
        "links": []
    }
]

```
**GET USERS/ID**
```markdown
GET /users/id - Retrieve a single user by id.
```

```json
{
    "id": "6a3f3d90-4abe-4dd8-b150-962109c3884b",
    "firstName": "Gustavo",
    "lastName": "Barros",
    "email": "gustavo@gmail.com",
    "roles": [
        {
            "id": "76d1a6cc-0e15-4936-84e8-0e59cc800dc1",
            "authority": "Seller"
        }
    ]
}
```

**POST USERS**
```markdown
POST /users - Register a new user into the App
```
```json
{
    "id": "14e3e76c-ee74-43e8-9d11-b31ff11a09e0",
    "firstName": "Vitória",
    "lastName": "Gonçalves",
    "email": "vitoria@gmail.com",
    "roles": [
        {
            "id": "941546f5-019a-4e73-a09c-3100eb5f68bf",
            "authority": "Assistent"
        }
    ]
}
```
**PUT USERS**
```markdown
PUT/users/id - Update a user in the application by id.
```
```json
{
    "id": "14e3e76c-ee74-43e8-9d11-b31ff11a09e0",
    "firstName": "Vitória",
    "lastName": "Gonçalves",
    "email": "vitoria@gmail.com.br",
    "roles": [
        {
            "id": "0faeb913-d7ec-4a7d-a0ef-b79831b5ea94",
            "authority": "Admin"
        }
    ]
}
```
**DELETE USERS**
```markdown
DELETE/users/id - Delete a user in the application by id.
return HTTP status: 200.
Body: User deleted successfully.

```
## Database
The project utilizes [H2 Database](https://www.h2database.com/html/tutorial.html) as the database.

There are settings for PostgreSQL, MySQL databases, you can use them by changing the application.properties file.

The application comes with the H2 database as standard.

## Technologies Used

- JDK 17
- Spring Boot
- Maven
- H2 Database
- IntelliJ IDEA Community
- Postman

## Observation
This APIRest provides other endpoints besides users, such as:
/categories
/products
/role
Located in the Application resources layer.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, abra um problema ou envie uma solicitação pull ao repositório.

Ao contribuir para este projeto, siga o estilo de código existente, [convenções de commit](https://medium.com/linkapi-solutions/conventional-commits-pattern-3778d1a1e657), e envie suas alterações em uma branch separado.

![Java mascot](https://img-c.udemycdn.com/course/750x422/3569929_d77b.jpg)
