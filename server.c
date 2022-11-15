/*
* Programa servidor en C para conectarse con un cliente java.
*/
#include "library/Socket_Servidor.h"
#include "library/Socket.h"
#include <string.h>
#include <stdio.h>
#include <json-c/json.h>

struct player
{
	char* id;
	int x;
	int y;
};

struct messagePackage
{
	char *event;
	struct player *player1;
	struct player *player2;
	
};

main ()
{
	/*
	* Descriptores de socket servidor y de socket con el cliente
	*/
    int Socket_Servidor;
	int Socket_Cliente;
    int Aux;
    int Longitud_Cadena;
	char Cadena[1024];

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
	 
	int x = 0;

	while(TRUE)
	{

		/*
		* Se lee la informacion del cliente, primero el número de caracteres de la cadena
		* que vamos a recibir (incluido el \0) y luego la cadena.
		*/
		int read = Lee_Socket (Socket_Cliente, (char *)&Aux, sizeof(Longitud_Cadena));

		/* El entero recibido hay que transformarlo de formato red a formato hardware */
		int longitud_Cadena = ntohl(Aux);
		printf ("Servidor C: Recibido %d\n", longitud_Cadena-1);

		/* Se lee la cadena */
		Lee_Socket (Socket_Cliente, Cadena, longitud_Cadena);
		printf ("Servidor C: Recibido %s\n", Cadena);

		// PARSEA MENSAJE DE CLIENTE 

		// LOGICA PARA DAR RESPUESTA 

		/*
		* Se envia un entero con la longitud de una cadena (incluido el \0 del final) y
		* la cadena.
		*/
		//strcpy (Cadena, "mensaje de respuesta");
		Longitud_Cadena = strlen(Cadena)+1;

		/* El entero que se envía por el socket hay que transformalo a formato red */
		int aux = htonl (Longitud_Cadena);

		/* Se envía el entero transformado */
		Escribe_Socket (Socket_Cliente, (char *)&aux, sizeof(Longitud_Cadena));
		printf ("Servidor C: Enviado %d\n", Longitud_Cadena-1);
		
		/* Se envía la cadena */
		Escribe_Socket (Socket_Cliente, Cadena, Longitud_Cadena);
		printf ("Servidor C: Enviado %s\n", Cadena);

	}

	/*
	* Se cierran los sockets
	*/
	close (Socket_Cliente);
	close (Socket_Servidor);
}

struct messagePackage ReadParseJson(char *Data)
{
	
	struct json_object *json;
	struct json_object *players;
	struct json_object *player;
	struct json_object *event;
	struct json_object *idPlayer;
	struct json_object *xCoordenate;
	struct json_object *yCoordanate;
	struct messagePackage parseMessage;
	
	
	size_t n_jugadores;
	size_t i;

	json = json_tokener_parse(Data);

	json_object_object_get_ex(json, "evento", &event);

	printf ("Servidor C: Recibido %s\n", json_object_get_string(event));

	parseMessage.event = json_object_get_string(event);

	json_object_object_get_ex(json, "jugadores", &players);
	
	n_jugadores = json_object_array_length(players);

	printf("Found %lu friends \n", n_jugadores);


	/* 
	Itera por los dos jugadores y crea el objeto parseado utilizando jsons
	*/
	for (i=0; i<n_jugadores; i++)
	{	
		player = json_object_array_get_idx(players, i);

		json_object_object_get_ex(player, "id", &idPlayer);
		printf ("Id de jugador: %s\n", json_object_get_string(idPlayer));

		xCoordenate = json_object_object_get(player, "x");
		printf ("Coordenada en x: %d\n", json_object_get_int(xCoordenate));

		yCoordanate = json_object_object_get(player, "y");
		printf ("Coordenada en y: %d\n", json_object_get_int(yCoordanate));

		if( i == 0)
		{
			struct player *player1;

			player1->id = json_object_get_string(idPlayer);
			player1->x = json_object_get_int(xCoordenate);
			player1->y = json_object_get_int(yCoordanate);

			parseMessage.player1 = player1;
		}
		else 
		{
			struct player *player2;

			player2->id = json_object_get_string(idPlayer);
			player2->x = json_object_get_int(xCoordenate);
			player2->y = json_object_get_int(yCoordanate);

			parseMessage.player2 = player2;
		}

	}

	return parseMessage;
}

