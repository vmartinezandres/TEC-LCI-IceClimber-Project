/*
* Programa servidor en C para conectarse con un cliente java.
*/
#include "library/Socket_Servidor.h"
#include "library/Socket.h"
#include <string.h>
#include <stdio.h>
#include <json-c/json.h>

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
	
   /*
    * Se envia un entero con la longitud de una cadena (incluido el \0 del final) y
    * la cadena.
    */
   Longitud_Cadena = 5;
   strcpy (Cadena, "Hola");

   /* El entero que se envía por el socket hay que transformalo a formato red */
   Aux = htonl (Longitud_Cadena);

   /* Se envía el entero transformado */
   Escribe_Socket (Socket_Cliente, (char *)&Aux, sizeof(Longitud_Cadena));
   printf ("Servidor C: Enviado %d\n", Longitud_Cadena-1);
  
   /* Se envía la cadena */
   Escribe_Socket (Socket_Cliente, Cadena, Longitud_Cadena);
   printf ("Servidor C: Enviado %s\n", Cadena);
   
   
	/*
	* Se lee la informacion del cliente, primero el número de caracteres de la cadena
   * que vamos a recibir (incluido el \0) y luego la cadena.
	*/
   Lee_Socket (Socket_Cliente, (char *)&Aux, sizeof(Longitud_Cadena));

   /* El entero recibido hay que transformarlo de formato red a formato hardware */
   int longitud_Cadena = ntohl(Aux);
   printf ("Servidor C: Recibido %d\n", longitud_Cadena-1);


  // "{'evento': 'update', jugadores: [{id: 1, x:1, y:2}, {id:2, x:3, y:4}]}"
   /* Se lee la cadena */
	Lee_Socket (Socket_Cliente, Cadena, longitud_Cadena);
	printf ("Servidor C: Recibido %s\n", Cadena);

	struct json_object *json;
	struct json_object *jugadores;
	struct json_object *jugador;

	size_t n_jugadores;
	size_t i;

	json = json_tokener_parse(Cadena);

	printf ("Servidor C: Recibido %s\n", Cadena);

	json_object_object_get_ex(json, "'jugadores'", &jugadores);
	

	n_jugadores = json_object_array_length(jugadores);

	printf("Found %lu friends \n", n_jugadores);

	for (i=0; i<n_jugadores; i++)
	{
		jugador = json_object_array_get_idx(jugadores, i);
		i++;
		printf(json_object_object_get(jugador, "id"));
		printf(json_object_object_get(jugador, "x"));
		printf(json_object_object_get(jugador, "y"));
	}


	/*
	* Se cierran los sockets
	*/
	close (Socket_Cliente);
	close (Socket_Servidor);
}
