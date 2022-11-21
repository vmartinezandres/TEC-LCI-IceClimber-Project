package Client.src;

public class Game implements Runnable {
    graphicUI loginInterface;
    Client client;
    public static void main(String[] args) {
        Game game = new Game();
        game.initilizeInterface(1);
        Thread thread1 = new Thread(game);
        thread1.start();
        game.observer();
    }

    /*
    * Esta clase inicializa la interfaz y crea un thread para el cliente
    * */
    public void startGame(int players) {
        initilizeInterface(players);
        Thread thread1 = new Thread(this);
        thread1.start();
    }

    /* ESta clase llama a la instancia de interfaz con la cantidad de players respectiva y construye el cliente*/
    public void initilizeInterface(int players){
       loginInterface = new graphicUI("Player", players);
       client = new Client(this.loginInterface);
       loginInterface.setClient(client);
    }

    /*
    * Inicialización del thread
    * */
    @Override
    public void run() {
        client.start();
    }

    /*
    * Llamada al observer, el cual es un espejo del cliente jugador
    * */
    public void observer(){
        ClientObserver clientObserver = new ClientObserver(this.loginInterface);
        clientObserver.start();
    }
}

