<div align="center">

# E-Commerce API
**API RESTful contruiída com Java 25 LTS e Spring Boot 4**, projetada com integridade transacional, controle de concorrência, segurança baseada em padrões de mercado e observabilidade desde a fundação.

![Java](https://img.shields.io/badge/Java-25%20LTS-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)

> **Status Atual:** 🚧 Em Desenvolvimento - **Spring 0 (Fundação & Infraestrutura)

</div>
## 🎯 Visão Geral

Este repositório implementa o backend de uma plataforma de e-commerce seguindo rigorosas práticas de engenharia de software. Diferente de aplicações CRUD convencionais, o projeto estabelece desde a **Sprint 0** uma base pronta para produção, com isolamento de ambiente via containers, versionamento determinístico de schema de banco de dados, testes de integração contra instâncias reais e integração contínua automatizada.

---

## Decisões Arquiteturais

| Decisão | Escolha Técnica | Motivação |
| :--- | :--- | :--- |
| **Organização de Pacotes** | *Package-by-Feature* | Agrupa código por domínio de negócio em vez de camadas técnicas, elevando a coesão e reduzindo o acoplamento entre módulos. |
| **Versionamento de Schema** | Flyway Migrations | Garante rastreabilidade, reprodutibilidade e evolução controlada do banco de dados em todos os ambientes (local, CI e produção). |
| **Testes de Integração** | Testcontainers (PostgreSQL 17) | Elimina falsos positivos causados por bancos em memória (como H2), validando queries e constraints diretamente no PostgreSQL real. |
| **Tratamento de Erros** | Problem Details (RFC 9457) | Padroniza contratos de erro HTTP de forma semântica e previsível para clientes da API. |
| **Observabilidade Inicial** | Spring Boot Actuator | Expõe probes de *health* e *readiness* indispensáveis para orquestradores de containers e monitoramento. |

---

## Stack Tecnológica

* **Linguagem & Runtime:** Java 25 (LTS)
* **Framework Principal:** Spring Boot 4.0.x (Spring Web, Spring Data JPA, Spring Validation)
* **Banco de Dados & Migrations:** PostgreSQL 17, Hibernate, Flyway
* **Observabilidade:** Spring Boot Actuator
* **Testes Automatizados:** JUnit 5, AssertJ, Spring Boot Test, Testcontainers
* **Containerização:** Docker & Docker Compose
* **Build & Automação:** Maven Wrapper (`./mvnw`) & GitHub Actions

---

## Como Executar Localmente

### Pré-requisitos

Certifique-se de ter instalados em seu ambiente:
* **JDK 25** (`java -version`)
* **Docker** e **Docker Compose** em execução (`docker info`)
* **Git**

### Passo a passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/ecommerce-api.git](https://github.com/seu-usuario/ecommerce-api.git)
   cd ecommerce-api
   ```

2. **Inicie a infraestrutura de banco de dados:**
   ```bash
   docker compose up -d
   ```

3. **Execute a aplicação via Maven Wrapper:**
   ```bash
   ./mvnw spring-boot:run
   ```
   > Na inicialização, o **Flyway** validará e aplicará automaticamente as migrations pendentes em `src/main/resources/db/migration`.

4. **Valide o status da aplicação (Health Check):**
   ```bash
   curl -i http://localhost:8080/actuator/health
   ```
   **Resposta esperada (`200 OK`):**
   ```json
   {
     "status": "UP"
   }
   ```

---
## Testes & Garantia de Qualidade

A suíte de testes utiliza **Testcontainers** para provisionar automaticamente um container efêmero do **PostgreSQL 17** durante a execução, garantindo paridade completa com o banco de produção.

Para executar todos os testes:

```bash
./mvnw verify
```

---
## Pipeline CI/CD

Cada *push* ou *Pull Request* dispara o workflow configurado em `.github/workflows/ci.yml`, executando:
1. Checkout do código e setup do **JDK 25** com cache de dependências Maven.
2. Compilação estrita do projeto (`./mvnw clean compile`).
3. Execução integral dos testes unitários e de integração com Testcontainers (`./mvnw verify`).
---
## Padrões de Engenharia & Git

* **Fluxo de Branches:** Desenvolvimento isolado em *feature branches* por tarefa (`feat/p1-t01-setup`, `chore/p1-t02-docker`).
* **Conventional Commits:** Histórico semântico e auditável (`feat:`, `fix:`, `chore:`, `test:`, `docs:`, `refactor:`).
* **TDD (Test-Driven Development):** Regras de negócio cobertas por testes antes ou em conjunto com a implementação.

---
## Roadmap de Evolução
* [x] **Sprint 0 — Fundação & Infraestrutura:** Setup Maven, Docker Compose (PostgreSQL 17), Flyway, Testcontainers, Actuator e CI no GitHub Actions.
* [ ] **Sprint 1 — Autenticação & Segurança:** Spring Security, OAuth2 Resource Server (JWT HS256), Refresh Token com rotação e detecção de reuso.
* [ ] **Sprint 2 — Catálogo & Estoque:** CRUD de categorias/produtos e controle de concorrência otimista (`@Version`).
* [ ] **Sprint 3 — Carrinho & Pedidos:** Precisão monetária (`BigDecimal`), snapshot de preços e máquina de estados do pedido.
* [ ] **Sprint 4 — Pagamentos & Idempotência:** Processamento seguro via `Idempotency-Key` e validação de payload canônico.
* [ ] **Sprints 5 a 7 — Qualidade, Documentação & Observabilidade:** OpenAPI/Swagger, Problem Details (RFC 9457), logs estruturados e métricas.