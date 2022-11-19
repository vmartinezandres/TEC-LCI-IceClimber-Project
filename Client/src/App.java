package Client.src;

import org.json.JSONObject;

import java.util.Dictionary;
import java.util.Enumeration;

/*Esto es una clase demo de como se ve el flujo de las cosas */
public class App {
    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        client.runClient();

        ResponseController responseController = new ResponseController();

        String message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        Dictionary<String, int[]> npcs = responseController.getNpcs(jsonResponse);
        int player1lifes = responseController.getPlayersLifes(jsonResponse, 1);
        int player2lifes = responseController.getPlayersLifes(jsonResponse, 2);
        int player1Points = responseController.getPlayersPoints(jsonResponse, 1);
        int players2Points = responseController.getPlayersPoints(jsonResponse, 2);

        System.out.println("Vidas jugador 1 => "+ player1lifes + " y vidas jugador 2 =>"+ player2lifes);
        System.out.println("Puntos jugador 1 =>"+ player1Points + " y puntos jugador 2 =>"+ players2Points);
        // keys() method :
        for (Enumeration k = npcs.keys(); k.hasMoreElements();)
        {
            Object key = k.nextElement();
            String id = key.toString();
            System.out.println("Key in  : " + id + " => has value of x : " + npcs.get(key)[0] + " has a value of y: " + npcs.get(key)[1]);
        }

    }

}


