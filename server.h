#ifndef _SERVER_H
#define _SERVER_H


/* Informacion sobre jugadors */
struct playerClient
{
	char* id;
	int x;
	int y;
};

/* Informacion que se recibe del cliente */
typedef struct messageBox
{
	char *event;
	struct playerClient player1;
	struct playerClient player2;
	
};



#endif