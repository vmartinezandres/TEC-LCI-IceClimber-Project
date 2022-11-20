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
    }
    public void sendSledgehammer(infoPackage i)
    {
        String message = "{\"evento\": \""+i.event+"\", \"jugadores\": [{ \"id\": \""+i.playerId+"\", \"x\":"+i.playerCoordx+", \"y\":"+i.playerCoordy+",\"blockNumber\": "+i.blockNumber+",  \"floorNumber\": "+i.floorNumber+", \"isFloorMoving\": "+i.isFloorMoving+"}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        update(jsonResponse);
    }
    public void sendDestroyBlock()
    {
        String event = "destroyBlock";
        String playerId = "P1";
        int playerCoordx = this.clientInterface.playerCoordx1;
        int playerCoordy = this.clientInterface.playerCoordy1;
        int blockNumber = 20;
        int floorNumber = 2;
        int isFloorMoving = 0;
        String message = "{\"evento\": \""+event+"\", \"jugadores\": [{ \"id\": "+playerId+", \"x\":"+playerCoordx+", \"y\":"+playerCoordy+",\"blockNumber\": "+blockNumber+",  \"floorNumber\": "+floorNumber+", \"isFloorMoving\": "+isFloorMoving+"}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        update(jsonResponse);
    }
    public void sendChangeFloors()
    {
        String event = "changeFloors";
        String playerId = "P1";
        int playerCoordx = this.clientInterface.playerCoordx1;
        int playerCoordy = this.clientInterface.playerCoordy1;
        int blockNumber = 20;
        int floorNumber = 2;
        int isFloorMoving = 1;
        String message = "{\"evento\": \""+event+"\", \"jugadores\": [{ \"id\": "+playerId+", \"x\":"+playerCoordx+", \"y\":"+playerCoordy+",\"blockNumber\": "+blockNumber+",  \"floorNumber\": "+floorNumber+", \"isFloorMoving\": "+isFloorMoving+"}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        update(jsonResponse);
    }

    @Override
    public void run() {
        int n = 0;
        while (n <= 100)
        {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String message;
            int blockNumber = 0;
            int floorNumber = 0;
            int isFloorMoving = this.clientInterface.isFloorMoving;
            this.clientInterface.updatePlayerCoords();
            if(clientInterface.totalPlayer == 2){
                message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": \"P1\", \"x\":"+this.clientInterface.playerCoordx1+ ", \"y\":"+this.clientInterface.playerCoordy1+", \"blockNumber\": "+blockNumber+",  \"floorNumber\": "+floorNumber+", \"isFloorMoving\": "+isFloorMoving+"},{\"id\":\"P2\", \"x\":"+this.clientInterface.playerCoordx2+", \"y\":"+this.clientInterface.playerCoordy2+" ,\"blockNumber\": "+blockNumber+",  \"floorNumber\": "+floorNumber+", \"isFloorMoving\": "+isFloorMoving+"}]}";
            }else{
                message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": \"P1\", \"x\":"+this.clientInterface.playerCoordx1+", \"y\":"+this.clientInterface.playerCoordy1+",\"blockNumber\": "+blockNumber+",  \"floorNumber\": "+floorNumber+", \"isFloorMoving\": "+isFloorMoving+"},]}";
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
