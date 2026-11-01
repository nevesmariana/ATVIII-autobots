# AutoManager - AutoBots

Sistema desenvolvido para as atividades práticas da disciplina, utilizando uma arquitetura baseada em Spring Boot (micro-serviço) para gerenciamento de uma rede de manutenção veicular e venda de autopeças.

O projeto implementa CRUD e HATEOAS para usuários, empresas, veículos, mercadorias, serviços, vendas, credenciais, documentos, e-mails, endereços e telefones, utilizando DTOs, Services, Repositories, Controllers, Mappers e regras de negócio.

---

# Objetivo das atividades

- **ATVI:** completar o CRUD básico de um sistema de gestão utilizando uma arquitetura organizada.
- **ATVII:** evoluir a API para atender os quatro níveis do Richardson Maturity Model (RMM), incluindo HATEOAS.
- **ATVIII ("atualização de base"):** ampliar o domínio do sistema para atender os investidores (Toyota e Volkswagen): empresas, tipos de usuário, veículos, mercadorias, serviços, vendas e credenciais de acesso, mantendo CRUD e HATEOAS em todas as entidades.

## Tecnologias utilizadas

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 2.6.3 |
| Maven | Maven Wrapper (`mvnw`) |
| Hibernate | 5.6.x |
| Lombok | Utilizado no projeto |
| Spring Data JPA | Utilizado no projeto |
| Spring Web | Utilizado no projeto |
| Spring Validation | Utilizado no projeto |
| Spring HATEOAS | Utilizado no projeto |
| H2 | Banco em memória (os dados zeram a cada reinício) |

---

## Como executar o projeto

Entre na pasta do projeto:

```
cd automanager
```

Execute a aplicação (Java 17):

```cmd
mvnw.cmd spring-boot:run
```

Quando a aplicação iniciar corretamente, será exibido:

```text
Tomcat started on port(s): 8080
```

A API estará disponível em:

```text
http://localhost:8080
```
---

# Modelo de domínio

```
Empresa ──┬── Usuario (cliente, fornecedor ou funcionário)
          ├── Mercadoria
          ├── Servico
          └── Venda ──── cliente (Usuario), funcionário (Usuario), Veiculo,
                         mercadorias (N), serviços (N)

Usuario ──┬── Telefone (N), Endereco, Documento (N), Email (N)   → dentro do JSON
          ├── Credencial (N): usuário/senha ou código de barras  → associação
          ├── Mercadoria (N)                                     → associação
          └── Veiculo (N, via proprietário)

Empresa ──── Telefone (N), Endereco                              → dentro do JSON
```

## Duas formas de relacionar entidades

| Natureza | Entidades | Como funciona |
|---|---|---|
| **Composição** (nascem e morrem com o dono) | telefones, endereço, documentos e e-mails do usuário; telefones e endereço da empresa | Vão **dentro do JSON** do usuário/empresa e são criados junto com ele |
| **Associação** (já existem sozinhas) | credenciais e mercadorias de um usuário; usuários, mercadorias e serviços de uma empresa | Criados em seus próprios endpoints e **ligados depois** por endpoints de associação |

---

# Níveis de maturidade (RMM)

| Nível | Conceito | Como foi atendido |
|---|---|---|
| 0 | Uso do protocolo HTTP | API REST sobre HTTP, com JSON |
| 1 | URI única por recurso | `/usuarios`, `/empresas`, `/veiculos`, `/vendas` etc. (substantivos, sem verbos na URI) |
| 2 | Métodos HTTP e suas semânticas | GET consulta, POST cria, PUT atualiza, DELETE remove, cada um com o status HTTP adequado |
| 3 | HATEOAS | Respostas trazem `_links` (Spring HATEOAS, formato HAL) para navegação entre os recursos |

## Ponto de entrada

```http
GET /
```

Retorna links para todos os recursos principais (`empresas`, `usuarios`, `veiculos`, `mercadorias`, `servicos`, `vendas`, `credenciais`, `documentos`, `emails`, `enderecos` e `telefones`). Um consumidor da API só precisa conhecer essa URL e seguir os links.

## Links retornados (`_links`)

| Resposta | Links |
|---|---|
| Item de uma listagem | `self` |
| Recurso individual | `self`, link da coleção, `atualizar`, `excluir` |
| Coleção | `self`, `cadastrar` (e, em usuários, `clientes`, `fornecedores` e `funcionarios`) |
| Itens associados (credenciais, mercadorias etc.) | `self` e `desassociar` |
| Usuário | `veiculos` e `compras` (vendas em que é cliente) |
| Empresa | `vendas` |
| Veículo / Venda | `self` do proprietário, cliente, funcionário, empresa, mercadorias e serviços relacionados |

---

# Rotas da API

As rotas podem ser testadas com Postman, Insomnia, Thunder Client ou navegador (para GET).

