Para rodar vá ate a pasta do projeto e digite:

No linux
$ ./gradlew build
$ /bin/sh build/install/projeto/bin/projeto <userid> <qntRecom>

No windows

> .\gradlew.bat build
> .\build\install\projeto\bin\projeto.bat <recMethod> <userid> <qntRecom>


recMethod:
    1- item-item
    2- user-user
    3- Popularity rank

Onde userid é o id do usuario o qual deseja recomendar item e qntRecom é o numero de recomendações que deseja se dar para o usuario. 

Uma lista de pares <userid> <qntRecom> pode ser informado para várias recomendações.
