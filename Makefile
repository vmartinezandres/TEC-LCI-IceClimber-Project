
# Makefile para la construcción del cliente y servidor que conectan con un servidor
# y cliente de java.
#
all : Servidor Cliente

# Coloca aqui el path donde te hayas descargado y compilado la mini-librería de sockets
PATH_CHSOCKET=//home/diani/Documents/TEC-LCI-IceClimber-Project/library

CFLAGS = -g -I. -I$(PATH_CHSOCKET)

Servidor : server.c
	cc server.c -L$(PATH_CHSOCKET) -lChSocket -o Servidor

Cliente : client.c
	cc client.c -L$(PATH_CHSOCKET) -lChSocket -o Cliente

clean :
	rm *.o Cliente Servidor	