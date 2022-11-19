package Client.src;

import org.json.JSONObject;

import java.util.Dictionary;

public class ClientObserver extends ResponseController{

    graphicUI observedInterface;
    graphicUI clientInterface;

    SocketClient client;

    public ClientObserver(graphicUI ObservedInterface)
    {
        this.observedInterface = ObservedInterface;
        client = new SocketClient();
        clientInterface = new graphicUI("Observer", observedInterface.totalPlayer);
    }

    public void start()
    {
        client.runClient();
        update();
    }

    public void update() {
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
            int isFloorMoving = 0;
            this.observedInterface.updatePlayerCoords();
            if(observedInterface.totalPlayer == 2){
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
        Dictionary<String, int[]> npcs = this.observedInterface.npcsServer;
        String pointP1 = this.observedInterface.pointsP1.getText();
        String pointP2 = this.observedInterface.pointsP2.getText();
        String lifeP1 = this.observedInterface.lifeP1.getText();
        String lifeP2 = this.observedInterface.lifeP2.getText();
        clientInterface.updateNpcs(npcs);
        clientInterface.updatePointsAndLifesInObserver(pointP1, pointP2, lifeP1, lifeP2);
        int x1 = this.observedInterface.playerCoordx1;
        int x2 = this.observedInterface.playerCoordx2;
        int y1 = this.observedInterface.playerCoordy1;
        int y2 = this.observedInterface.playerCoordy2;
        clientInterface.updatePlayer(x1, x2, y1, y2);
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
