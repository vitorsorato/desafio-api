## **Desafio Técnico Softplan**

### **Visão Geral**

Este projeto foi desenvolvido como resposta ao desafio técnico da Softplan. A aplicação consiste em uma API REST em Java, frontend em React, infraestrutura com Docker compose e utilização de TDD.

![Tela Inicial](https://github.com/vitorsorato/softplan-desafio-api/blob/main/front.gif)

### **Tecnologias Utilizadas**
* **Backend:** Java, Spring Boot, PostgreSQL
* **Frontend:** React, TypeScript, MUI
* **Infraestrutura:** Docker Compose

## Estrutura do Projeto

### Diretório Backend

- **desafio**: Diretório contendo a aplicação backend.
  - **src**: Código fonte java.
  - **target**: Diretório onde o build está gerado.
  - **Dockerfile**: Arquivo para construir a imagem Docker da aplicação backend.
  - **nginx.conf**: Configuração do Nginx para o backend.
  - **pom.xml**: Gerenciador de dependências do Maven.
  - **script.sql**: Script SQL para inicialização do banco de dados.

### Diretório Frontend

- **desafio-front**: Diretório contendo a aplicação frontend.
  - **build**: Diretório onde o build está gerado.
  - **src**: Código fonte da aplicação frontend.
  - **default.conf**: Configuração do Nginx para o frontend.
  - **Dockerfile**: Arquivo para construir a imagem Docker da aplicação frontend.
  - **package.json**: Gerenciador de dependências do Node.js.
  - **tsconfig.json**: Configurações do TypeScript.

### Arquivos de Configuração

- **.gitignore**: Arquivo para ignorar arquivos e pastas no repositório Git.
- **docker-compose.yml**: Arquivo para definir e executar múltiplos containers Docker. Inclui serviços para backend, frontend e Nginx.
- **README.md**: Documento de leitura do projeto principal.

## Endpoints da API

### 1. Listar Todos os Clientes
- **URL:** `/clientes`
- **Método:** `GET`
- **Descrição:** Retorna a lista de todos os clientes com seus detalhes, incluindo limite e saldo.

### 2. Obter Extrato de Transações
- **URL:** `/clientes/[id]/extrato`
- **Método:** `GET`
- **Descrição:** Obtém o extrato de transações do cliente com o ID especificado.

### 3. Criar Transação
- **URL:** `/clientes/[id]/transacoes`
- **Método:** `POST`
- **Descrição:** Cria uma nova transação para o cliente com o ID especificado.


## Configuração do Load Balancer e Frontend

### Balanceador de Carga (Nginx)

- **O que faz**: O Nginx é utilizado como um balanceador de carga para distribuir o tráfego entre duas instâncias da API. Ele também atua como um proxy reverso, encaminhando as requisições do frontend para as APIs backend.
- **Como funciona**: O Nginx escuta nas portas 9999 e 80. Ele distribui as requisições entre as instâncias `api1` e `api2`, e configura os cabeçalhos de solicitação apropriados.

### Configuração do Frontend

- **O que faz**: O frontend é servido por um servidor Nginx, que fornece a aplicação React construída como arquivos estáticos.
- **Como funciona**: A aplicação React é construída e copiada para a pasta padrão de arquivos estáticos do Nginx. O Nginx serve esses arquivos e lida com o roteamento, garantindo que a aplicação funcione corretamente no navegador.



## Como Rodar o Servidor com Docker

1. **Compile e Construa a Imagem Docker para o Backend**
   Navegue até o diretório `desafio` e execute:
   ```bash
   cd desafio
   docker build -t api-desafio:latest .
   ```

2. **Compile e Construa a Imagem Docker para o Frontend**
   Navegue até o diretório `desafio-front` e execute:
   ```bash
   cd ../desafio-front
   docker build -t front-desafio:latest .
   ```

3. **Inicie os Containers Docker**
   Navegue até o diretório onde está o arquivo `docker-compose.yml` e execute:
   ```bash
   cd ../softplan-desafio-api
   docker-compose up --build -d
   ```