| Recurso | Listar | Buscar | Cadastrar | Atualizar | Excluir |
|---|---|---|---|---|---|
| Usuário | `GET /usuarios` | `GET /usuarios/{id}` | `POST /usuarios` | `PUT /usuarios/{id}` | `DELETE /usuarios/{id}` |
| Empresa | `GET /empresas` | `GET /empresas/{id}` | `POST /empresas` | `PUT /empresas/{id}` | `DELETE /empresas/{id}` |
| Veículo | `GET /veiculos` | `GET /veiculos/{id}` | `POST /veiculos` | `PUT /veiculos/{id}` | `DELETE /veiculos/{id}` |
| Mercadoria | `GET /mercadorias` | `GET /mercadorias/{id}` | `POST /mercadorias` | `PUT /mercadorias/{id}` | `DELETE /mercadorias/{id}` |
| Serviço | `GET /servicos` | `GET /servicos/{id}` | `POST /servicos` | `PUT /servicos/{id}` | `DELETE /servicos/{id}` |
| Venda | `GET /vendas` | `GET /vendas/{id}` | `POST /vendas` | `PUT /vendas/{id}` | `DELETE /vendas/{id}` |
| Credencial (usuário/senha) | `GET /credenciais/usuario-senha` | `GET /credenciais/usuario-senha/{id}` | `POST /credenciais/usuario-senha` | `PUT /credenciais/usuario-senha/{id}` | `DELETE /credenciais/usuario-senha/{id}` |
| Credencial (código de barras) | `GET /credenciais/codigo-barra` | `GET /credenciais/codigo-barra/{id}` | `POST /credenciais/codigo-barra` | `PUT /credenciais/codigo-barra/{id}` | `DELETE /credenciais/codigo-barra/{id}` |
| Documento | `GET /documentos` | `GET /documentos/{id}` | `POST /documentos` | `PUT /documentos/{id}` | `DELETE /documentos/{id}` |
| E-mail | `GET /emails` | `GET /emails/{id}` | `POST /emails` | `PUT /emails/{id}` | `DELETE /emails/{id}` |
| Endereço | `GET /enderecos` | `GET /enderecos/{id}` | `POST /enderecos` | `PUT /enderecos/{id}` | `DELETE /enderecos/{id}` |
| Telefone | `GET /telefones` | `GET /telefones/{id}` | `POST /telefones` | `PUT /telefones/{id}` | `DELETE /telefones/{id}` |

## Filtros

| Rota | Filtro |
|---|---|
| `GET /usuarios` | `?perfil=CLIENTE`, `FORNECEDOR` ou `FUNCIONARIO` |
| `GET /veiculos` | `?proprietarioId={id}` |
| `GET /vendas` | `?empresaId={id}` e/ou `?clienteId={id}` |

## Associações

| Operação | Rota |
|---|---|
| Ligar / desligar credencial a um usuário | `POST` / `DELETE /usuarios/{id}/credenciais/{credencialId}` |
| Ligar / desligar mercadoria a um usuário | `POST` / `DELETE /usuarios/{id}/mercadorias/{mercadoriaId}` |
| Ligar / desligar usuário a uma empresa | `POST` / `DELETE /empresas/{id}/usuarios/{usuarioId}` |
| Ligar / desligar mercadoria a uma empresa | `POST` / `DELETE /empresas/{id}/mercadorias/{mercadoriaId}` |
| Ligar / desligar serviço a uma empresa | `POST` / `DELETE /empresas/{id}/servicos/{servicoId}` |

Desligar nunca apaga o item; ele continua existindo e pode ser ligado a outro dono.

## Códigos de status

| Operação | Sucesso | Falha |
|---|---|---|
| GET | 200 | 404 (não encontrado), 400 (parâmetro inválido) |
| POST | 201 + header `Location` (associações: 200) | 400 (validação), 404 (referência inexistente), 409 (duplicidade ou associação já existente) |
| PUT | 200 | 400, 404, 409 |
| DELETE | 204 | 404, 409 (item em uso) |

As respostas de erro seguem o formato `{"erro": "mensagem"}`.

---

# Bodies de exemplo

Datas no formato `yyyy-MM-dd`.

### Usuário (`POST /usuarios`)

```json
{
    "nome": "Jose da Silva",
    "nomeSocial": "Jose",
    "perfis": ["CLIENTE", "FORNECEDOR"],
    "telefones": [{ "ddd": "12", "numero": "999990000" }],
    "endereco": {
        "estado": "SP",
        "cidade": "São José dos Campos",
        "bairro": "Centro",
        "rua": "Rua A",
        "numero": "100",
        "codigoPostal": "12200-000"
    },
    "documentos": [{ "tipo": "CPF", "dataEmissao": "2020-05-10", "numero": "11122233344" }],
    "emails": [{ "endereco": "jose@email.com" }]
}
```

Tipos de documento: `CPF`, `CNPJ`, `RG`, `CNH`, `PASSAPORTE`.

### Empresa (`POST /empresas`)

```json
{
    "razaoSocial": "Autobots Manutenção Veicular Ltda",
    "nomeFantasia": "Autobots Center",
    "telefones": [{ "ddd": "12", "numero": "32001000" }],
    "endereco": {
        "estado": "SP",
        "cidade": "São José dos Campos",
        "rua": "Av. Andromeda",
        "numero": "500"
    }
}
```

