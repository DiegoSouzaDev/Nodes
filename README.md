[![codecov](https://codecov.io/gh/DiegoSouzaDev/Nodes/branch/master/graph/badge.svg)](https://codecov.io/gh/DiegoSouzaDev/Nodes)

# Nodes

Desafio implementado com Java 8 e SpringBoot, JUnit para testes e Maven para gerenciar a build.
Neste projeto, a comunicação é feita via REST, a persistência dos dados é realizada em banco de dados MySql utilizando Hibernate.


## O Desafio

Escrever o BackEnd de uma aplicação para representar e manipular uma estrutura de árvore.

A aplicação deverá conter métodos REST para exibir a estrutura, adicionar, editar e excluir os nós da árvore. 

## COMPORTAMENTOS ESPERADOS 

### **Método para cadastrar um novo nó:**

`POST` - `/node`
 **Request**
 * code: string 
 * description: string
 * parentId: number
 * detail: string
 
 **Response**
 * id: number
 

### **Método para atualizar nó**

`PUT` - `/node`
**Request**
* id: number
* code: string
* description: string
* parentId: number
* detail: string

**Response**
* id: number

 
### **Método para buscar toda a estrutura:**

`GET` - `/node`
 **Response**
 * id: number
 * code: string
 * description: string
 * parentId: number
 * detail: string
 * children: array 
 ** id: number
 ** code: string
 ** description: string
 ** parentId: number
 ** detail: string
 ** children: array
  
*O atributo children é um array que contém todos os nós filhos daquele nó, ou seja, irá conter toda a hierarquia até o ultimo nível;*



### **Método para buscar todos os nós abaixo de um nó específico**

`GET` - `/node/{parentId}`
**Response**
+array+
** id: number
** code: string
** description: string
** parentId: number
** detail: string 
** hasChildren: boolean 

______
## Teste e Execução da aplicação.

### EXECUTANDO OS TESTES
Para que os testes sejam executados, basta acessar a pasta raiz do projeto, abrir o CMD e executar o comando `mvn package`.
Feito isso, o maven baixará as dependencias e executará os testes.

### PUBLICAÇÃO E INICIALIZAÇÃO DA APLICAÇÃO:

Para  inicia a aplicação, é necessário realizar algumas preparações de ambiente.
1) Ter uma instância do MySql configurada.
2) Criar uma base de dados, usuário e senha no Mysql, e logo após, configurar a aplicação para utilizar esses dados (configuravel no arquivo application.properties).
Os dados previamente configurados na aplicação são:
`Database URL`
*  spring.datasource.url = jdbc:mysql://localhost:3306/saas?useSSL=false

`Username and password`
*  spring.datasource.username = root
*  spring.datasource.password = root

3) Ter uma instalação do `Tomcat 9` disponivel.
4) Ter o `Apache Maven 3.3.3` devidamente configurado.
5) Abrir o cmd no diretório raiz do projeto maven (no mesmo diretório onde se encontra o pom.xml) e digitar o comando `mvn package`.
6) Verificar que ao final da execução do comando citado, será gerado um arquivo `SaaS-1.0.war`.
7) Abrir o `Manager`do Tomcat e fazer o deploy do arquivo citado.

Após isso o sistema passará a responder na URL http://{host}:{port}/SaaS-1.0


## API

**ENDPOINT**

Considerando que o Tomcat está configurado na máquina local utilizando a porta `8080`, as chamadas deverão acontecer conforme abaixo:
```
PS: Substituir o **?** pelo id (numerico a ser consultado/removido)
```
```
POST http://localhost:8080/SaaS-1.0/saas/node
```
```
PUT http://localhost:8080/SaaS-1.0/saas/node
```
```
GET http://localhost:8080/SaaS-1.0/saas/node
```
```
GET http://localhost:8080/SaaS-1.0/saas/node/?
```
```
DELETE http://localhost:8080/SaaS-1.0/saas/node/?
```

Com a exceção das operações que enviam somente um parametro numerico via URL, as chamadas devem ser feitas passando os parametros via JSON, utilizando Content-type = application/json e o formato das informações conforme especificação

Exemplo de JSON para criar um nó (POST):
```
{
  "code": "c01",
  "description": "primeiro nó",
  "parentId": 0,
  "detail": "primeiro nó cadastrado"
}
```

Exemplo de JSON para atualizar um nó (PUT):
```
{
  "id": 2,
  "code": "d25",
  "description": "filho do primeiro nó",
  "parentId": 1,
  "detail": "é filho do primeiro nó cadastrado"
}
```

