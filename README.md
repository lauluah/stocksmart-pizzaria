<div align="center">

```
╔═══════════════════════════════════════════════════════════════╗
║                                                               ║
║        🍕  S T O C K S M A R T  P I Z Z A R I A  🍕         ║
║                                                               ║
║         Sistema de Gestão de Estoque para Pizzarias           ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝
```

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![H2](https://img.shields.io/badge/H2_Database-In--Memory-004088?style=for-the-badge&logo=h2&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

API REST completa para controlar estoque de ingredientes, receitas de pizzas, vendas e movimentações

</div>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como Rodar](#-como-rodar)
- [Endpoints da API](#-endpoints-da-api)
  - [Ingredientes](#-ingredientes)
  - [Pizzas (Receitas)](#-pizzas-receitas)
  - [Vendas](#-vendas)
  - [Movimentações](#-movimentações-manuais)
- [Testando no Postman](#-testando-no-postman)
- [Banco de Dados H2](#-banco-de-dados-h2)
- [Tratamento de Erros](#-tratamento-de-erros)

---

## 🎯 Sobre o Projeto

O **StockSmart Pizzaria** nasceu de um problema real: pizzarias pequenas ainda controlam estoque com papel e caneta, perdendo ingredientes por vencimento, comprando demais ou ficando sem produto no meio do serviço.

### O que a API resolve:

| Problema | Solução |
|---|---|
| Não sabe o que tem em estoque | Listagem em tempo real de todos os ingredientes |
| Esquece o que precisa comprar | Alerta automático de estoque baixo |
| Perde produto vencido | Alerta de ingredientes próximos do vencimento |
| Não sabe o custo de cada pizza | Custo calculado automaticamente pela receita |
| Desconta estoque na mão | Venda registrada = ingredientes debitados automaticamente |

---

## 🛠 Tecnologias

- **Java 21** 
- **Spring Boot 3.2** 
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** 
- **Lombok** 
- **Maven** 
- **Postman** 

---

## 📁 Estrutura do Projeto

```
src/main/java/com/stocksmart/api/
│
├── 📂 controller/          ← Recebe as requisições HTTP
│   ├── IngredienteController.java
│   ├── PizzaController.java
│   ├── MovimentacaoController.java
│   └── VendaController.java
│
├── 📂 service/             ← Regras de negócio (a inteligência da API)
│   ├── IngredienteService.java
│   ├── PizzaService.java
│   ├── MovimentacaoService.java
│   └── VendaService.java
│
├── 📂 repository/          ← Comunicação com o banco de dados
│   ├── IngredienteRepository.java
│   ├── PizzaRepository.java
│   ├── MovimentacaoRepository.java
│   └── VendaRepository.java
│
├── 📂 model/               ← Entidades (tabelas do banco)
│   ├── Ingrediente.java
│   ├── Pizza.java
│   ├── ReceitaItem.java
│   ├── Movimentacao.java
│   └── Venda.java
│
├── 📂 dto/                 ← Objetos de entrada e saída da API
│   ├── IngredienteDTO.java
│   └── PizzaDTO.java
│
├── 📂 exception/           ← Tratamento de erros personalizados
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── EstoqueInsuficienteException.java
│
└── StockmartApiApplication.java   ← Ponto de entrada da aplicação
```

---

## 🚀 Como Rodar

### Pré-requisitos

- Java 17+ instalado
- Maven instalado (ou usar o wrapper `./mvnw`)

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/seu-usuario/stocksmart-pizzaria.git
cd stocksmart-pizzaria
```

**2. Rode a aplicação**
```bash
./mvnw spring-boot:run
```
ou
```bash
mvn spring-boot:run
```

**3. Pronto!** A API estará disponível em:
```
http://localhost:8080
```

> 💡 O banco H2 é criado automaticamente na memória quando a aplicação sobe. Não precisa configurar nada.

---

## 📡 Endpoints da API

### 🧀 Ingredientes

Base URL: `/api/ingredientes`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/ingredientes` | Cadastra um novo ingrediente |
| `GET` | `/api/ingredientes` | Lista todos os ingredientes e quantidades |
| `GET` | `/api/ingredientes/{id}` | Busca um ingrediente específico |
| `PUT` | `/api/ingredientes/{id}` | Atualiza um ingrediente |
| `DELETE` | `/api/ingredientes/{id}` | Remove um ingrediente |
| `GET` | `/api/ingredientes/alertas/estoque-baixo` | Lista ingredientes abaixo do mínimo |
| `GET` | `/api/ingredientes/alertas/vencendo?dias=7` | Lista ingredientes vencendo em X dias |

**Exemplo de body para cadastrar:**
```json
{
  "nome": "Mussarela",
  "unidade": "g",
  "quantidadeEstoque": 5000,
  "quantidadeMinima": 500,
  "precoPorUnidade": 0.04,
  "dataValidade": "2025-12-31"
}
```

**Campos:**
- `unidade` → `"g"`, `"kg"`, `"ml"`, `"unidade"`
- `quantidadeMinima` → abaixo disso, o alerta de estoque baixo é disparado
- `precoPorUnidade` → usado para calcular o custo de cada pizza

---

### 🍕 Pizzas (Receitas)

Base URL: `/api/pizzas`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/pizzas` | Cadastra pizza com receita |
| `GET` | `/api/pizzas` | Lista todas as pizzas com custo calculado |
| `GET` | `/api/pizzas/{id}` | Detalhe de uma pizza |
| `DELETE` | `/api/pizzas/{id}` | Remove uma pizza |

**Exemplo de body para cadastrar:**
```json
{
  "nome": "Margherita",
  "descricao": "Clássica com molho, mussarela e manjericão",
  "tamanho": "GRANDE",
  "ingredientes": [
    { "ingredienteId": 1, "quantidade": 150 },
    { "ingredienteId": 2, "quantidade": 100 },
    { "ingredienteId": 3, "quantidade": 1 }
  ]
}
```

**Tamanhos disponíveis:** `PEQUENA` | `MEDIA` | `GRANDE` | `FAMILIA`

> 💰 O campo `custoTotal` é calculado automaticamente com base nos preços dos ingredientes da receita.

---

### 💰 Vendas

Base URL: `/api/vendas`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/vendas` | Registra venda e debita estoque automaticamente |
| `GET` | `/api/vendas` | Histórico de todas as vendas |
| `GET` | `/api/vendas/pizza/{id}` | Vendas de uma pizza específica |

**Exemplo de body:**
```json
{
  "pizzaId": 1,
  "quantidade": 2,
  "observacao": "Mesa 3 - delivery"
}
```

> ⚡ **Automático:** ao registrar uma venda, o sistema verifica se há estoque suficiente para TODOS os ingredientes da receita e debita tudo de uma vez. Se faltar algum ingrediente, retorna erro `422` antes de fazer qualquer desconto.

---

### 📦 Movimentações Manuais

Base URL: `/api/movimentacoes`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/movimentacoes` | Registra entrada, saída ou desperdício |
| `GET` | `/api/movimentacoes` | Histórico completo de movimentações |
| `GET` | `/api/movimentacoes/ingrediente/{id}` | Histórico de um ingrediente |
| `GET` | `/api/movimentacoes/desperdicios` | Relatório de descartes |

**Exemplo de body — Reposição de estoque (compra):**
```json
{
  "ingredienteId": 1,
  "tipo": "ENTRADA",
  "quantidade": 2000,
  "observacao": "Compra fornecedor Laticinios ABC"
}
```

**Exemplo de body — Descarte por vencimento:**
```json
{
  "ingredienteId": 1,
  "tipo": "DESPERDICIO",
  "quantidade": 300,
  "observacao": "Mussarela vencida descartada"
}
```

**Tipos disponíveis:**

| Tipo | Efeito no estoque | Quando usar |
|---|---|---|
| `ENTRADA` | ➕ Soma ao estoque | Chegou mercadoria |
| `SAIDA` | ➖ Subtrai do estoque | Consumo fora de venda |
| `DESPERDICIO` | ➖ Subtrai do estoque | Produto vencido ou descartado |

---

## 🧪 Testando com Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/39388795/2sBXqQEH9i)

📖 [Documentação completa da API](https://documenter.getpostman.com/view/39388795/2sBXqQEH9i)

### Ordem recomendada de teste

```
PASSO 1 → Cadastre os ingredientes (Mussarela, Molho, Massa, Pepperoni)
PASSO 2 → Cadastre as pizzas (Margherita, Pepperoni)
PASSO 3 → Registre uma venda
PASSO 4 → Liste os ingredientes e veja o estoque diminuir
PASSO 5 → Consulte os alertas de estoque baixo
```

### Variável de ambiente

A collection já vem configurada com:
```
{{baseUrl}} = http://localhost:8080
```

---

## 🗄 Banco de Dados H2

O H2 é um banco de dados que roda direto na memória — não precisa instalar MySQL, PostgreSQL ou qualquer outra coisa.

> ⚠️ Os dados são apagados toda vez que a aplicação é reiniciada. Ideal para desenvolvimento e testes.

### Acessar o console visual

Com a aplicação rodando, abra no navegador:
```
http://localhost:8080/h2-console
```

Configure assim:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:pizzariadb` |
| User Name | `sa` |
| Password | *(deixe em branco)* |

Lá você consegue ver as tabelas, executar SQL e inspecionar os dados.

---

## ⚠️ Tratamento de Erros

A API retorna erros padronizados em JSON:

### 404 — Recurso não encontrado
```json
{
  "status": 404,
  "mensagem": "Pizza com id 99 não encontrada.",
  "timestamp": "2026-05-12T10:30:00"
}
```

### 422 — Estoque insuficiente
```json
{
  "status": 422,
  "mensagem": "Estoque insuficiente para 'Mussarela'. Disponível: 100.0 | Necessário: 450.0",
  "timestamp": "2026-05-12T10:30:00"
}
```

### 400 — Dados inválidos
```json
{
  "status": 400,
  "mensagem": "Erro de validação",
  "erros": {
    "nome": "Nome é obrigatório",
    "quantidadeEstoque": "Quantidade não pode ser negativa"
  }
}
```

---

<div align="center">


</div>
