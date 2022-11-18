package Client.src;

import org.json.JSONObject;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Objects;

/*Esto es una clase demo de como se ve el flujo de las cosas */
public class App extends ResponseController{
    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        client.runClient();

        App app = new App();

        String message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        Dictionary<String, int[]> npcs = app.getNpcs(jsonResponse);
        Dictionary<String, int[]> players = app.getPlayers(jsonResponse);

        // keys() method :
        for (Enumeration k = npcs.keys(); k.hasMoreElements();)
        {
            Object key = k.nextElement();
            System.out.println("Key in  : " + key + " => has value of x : " + npcs.get(key)[0] + " has a value of y: " + npcs.get(key)[1]);
        }

        String message1 = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}]}";
        JSONObject jsonResponse1= client.sendRequest(message1);
        Dictionary<String, int[]> npcs1 = app.getNpcs(jsonResponse1);
        Dictionary<String, int[]> players1 = app.getPlayers(jsonResponse1);
        client.closeSocket();
    }

    @Override
    public Dictionary<String, int[]> getNpcs(JSONObject json) {
        return super.getNpcs(json);
    }

    @Override
    public Dictionary<String, int[]> getPlayers(JSONObject json) {
        return super.getPlayers(json);
    }
}


