/*
* Programa servidor en C para conectarse con un cliente java.
*/
#include "library/Socket_Servidor.h"
#include "library/Socket.h"
#include <string.h>
#include <stdio.h>
#include <json-c/json.h>
#include "server.h"



/* Esta función se encarga de leer el request que se recibe del cliente 
   y convertirlo en un struct de tipo json_object*/
struct messageBox ReadParseJson(char *Data)
{
	
	struct json_object *json;
	struct json_object *players;
	struct json_object *player;
	struct json_object *event;
	struct json_object *idPlayer;
	struct json_object *xCoordenate;
	struct json_object *yCoordanate;
	struct json_object *blockNumber;
	struct json_object *floorNumber;	
	struct json_object *isFloorMoving;
	struct messageBox parseMessage;
	struct playerClient player1;
	struct playerClient player2;
	
	size_t nJugadores;
	size_t i;

	json = json_tokener_parse(Data);

	json_object_object_get_ex(json, "evento", &event);


	parseMessage.event = json_object_get_string(event);

	json_object_object_get_ex(json, "jugadores", &players);
	
	nJugadores = json_object_array_length(players);



	/* 
	Itera por los dos jugadores y crea el objeto parseado utilizando jsons
	*/
	for (i=0; i<nJugadores; i++)
	{	
		player = json_object_array_get_idx(players, i);

		json_object_object_get_ex(player, "id", &idPlayer);

		xCoordenate = json_object_object_get(player, "x");

		yCoordanate = json_object_object_get(player, "y");

		blockNumber = json_object_object_get(player, "blockNumber");

		floorNumber = json_object_object_get(player, "floorNumber");

		isFloorMoving = json_object_object_get(player, "isFloorMoving");

		player2.id = NULL;

		if( i == 0)
		{
			player1.id = json_object_get_string(idPlayer);
			player1.x = json_object_get_int(xCoordenate);
			player1.y = json_object_get_int(yCoordanate);
			player1.blockNumber = json_object_get_int(blockNumber);
			player1.floorNumber = json_object_get_int(floorNumber);
			player1.isFloorMoving = json_object_get_int(isFloorMoving);

			parseMessage.player1 = player1;
		}
		else if (i == 1)
		{
			player2.id = json_object_get_string(idPlayer);
			player2.x = json_object_get_int(xCoordenate);
			player2.y = json_object_get_int(yCoordanate);
			player2.blockNumber = json_object_get_int(blockNumber);
			player2.floorNumber = json_object_get_int(floorNumber);
			player2.isFloorMoving = json_object_get_int(isFloorMoving);

			parseMessage.player2 = player2;

		}
		
	}

	return parseMessage;
}

main ()
{
	/*
	* Descriptores de socket servidor y de socket con el cliente
	*/
    int Socket_Servidor;
	int Socket_Cliente;
	char Cadena[1024];
	pid_t childpid;
	/*
	* Se abre el socket servidor, con el servicio "cpp_java" dado de
	* alta en /etc/services. El número de dicho servicio debe ser 25557, que es
   	* el que se ha puesto en el código java del cliente.
	*/
	Socket_Servidor = Abre_Socket_Inet ("cpp_java");
	if (Socket_Servidor == -1)
	{
		printf ("No se puede abrir socket servidor\n");
		exit (-1);
	}

	printf ("Esperando conexion...\n");

	while(TRUE)
	{

		/*
		* Se espera un cliente que quiera conectarse
		*/
		Socket_Cliente = Acepta_Conexion_Cliente (Socket_Servidor);
		if (Socket_Servidor == -1)
		{
			printf ("No se puede abrir socket de cliente\n");
			exit (-1);
		}

		printf ("Conectado...\n");

		/* se abre un nuevo proceso utilizando Fork()*/
		if((childpid = fork()) == 0){
			close (Socket_Servidor);

			while(1){

				int aux;
				int lenght_request;
			

				/*
				* Se lee la informacion del cliente, primero el número de caracteres de la cadena
				* que vamos a recibir (incluido el \0) y luego la cadena.
				*/
				int read = Lee_Socket (Socket_Cliente, (char *)&aux, sizeof(lenght_request));

				/* El entero recibido hay que transformarlo de formato red a formato hardware */
				int longitudCadena = ntohl(aux);
				//printf ("Servidor C: Recibido %d\n", longitudCadena-1);

				/* Se lee la cadena */
				Lee_Socket (Socket_Cliente, Cadena, longitudCadena);


				struct messageBox mes = ReadParseJson(Cadena);

				char* serverResponse = receiveMessage(mes);

				char response[1024];
				strcpy(response, serverResponse);

				/*
				* Se envia un entero con la longitud de una cadena (incluido el \0 del final) 
				* la cadena.
				*/
				int lenght = strlen(response)+1;

				/* El entero que se envía por el socket hay que transformalo a formato red */
				int aux_response = htonl (lenght);

				/* Se envía el entero transformado */
				Escribe_Socket (Socket_Cliente, (char *)&aux_response, sizeof(lenght));
				

				/* Se envía la cadena */
				Escribe_Socket (Socket_Cliente, response, lenght);
			}
		}
	}

	/*
	* Se cierran los sockets
	*/
	close (Socket_Cliente);

}



