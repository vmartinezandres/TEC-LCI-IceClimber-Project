import java.net.*;
import java.io.*;
//import org.json.*;

/**
 * Clase que crea un socket cliente, establece la conexión y lee los datos
 * del servidor, escribiéndolos en pantalla.
 */
public class SocketClient
 {

    public String message;
    public String serverResponse;

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
    /**
     * Crea el socket cliente y lee los datos
    */
    public SocketClient(int value)
    {
        
        try
        {
            /* Se crea el socket cliente */
            this.socket = new Socket ("localhost", 25557);
            System.out.println ("conectado");

            /* Se hace que el cierre espere a la recogida de todos los datos desde
            * el otro lado */
            socket.setSoLinger (true, 10);
            
            /* Se obtiene un stream de lectura para leer objetos */
            DataInputStream bufferEntrada =
            new DataInputStream (socket.getInputStream());
            
            /* Se lee un Datosocket que nos envía el servidor y se muestra 
            * en pantalla */
            DataSocket dato = new DataSocket("");
            dato.readObject(bufferEntrada);
            String response = dato.toString();
            System.out.println ("Cliente Java: Recibido " + response);

            /*

            PROBLEMA PARA LA DIANA DEL FUTURO

            JSONObject json = new JSONObject(response);

            System.out.println(json.getString("evento"));

            JSONArray jugadores = json.getJSONArray("jugadores");

            int n_jugadores = jugadores.length();

            System.out.println("número de jugadores "+ n_jugadores);

            for (int i = 0; i<n_jugadores; i++)
            {
                JSONObject jugador = jugadores.getJSONObject(i);

                System.out.println(jugador.getString("id"));
                System.out.println(jugador.getInt("x"));
                System.out.println(jugador.getInt("y"));
            }
             */
            

           
            /* Se obtiene un flujo de envio de datos para enviar un dato al servidor */
            DataOutputStream bufferSalida =
            new DataOutputStream (socket.getOutputStream());

            /* Se crea el dato y se escribe en el flujo de salida */
            DataSocket aux = new DataSocket ("{\"evento\": \"update\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}");
            aux.writeObject(bufferSalida);


            System.out.println ("Cliente Java: Enviado " + aux.toString());
        
            /* La llamada a setSoLinger() hará que el cierre espere a que el otro
            lado retire los datos que hemos enviado */
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    /*
     * Se encarga de inicializar el puesto y establecer la conexion con el server
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
     * Este metodo cierra el objeto de socket inicializado
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
     * Este metodo sera utilizado para mandar el request de mensaje que el server necesita enviar 
     */
    public String sendRequest(String message)
    {
        try
        {
            if(socket.isConnected())
            {
                //this.message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}";
                
                /* Se obtiene un flujo de envio de datos para enviar un dato al servidor */
                DataOutputStream request = new DataOutputStream (socket.getOutputStream());
        
                /* Se crea el dato y se escribe en el flujo de salida */
                DataSocket aux = new DataSocket (message);
                
                aux.writeObject(request);
        
                System.out.println ("Cliente Java: Enviado " + aux.toString());
        
                /* Se obtiene un stream de lectura para leer objetos */
                DataInputStream response = new DataInputStream (socket.getInputStream());
        
                /* Se lee un Datosocket que nos envía el servidor y se muestra 
                * en pantalla */
                DataSocket dato = new DataSocket("");
        
                dato.readObject(response);
        
                int length = dato.c;
                String responsetoString = dato.d;
        
                System.out.println ("Cliente Java: Recibido " +length +" --- "+ responsetoString);
            
                return responsetoString;
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
}
