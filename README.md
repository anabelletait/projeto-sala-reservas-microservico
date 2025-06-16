# Sistema de Reserva de Salas

Este é um sistema de microserviços para gerenciamento de reservas de salas, desenvolvido com Spring Boot e utilizando uma arquitetura distribuída. O sistema implementa conceitos de Domain-Driven Design (DDD) para garantir uma modelagem de domínio rica e coesa, com foco em agregados, entidades de domínio e eventos de domínio.

## 🏗️ Arquitetura

O sistema é composto por vários microserviços que trabalham em conjunto para fornecer uma solução completa de gerenciamento de reservas de salas.

### Componentes Principais

#### 1. Microserviço de Usuário (Porta 8083)
- Responsável pelo gerenciamento de usuários do sistema
- Funcionalidades:
  - Cadastro de usuários
  - Autenticação e autorização
  - Gerenciamento de perfis
  - Validação de permissões
- Banco de dados dedicado (PostgreSQL)
- Comunica-se com outros serviços via API Gateway

#### 2. Microserviço de Sala (Porta 8085)
- Gerencia o catálogo de salas disponíveis
- Funcionalidades:
  - Cadastro de salas
  - Consulta de disponibilidade
  - Gerenciamento de recursos das salas
  - Status de manutenção
- Banco de dados dedicado (PostgreSQL)
- Expõe APIs para consulta de disponibilidade

#### 3. Microserviço de Reserva (Porta 8084)
- Core do sistema de reservas
- Funcionalidades:
  - Criação de reservas
  - Cancelamento de reservas
  - Consulta de reservas
  - Validação de conflitos
- Utiliza RabbitMQ para comunicação assíncrona
- Banco de dados dedicado (PostgreSQL)
- Integra-se com os serviços de Usuário e Sala

#### 4. API Gateway (Porta 8080)
- Ponto único de entrada para todas as requisições
- Responsabilidades:
  - Roteamento de requisições
  - Balanceamento de carga
  - Rate limiting
  - Autenticação centralizada
  - Logging de requisições
- Implementado com Spring Cloud Gateway

#### 5. Frontend (Porta 3000)
- Interface web do sistema
- Tecnologias:
  - Nginx como servidor web
  - Aplicação SPA (Single Page Application)
- Comunica-se com o backend através do API Gateway

## 🛠️ Tecnologias Utilizadas

### Backend
- **Spring Boot 3.2.3**: Framework principal para desenvolvimento dos microserviços
- **Spring Cloud Gateway**: Para implementação do API Gateway
- **Spring Data JPA**: Para persistência de dados
- **Spring AMQP**: Para integração com RabbitMQ
- **PostgreSQL**: Banco de dados relacional
- **RabbitMQ**: Message broker para comunicação assíncrona
- **Lombok**: Para redução de código boilerplate
- **Maven**: Gerenciamento de dependências e build

### Frontend
- **Nginx**: Servidor web para servir a aplicação frontend
- **HTML/CSS/JavaScript**: Para interface do usuário
- **Thymeleaf**: Template engine

### Infraestrutura
- **Docker**: Containerização dos serviços
- **Docker Compose**: Orquestração dos containers
- **Adminer**: Interface web para gerenciamento dos bancos de dados

## 📦 Estrutura do Projeto

O projeto segue os princípios do Domain-Driven Design (DDD), com uma estrutura organizada por domínios e bounded contexts.

```
.
├── application/           # Frontend da aplicação
├── gateway/              # API Gateway
├── reserva/              # Microserviço de Reservas (Core Domain)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/reserva/
│   │   │   │       ├── domain/           # Entidades e regras de domínio
│   │   │   │       │   ├── model/        # Entidades e Value Objects
│   │   │   │       │   ├── repository/   # Interfaces de repositório
│   │   │   │       │   └── service/      # Serviços de domínio
│   │   │   │       ├── application/      # Casos de uso e serviços de aplicação
│   │   │   │       ├── infrastructure/   # Implementações técnicas
│   │   │   │       └── interfaces/       # Controllers e DTOs
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── sala/                 # Microserviço de Salas (Supporting Domain)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/sala/
│   │   │   │       ├── domain/           # Entidades e regras de domínio
│   │   │   │       │   ├── model/        # Entidades e Value Objects
│   │   │   │       │   ├── repository/   # Interfaces de repositório
│   │   │   │       │   └── service/      # Serviços de domínio
│   │   │   │       ├── application/      # Casos de uso e serviços de aplicação
│   │   │   │       ├── infrastructure/   # Implementações técnicas
│   │   │   │       └── interfaces/       # Controllers e DTOs
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── usuario/              # Microserviço de Usuários (Supporting Domain)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/usuario/
│   │   │   │       ├── domain/           # Entidades e regras de domínio
│   │   │   │       │   ├── model/        # Entidades e Value Objects
│   │   │   │       │   ├── repository/   # Interfaces de repositório
│   │   │   │       │   └── service/      # Serviços de domínio
│   │   │   │       ├── application/      # Casos de uso e serviços de aplicação
│   │   │   │       ├── infrastructure/   # Implementações técnicas
│   │   │   │       └── interfaces/       # Controllers e DTOs
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
└── docker-compose.yml
```

