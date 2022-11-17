package Client.src;

import java.net.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Clase que crea un socket cliente, establece la conexión y lee los datos
 * del servidor, escribiéndolos en pantalla.
 */
public class SocketClient
 {

    private Socket socket;

    /** Programa principal, crea el socket cliente */
    public static void main (String [] args)
    {
        SocketClient client = new SocketClient();
        client.runClient();

        String message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}";
        client.sendRequest(message);
        
        String message1 = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}]}";
        client.sendRequest(message1);

        client.closeSocket();
    }
    
    /*
     * Crea la instancia del socket 
     */
    public SocketClient()
    {

    }

    /*
     * Se encarga de inicializar el puesto y establecer la conexión con el server
     */
    private void runClient()
    {
        try
        {
            /* Se crea el socket cliente */
            this.socket = new Socket ("localhost", 25557);
            System.out.println ("conectado");

            /* Se hace que el cierre espere a la recogida de todos los datos desde
            * el otro lado */
            socket.setSoLinger (true, 10);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /*
     * Este método cierra el objeto de socket inicializado
     */
    public void closeSocket()
    {
        try
        {
            this.socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * Este método será utilizado para mandar el request de mensaje que el server necesita enviar
     */
    public String sendRequest(String message)
    {
        try
        {
            if(socket.isConnected())
            {
                
                /* Se obtiene un flujo de envío de datos para enviar un dato al servidor */
                DataOutputStream request = new DataOutputStream (socket.getOutputStream());
        
                /* Se crea el dato y se escribe en el flujo de salida */
                DataSocket aux = new DataSocket (message);
                
                aux.writeObject(request);
        
                System.out.println ("Cliente Java: Enviado " + aux.toString());
        
                /* Se obtiene un stream de lectura para leer objetos */
                DataInputStream response = new DataInputStream (socket.getInputStream());
        
                /* Se lee un Dato socket que nos envía el servidor y se muestra
                * en pantalla */
                DataSocket dato = new DataSocket("");
                dato.readObject(response);

                /* Toma el string de la clase DataSocket */
                String responseToString = dato.d;

                return responseToString;
            }
            else
            {
                return "";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public JSONObject parseResponse(String message)
    {
        JSONObject json = new JSONObject(message);

        System.out.println(json.getString("evento"));

        JSONArray players = json.getJSONArray("jugadores");

        int nJugadores = players.length();

        System.out.println("número de jugadores "+ nJugadores);

        for (int i = 0; i<nJugadores; i++)
        {
            JSONObject player = players.getJSONObject(i);

            System.out.println("Id de jugador: " + player.getInt("id"));
            System.out.println("coordenada en x: " + player.getInt("x"));
            System.out.println("coordenada en y: " +player.getInt("y"));
        }

        JSONArray npcs = json.getJSONArray("npcs");

        int nNpcs = npcs.length();

        System.out.println("número de jugadores "+ nNpcs);

        for (int i = 0; i<nNpcs; i++)
        {
            JSONObject npc = npcs.getJSONObject(i);

            System.out.println("Id de npc: " + npc.getString("id"));
            System.out.println("coordenada en x: " + npc.getInt("x"));
            System.out.println("coordenada en y: " +npc.getInt("y"));
        }

        return json;
    }
}
