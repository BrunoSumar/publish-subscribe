##
# paradigma publish/subscribe
#
##

SRC_C = Ouvinte.java IOuvinte.java
SRC_S = INotificador.java Notificador.java Topico.java

comp : $(SRC_C) $(SRC_S)
	javac -d ./class/ $^

client : $(SRC_C)
	javac -d ./class/ $^

server : $(SRC_S)
	javac -d ./class/ $^


# end
