package Client.src;

import com.sun.tools.javac.Main;
import org.json.JSONObject;

import java.util.Dictionary;

public class mainClass implements Runnable {
    graphicUI loginInterface;
    public static void main(String[] args) {
        mainClass mainClass = new mainClass();
        mainClass.initilizeInterface();
        Thread thread1 = new Thread(mainClass);
        thread1.start();


    }

    public void initilizeInterface(){
       loginInterface = new graphicUI();
    }

    @Override
    public void run() {
        Client client = new Client(this.loginInterface);
        client.start();
    }
}