### Veículo (`POST /veiculos`)

```json
{
    "tipo": "SUV",
    "modelo": "Toyota Corolla Cross",
    "placa": "ABC1D23",
    "proprietarioId": 1
}
```

Tipos de veículo: `HATCH`, `SEDA`, `SUV`, `PICKUP`, `SW`. A placa aceita os formatos `ABC-1234` e `ABC1D23` e é guardada em maiúsculas e sem hífen.

### Mercadoria (`POST /mercadorias`)

```json
{
    "nome": "Filtro de óleo",
    "fabricacao": "2026-01-10",
    "validade": "2028-01-10",
    "quantidade": 50,
    "valor": 35.9,
    "descricao": "Filtro universal"
}
```

### Serviço (`POST /servicos`)

```json
{ "nome": "Troca de óleo", "valor": 150.0, "descricao": "Óleo + filtro" }
```

### Credenciais

```json
POST /credenciais/usuario-senha
{ "nomeUsuario": "joao.silva", "senha": "senha123" }

POST /credenciais/codigo-barra
{ "codigo": 7891000100103 }
```

### Venda (`POST /vendas`)

```json
{
    "identificacao": "VENDA-0001",
    "empresaId": 1,
    "clienteId": 1,
    "funcionarioId": 2,
    "veiculoId": 1,
    "mercadoriasIds": [1, 2],
    "servicosIds": [1]
}
```

A resposta traz cliente, funcionário, veículo, empresa, mercadorias e serviços resumidos (com links) e o campo `valorTotal`.

---

# Regras de negócio

## Usuários

- Todo usuário tem pelo menos um perfil: `CLIENTE`, `FORNECEDOR` ou `FUNCIONARIO` (uma pessoa pode ter vários).
- Não é possível cadastrar dois documentos com o mesmo número, nem repetir o número dentro da mesma requisição (409).
- No `PUT`, uma lista não enviada **não muda**; uma lista enviada **substitui** a anterior; `[]` esvazia. Documentos com número já existente no próprio usuário são mantidos e apenas atualizados.
- Credenciais, mercadorias, veículos e dados pessoais não são alterados por um `PUT` que não os mencione.

## Credenciais

- `nomeUsuario` e `codigo` são únicos (409).
- A **senha nunca é devolvida** nas respostas.
- Uma credencial pertence a um único usuário (409 ao ligar uma credencial que já tem dono).
- `criacao` é definida pelo servidor; `ultimoAcesso` ficará a cargo do futuro login.

## Veículos

- O proprietário precisa existir (404) e ter o perfil `CLIENTE` (400).
- A placa é única (409) e validada.

## Empresas

- `razaoSocial` é única (409).
- Cada usuário, mercadoria e serviço pertence a **uma** empresa (409 ao ligar algo que já tem empresa; 404 ao desligar algo que não é daquela empresa).

## Vendas

| Regra | Erro |
|---|---|
| `identificacao` única | 409 |
| Cliente existe e tem perfil `CLIENTE` | 404 / 400 |
| Funcionário existe e tem perfil `FUNCIONARIO` | 404 / 400 |
| O veículo existe e pertence ao cliente | 404 / 400 |
| A empresa existe | 404 |
| Funcionário, mercadorias e serviços pertencem à empresa da venda | 400 |
| Pelo menos uma mercadoria ou um serviço | 400 |
| Ids de mercadorias e serviços existem | 404 |

A mesma mercadoria, serviço ou veículo pode aparecer em várias vendas. `cadastro` é definido pelo servidor.

## Exclusões

| Ao excluir... | Resultado |
|---|---|
| Telefone, endereço, documento ou e-mail pertencente a um usuário ou empresa | é desvinculado do dono e removido |
| Credencial ou mercadoria ligada a um usuário | é desvinculada e removida |
| Usuário que é cliente ou funcionário de alguma venda | 409 |
| Usuário que possui veículos | 409 |
| Veículo, mercadoria ou serviço que está em alguma venda | 409 |
| Usuário | remove também seus telefones, endereço, documentos, e-mails e credenciais (mercadorias são apenas desvinculadas) |
| Venda | remove somente a venda |
| Empresa | remove, em cascata, suas vendas, os veículos dos seus usuários, seus usuários, mercadorias, serviços, telefones e endereço. É bloqueada (409) se algum usuário da empresa for cliente de venda de **outra** empresa |

---

# Tratamento de erros

O projeto possui exceções específicas para situações como:

- Entidade não encontrada (usuário, empresa, veículo, mercadoria, serviço, venda, credencial, documento, e-mail, endereço, telefone)
- Duplicidade (documento, placa, razão social, identificação de venda, credencial)
- Perfil inválido (proprietário de veículo, cliente ou funcionário de venda)
- Venda inválida (itens de outra empresa, veículo de outro dono, venda sem itens)
- Associação já existente ou inexistente
- Exclusão não permitida (item em uso)
- Dados inválidos (validação de campos obrigatórios, enums e datas)