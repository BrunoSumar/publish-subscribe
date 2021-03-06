##
# paradigma publish/subscribe
#
##

SRC_C = Ouvinte.java IOuvinte.java
SRC_S = INotificador.java Notificador.java Topico.java

comp: $(SRC_C) $(SRC_S)
	javac -d ./class/ $^

client: $(SRC_C)
	javac -d ./class/ $^

server: $(SRC_S)
	javac -d ./class/ $^

runServer:
	java -cp ./class -Djava.rmi.server.codebase=file:./class/ -Djava.security.policy=policy pubSub.Notificador

runClient:
	java -cp ./class -Djava.rmi.server.codebase=file:./class/ -Djava.security.policy=policy pubSub.Ouvinte

.PHONY: comp client server runServer


# end
