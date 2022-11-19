package Client.src;

public class Game implements Runnable {
    graphicUI loginInterface;
    Client client;
    public static void main(String[] args) {
        Game game = new Game();
        game.initilizeInterface(2);
        Thread thread1 = new Thread(game);
        thread1.start();
        game.observer();
    }

    public void startGame(int players) {
        initilizeInterface(players);
        Thread thread1 = new Thread(this);
        thread1.start();
    }

    public void initilizeInterface(int players){
       loginInterface = new graphicUI("Player", players);
       client = new Client(this.loginInterface);
       loginInterface.setClient(client);
    }

    @Override
    public void run() {
        client.start();
    }

    public void observer(){
        ClientObserver clientObserver = new ClientObserver(this.loginInterface);
        clientObserver.start();
    }
}

