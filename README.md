# AFgrupo9

Para que seja possível executar a aplicação você precisa ter node (versão 18.17 ou maior), npm, java (21 ou maior) e maven instalados em seu computador

Para clonar o repositorio e ter acesso ao código, navegue até a pasta desejada e digite "git clone https://github.com/enzodmrib/AFgrupo9.git"

Para instalar as dependências, primeiro vá até o diretório /api em um terminal e execute "npm run install:all". Caso este comando falhe, execute de forma isolada os comandos:
- npm run install:flights
- npm run install:hotels
- npm run install:events

Após isso, abra mais 3 terminais, eles serão responsáveis por rodar os serviços. Rode um comando em cada um deles:
- npm run start:flights
- npm run start:hotels
- npm run start:events

Em sequencia, execute o comando "npm run dev" no terminal inicial

Por fim, para executar o frontend, abra mais um terminal e navegue até o diretório /web e execute "npm install" e em sequencia, "npm run dev", e no seu navegador, navvegue até a rota "http://localhost:3000"
