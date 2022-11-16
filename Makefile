
# Makefile para la construcción del cliente y servidor que conectan con un servidor
# y cliente de java.
#
all : Servidor Cliente


# Coloca aqui el path donde te hayas descargado y compilado la mini-librería de sockets
PATH_CHSOCKET=//home/diani/Documents/TEC-LCI-IceClimber-Project/library

CFLAGS = -g -I. -I$(PATH_CHSOCKET)

server : server.c
	cc server.c -ljson-c -L$(PATH_CHSOCKET) -lChSocket -o server

client : SocketClient.java
	javac -cp /home/diani/Documents/JSON/json-20220924.jar SocketClient.java DataSocket.java
clean :
	rm *.o server server	