### Organização por Camadas (Cada Microserviço)

#### Domain Layer
- **model/**: Contém as entidades de domínio, value objects e regras de negócio
- **repository/**: Interfaces dos repositórios de domínio
- **service/**: Serviços de domínio que implementam regras de negócio

#### Application Layer
- **service/**: Implementação dos casos de uso
- **dto/**: Objetos de transferência de dados
- **mapper/**: Conversores entre entidades e DTOs

#### Infrastructure Layer
- **config/**: Configurações técnicas
- **persistence/**: Implementações dos repositórios
- **messaging/**: Integrações com RabbitMQ
- **security/**: Configurações de segurança

#### Interface Layer
- **controller/**: Endpoints REST
- **dto/**: DTOs específicos para API
- **exception/**: Tratamento de exceções

### Bounded Contexts

1. **Reserva (Core Domain)**
   - Responsável pela lógica central de reservas
   - Coordena interações entre usuários e salas
   - Implementa regras de negócio complexas

2. **Sala (Supporting Domain)**
   - Gerencia o catálogo de salas
   - Fornece informações de disponibilidade
   - Mantém características e recursos

3. **Usuário (Supporting Domain)**
   - Gerencia informações dos usuários
   - Controla autenticação e autorização
   - Mantém perfis e permissões

### Context Mapping

- **API Gateway**: Atua como camada anti-corrupção
- **RabbitMQ**: Facilita comunicação assíncrona entre contextos
- **Eventos**: Permitem desacoplamento entre domínios

## 🚀 Como Executar

### Pré-requisitos
1. Docker Desktop instalado e em execução
2. Git instalado
3. Mínimo 4GB de RAM disponível
4. Mínimo 10GB de espaço em disco

### Passos para Execução

1. Clone o repositório:
   ```bash
   git clone [URL_DO_REPOSITÓRIO]
   cd microservicos-reservas-salas
   ```

2. Inicie o Docker Desktop e aguarde até que esteja completamente carregado

3. Na pasta raiz do projeto, execute:
   ```bash
   docker-compose up -d
   ```

4. Aguarde alguns minutos até que todos os serviços estejam rodando. Você pode verificar o status com:
   ```bash
   docker-compose ps
   ```

5. Acesse a aplicação:
   - Frontend: http://localhost:3000
   - API Gateway: http://localhost:8080
   - Adminer (Banco de Dados): http://localhost:8089
   - RabbitMQ Management: http://localhost:15672

## 🔌 Portas e Acessos

- **API Gateway**: http://localhost:8080
- **Frontend**: http://localhost:3000
- **Adminer (Banco de Dados)**: http://localhost:8089
  - Sistema: PostgreSQL
  - Servidor: dbUsuario/dbReserva/dbSala
  - Banco: usuariodb/reservadb/saladb
- **RabbitMQ Management**: http://localhost:15672

## 📊 Bancos de Dados

O sistema utiliza três bancos de dados PostgreSQL separados:

1. **Usuário DB** (Porta 5433)
   - Banco: usuariodb

2. **Reserva DB** (Porta 5434)
   - Banco: reservadb

3. **Sala DB** (Porta 5435)
   - Banco: saladb

## 🔄 Comunicação entre Serviços

- Os serviços se comunicam através do API Gateway
- O RabbitMQ é utilizado para comunicação assíncrona entre os serviços
- Cada serviço possui sua própria rede Docker para isolamento

## 🔒 Segurança

- Cada serviço possui seu próprio banco de dados
- As credenciais são gerenciadas através de variáveis de ambiente
- O sistema utiliza redes Docker isoladas para cada serviço
- Recomenda-se alterar todas as senhas padrão em ambiente de produção 