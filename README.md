# Paradigma publish/subscribe

Esse programa é um trabalho feito por Bruno Sumar e Bruno Macedo para a disciplina de sistemas distribuídos. 
Ele é uma implementação do paradigma publish/subscribe utilizando Java RMI e é dividido em duas partes: Notificador(servidor) e ouvinte(cliente).
    
## Compilação e Execução

### Com makefile
Para compilar o programa com o makefile basta usar o comando:
>make

E para executar o notificador e o ouvinte respectivamente use:

>make runServer

>make runClient

### Sem makefile
Para compilar o programa com o makefile basta usar o comando:

>javac -p ./class/ Ouvinte.java IOuvinte.java INotificador.java Notificador.java Topico.java

E para executar o notificador e o ouvinte respectivamente use:

>java -cp ./class -Djava.rmi.server.codebase=file:./class/ -Djava.security.policy=policy pubSub.Notificador
>java -cp ./class -Djava.rmi.server.codebase=file:./class/ -Djava.security.policy=policy pubSub.Ouvinte



#### inicialiar rmi
> rmiresgistry &
