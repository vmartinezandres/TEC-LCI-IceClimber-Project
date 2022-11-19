#ifndef _SERVER_H
#define _SERVER_H


/* Informacion sobre jugadors */
struct playerClient
{
	char* id;
	int x;
	int y;
    int blockNumber;
    int floorNumber;
    int isFloorMoving;
};

/* Informacion que se recibe del cliente */
struct messageBox
{
	char *event;
	struct playerClient player1;
	struct playerClient player2;
	
};

char* receiveMessage(struct messageBox mb);

#endif