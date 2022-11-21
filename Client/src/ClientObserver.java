package Client.src;

import org.json.JSONObject;

import java.util.Dictionary;

public class ClientObserver extends ResponseController{

    graphicUI observedInterface;
    graphicUI clientInterface;

    SocketClient client;

    /**
     * El cliente observador es un espejo del cliente jugador
     * @param ObservedInterface
     */
    public ClientObserver(graphicUI ObservedInterface)
    {
        this.observedInterface = ObservedInterface;
        client = new SocketClient();
        clientInterface = new graphicUI("Observer", observedInterface.totalPlayer);
    }

    /**
     * Inicio del cliente
     */
    public void start()
    {
        client.runClient();
        update();
    }

    /**
     * Solo envia update a cliente
     */
    public void update() {
        int n = 0;
        while (true)
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

    /**
     * Lectura del mensaje de update
     * @param jsonResponse
     */
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
        int x2 = 0;
        int y1 = this.observedInterface.playerCoordy1;
        int y2 = 0;

        if(this.observedInterface.totalPlayer == 2)
        {
            x2 = this.observedInterface.playerCoordx2;
            y2 = this.observedInterface.playerCoordy2;
        }

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
