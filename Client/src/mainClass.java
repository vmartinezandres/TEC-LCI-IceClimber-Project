package Client.src;

import com.sun.tools.javac.Main;
import org.json.JSONObject;

import java.util.Dictionary;

public class mainClass implements Runnable {
    graphicUI loginInterface;
    public static void main(String[] args) {
        mainClass mainClass = new mainClass();
        mainClass.initilize();
        Thread thread1 = new Thread(mainClass);
        thread1.start();


    }

    public void initilize(){
       loginInterface = new graphicUI();
    }

    @Override
    public void run() {
        SocketClient client = new SocketClient();
        client.runClient();
        ResponseController responseController = new ResponseController();
        String message = "{\"evento\": \"update\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}";
        JSONObject jsonResponse = client.sendRequest(message);
        Dictionary<String, int[]> npcs = responseController.getNpcs(jsonResponse);
        System.out.println("I am going to update interface");
        loginInterface.updateNpcs(npcs);

        String message2 = "{\"evento\": \"quit\", \"jugadores\": [{ \"id\": 1, \"x\":1, \"y\":2}, {\"id\":2, \"x\":3, \"y\":4}]}";
        client.sendRequest(message2);

    }
}

