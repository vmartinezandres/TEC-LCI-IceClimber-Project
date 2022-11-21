package Client.src;

import org.json.JSONObject;

import java.util.Dictionary;

public class Client extends ResponseController implements Runnable {

    graphicUI clientInterface;

    SocketClient client;

    /*
    * Constructor de clase cliente
    * */
    public Client(graphicUI clientInterface)
    {
        this.clientInterface = clientInterface;
        client = new SocketClient();
    }

    /*Empezar a correr cliente, y se inicia el hilo*/
    public void start()
    {
        client.runClient();
        Thread thread = new Thread(this);
        thread.run();

    }

    /*
    * Responsable de cerrar el cliente una vez que se termine el juego
    * */
    public void close() throws InterruptedException {
        client.closeSocket();
    }

    /*Manda el evento de martillo al server */
    public void sendSledgehammer(infoPackage i) throws InterruptedException {
        String message = "{\"evento\": \""+i.event+"\", \"jugadores\": [{ \"id\": \""+i.playerId+"\", \"x\":"+i.playerCoordx+", \"y\":"+i.playerCoordy+",\"blockNumber\": "+i.blockNumber+",  \"floorNumber\": "+i.floorNumber+", \"isFloorMoving\": "+i.isFloorMoving+"}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        update(jsonResponse);
    }
    /*Manda el evento de romper bloque al server */
    public void sendDestroyBlock(infoPackage i) throws InterruptedException {
        String event = "destroyBlock";
        String message = "{\"evento\": \""+i.event+"\", \"jugadores\": [{ \"id\": \""+i.playerId+"\", \"x\":"+i.playerCoordx+", \"y\":"+i.playerCoordy+",\"blockNumber\": "+i.blockNumber+",  \"floorNumber\": "+i.floorNumber+", \"isFloorMoving\": "+i.isFloorMoving+"}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        update(jsonResponse);
    }
    /*Manda el evento de cambio de pisos al server */
    public void sendChangeFloors(infoPackage i) throws InterruptedException {
        String event = "changeFloors";
        String playerId = "P1";
        int playerCoordx = this.clientInterface.playerCoordx1;
        int playerCoordy = this.clientInterface.playerCoordy1;
        int blockNumber = 20;
        int floorNumber = 2;
        int isFloorMoving = 1;
        String message = "{\"evento\": \""+event+"\", \"jugadores\": [{ \"id\": \""+i.playerId+"\", \"x\":"+i.playerCoordx+", \"y\":"+i.playerCoordy+",\"blockNumber\": "+i.blockNumber+",  \"floorNumber\": "+i.floorNumber+", \"isFloorMoving\": "+i.isFloorMoving+"}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        update(jsonResponse);
    }

    @Override
    public synchronized void run() {
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
            this.clientInterface.updatePlayerCoords();
            if(clientInterface.totalPlayer == 2){
                message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": \"P1\", \"x\":"+this.clientInterface.playerCoordx1+ ", \"y\":"+this.clientInterface.playerCoordy1+", \"blockNumber\": "+blockNumber+",  \"floorNumber\": "+floorNumber+", \"isFloorMoving\": "+isFloorMoving+"},{\"id\":\"P2\", \"x\":"+this.clientInterface.playerCoordx2+", \"y\":"+this.clientInterface.playerCoordy2+" ,\"blockNumber\": "+blockNumber+",  \"floorNumber\": "+floorNumber+", \"isFloorMoving\": "+isFloorMoving+"}]}";
            }else{
                message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": \"P1\", \"x\":"+this.clientInterface.playerCoordx1+", \"y\":"+this.clientInterface.playerCoordy1+",\"blockNumber\": "+blockNumber+",  \"floorNumber\": "+floorNumber+", \"isFloorMoving\": "+isFloorMoving+"},]}";
            }

            JSONObject jsonResponse = client.sendRequest(message);
            try {
                update(jsonResponse);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            n++;
        }
    }

    public void update(JSONObject jsonResponse) throws InterruptedException {
        Dictionary<String, int[]> npcs = getNpcs(jsonResponse);
        int pointP1 = getPlayersPoints(jsonResponse, 1);
        int pointP2 = getPlayersPoints(jsonResponse, 2);
        int lifeP1 = getPlayersLifes(jsonResponse, 1);
        int lifeP2 = getPlayersLifes(jsonResponse, 2);
        int levelP1 = getPlayersLevel(jsonResponse, 1);
        int levelP2 = getPlayersLevel(jsonResponse, 2);
        clientInterface.updateNpcs(npcs);
        clientInterface.updatePointsAndLifes(pointP1, pointP2, lifeP1, lifeP2, levelP1, levelP2);
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

    @Override
    public int getPlayersLevel(JSONObject json, int jugador) {
        return super.getPlayersLevel(json, jugador);
    }
}
