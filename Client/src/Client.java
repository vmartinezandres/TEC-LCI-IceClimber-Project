package Client.src;

import org.json.JSONObject;

import java.util.Dictionary;

public class Client extends ResponseController implements Runnable {

    graphicUI clientInterface;

    SocketClient client;

    public Client(graphicUI clientInterface)
    {
        this.clientInterface = clientInterface;
        client = new SocketClient();
    }

    public void start()
    {
        client.runClient();
        Thread thread = new Thread(this);
        thread.run();
        String message = "{\"evento\": \"masazo\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        update(jsonResponse);
    }
    public void sendMessage(String evento)
    {
        String message = "{\"evento\": \""+evento+"\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        update(jsonResponse);
    }

    @Override
    public void run() {
        int n = 0;
        while (n <= 3)
        {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String message;
            if(clientInterface.totalPlayer == 2){
                message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": \"P1\", \"x\":"+this.clientInterface.playerCoordx1+ ", \"y\":"+this.clientInterface.playerCoordy1+"},{\"id\":\"P2\", \"x\":"+this.clientInterface.playerCoordx2+", \"y\":"+this.clientInterface.playerCoordy2+"}]}";
            }else{
                message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": \"P1\", \"x\":"+this.clientInterface.playerCoordx1+", \"y\":"+this.clientInterface.playerCoordy1+"}]}";
            }
            JSONObject jsonResponse = client.sendRequest(message);
            update(jsonResponse);
            n++;
        }

    }

    public void update(JSONObject jsonResponse)
    {
        Dictionary<String, int[]> npcs = getNpcs(jsonResponse);
        int pointP1 = getPlayersPoints(jsonResponse, 1);
        int pointP2 = getPlayersPoints(jsonResponse, 2);
        int lifeP1 = getPlayersLifes(jsonResponse, 1);
        int lifeP2 = getPlayersLifes(jsonResponse, 2);
        clientInterface.updateNpcs(npcs);
        clientInterface.updatePointsAndLifes(pointP1, pointP2, lifeP1, lifeP2);
    }

    @Override
    public int getPlayersLifes(JSONObject json, int jugador) {
        return super.getPlayersLifes(json, jugador);
    }

    @Override
    public int getPlayersPoints(JSONObject json, int jugador) {
        return super.getPlayersPoints(json, jugador);
    }

    @Override
    public Dictionary<String, int[]> getNpcs(JSONObject json) {
        return super.getNpcs(json);
    }
}
