package Client.src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class graphicUI extends JFrame implements KeyListener {

    private int LABELWIDTH = 100;
    private int LABELHEIGHT = 20;
    private  int LABELINITIALPOSX = 10;
    private int LABELIN0ITIALPOSY = 0;
    private int LABELGAPRIGHT = 120;
    private int LABELGAPDOWN = 25;
    public int playerCoordx1 = 0;
    public int playerCoordy1 = 0;
    public int playerCoordx2 = 0;
    public int playerCoordy2 = 0;
    public int totalPlayer = 0;
    public int isFloorMoving = 0;
    public int BLOCKS = 24;
    public int DEFAULTSIZE = 40;
    public int SPACING = 5;
    public int MOVEMENT = 5;
    public int HEIGHT = BLOCKS*DEFAULTSIZE + DEFAULTSIZE;
    public int WIDTH = BLOCKS*DEFAULTSIZE;
    public int GROUND = HEIGHT - 2*DEFAULTSIZE;
    public int SECONDFLOOR = GROUND - SPACING*DEFAULTSIZE;
    public int SUBSECOND = SECONDFLOOR + DEFAULTSIZE;
    public int THIRDFLOOR = SECONDFLOOR - SPACING*DEFAULTSIZE;
    public int SUBTHIRD = THIRDFLOOR + DEFAULTSIZE;
    public int FOURTHFLOOR = THIRDFLOOR - SPACING*DEFAULTSIZE;
    public int SUBFOURTH = FOURTHFLOOR + DEFAULTSIZE;
    public int FIFTHFLOOR = FOURTHFLOOR - SPACING*DEFAULTSIZE;
    public int SUBFIFTH = FIFTHFLOOR + DEFAULTSIZE;
    public JLabel[] ground;
    public JLabel[] secondFloor;
    public JLabel[] subSecond;
    public JLabel[] thirdFloor;
    public JLabel[] subThird;
    public JLabel[] fourthFloor;
    public JLabel[] subFourth;
    public JLabel[] fifthFloor;
    public JLabel[] subFifth;
    public Dictionary<String, JLabel> npcs;
    public Dictionary<String, int[]> npcsServer;
    public JPanel panel;
    JLabel backgroundLabel;
    JLabel player1;
    JLabel player2;
    JLabel player1Label;
    JLabel player2Label;
    JLabel lifeP1;
    JLabel lifeP2;
    public int lifeP1int = 3;
    public int lifeP2int = 3;
    public int pointsP1int = 0;
    public int pointsP2int = 0;
    JLabel pointsP1;
    JLabel pointsP2;
    JLabel labelLifeP1;
    JLabel labelLifeP2;
    JLabel labelPointP1;
    JLabel labelPointP2;
    public BufferedImage backgroundImage;
    public BufferedImage iceIcon;
    public BufferedImage sealIcon;
    public BufferedImage birdIcon;
    public BufferedImage picoIcon;
    public BufferedImage orangeIcon;
    public BufferedImage bananaIcon;
    public BufferedImage eggplantIcon;
    public BufferedImage lettuceIcon;
    public BufferedImage player1Icon;
    public BufferedImage player2Icon;
    public BufferedImage fireIcon;
    public BufferedImage hammerIcon;

    public String typeInterface;
    public Client client;
    public String background = "#5c5b85";

    Font font = new Font("Arial", Font.PLAIN, 16);

    public graphicUI(String typeInterface, int players) {
        this.typeInterface = typeInterface;
        this.totalPlayer = players;
        this.setTitle("Ice Climber " + typeInterface);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        addKeyListener(this);

        // Inicializar panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode(background));
        this.getContentPane().add(this.panel);

        // Creación de las imágenes que se van a utilizar
        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/background.png"));
            iceIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/ice.png"));
            sealIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/seall.png"));
            birdIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/birdl.png"));
            picoIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/hielo.png"));
            player1Icon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/right1.png"));
            player2Icon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/right3.png"));
            fireIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/fuego.png"));
            hammerIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/hammer.png"));
            lettuceIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/lettuce.png"));
            bananaIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/banana.png"));
            orangeIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/orange.png"));
            eggplantIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/eggplant.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inicializar diccionario con npcs
        this.npcs = new Hashtable();

        // Inicializar jugadores

        if(this.totalPlayer == 2){
            this.player1 = new JLabel(new ImageIcon(player1Icon));
            player1.setSize(DEFAULTSIZE, 2 * DEFAULTSIZE);
            player1.setLocation(DEFAULTSIZE*8, SECONDFLOOR - 2 * DEFAULTSIZE + SPACING);
            panel.add(player1);

            this.player2 = new JLabel(new ImageIcon(player2Icon));
            player2.setSize(DEFAULTSIZE, 2 * DEFAULTSIZE);
            player2.setLocation(50, GROUND - 2 * DEFAULTSIZE + SPACING*2);
            panel.add(player2);
        }else {
            this.player1 = new JLabel(new ImageIcon(player1Icon));
            player1.setSize(DEFAULTSIZE, 2 * DEFAULTSIZE);
            player1.setLocation(DEFAULTSIZE*8, SECONDFLOOR - 2 * DEFAULTSIZE + SPACING);
            panel.add(player1);
        }

        updatePlayerCoords();

        // Iniciar labels para puntos y vidas
        this.player1Label = new JLabel("Jugador 1");
        this.player1Label.setSize(LABELWIDTH, LABELHEIGHT);
        this.player1Label.setLocation(LABELINITIALPOSX,LABELIN0ITIALPOSY);
        this.panel.add(this.player1Label);

        this.labelLifeP1 = new JLabel("Vida: ");
        this.labelLifeP1.setSize(LABELWIDTH,LABELHEIGHT);
        this.labelLifeP1.setLocation(LABELINITIALPOSX,LABELIN0ITIALPOSY + LABELGAPDOWN);
        this.panel.add(this.labelLifeP1);

        this.lifeP1 = new JLabel("0");
        this.lifeP1.setSize(LABELWIDTH,LABELHEIGHT);
        this.lifeP1.setLocation(LABELINITIALPOSX + LABELGAPRIGHT / 2,LABELIN0ITIALPOSY + LABELGAPDOWN);
        this.panel.add(this.lifeP1);

        this.labelPointP1 = new JLabel("Puntos: ");
        this.labelPointP1.setSize(LABELWIDTH,LABELHEIGHT);
        this.labelPointP1.setLocation(LABELINITIALPOSX,LABELIN0ITIALPOSY + LABELGAPDOWN * 2);
        this.panel.add(this.labelPointP1);

        this.pointsP1 = new JLabel("0");
        this.pointsP1.setSize(LABELWIDTH,LABELHEIGHT);
        this.pointsP1.setLocation(LABELINITIALPOSX + LABELGAPRIGHT / 2,LABELIN0ITIALPOSY + LABELGAPDOWN * 2);
        this.panel.add(this.pointsP1);

        this.player2Label = new JLabel("Jugador 2");
        this.player2Label.setSize(LABELWIDTH, LABELHEIGHT);
        this.player2Label.setLocation(LABELINITIALPOSX + LABELGAPRIGHT,LABELIN0ITIALPOSY);
        this.panel.add(this.player2Label);

        this.labelLifeP2 = new JLabel("Vida: ");
        this.labelLifeP2.setSize(LABELWIDTH,LABELHEIGHT);
        this.labelLifeP2.setLocation(LABELINITIALPOSX + LABELGAPRIGHT,LABELIN0ITIALPOSY + LABELGAPDOWN);
        this.panel.add(this.labelLifeP2);

        this.lifeP2 = new JLabel("0");
        this.lifeP2.setSize(LABELWIDTH,LABELHEIGHT);
        this.lifeP2.setLocation(LABELINITIALPOSX + LABELGAPRIGHT + LABELGAPRIGHT / 2 ,LABELIN0ITIALPOSY + LABELGAPDOWN);
        this.panel.add(this.lifeP2);

        this.labelPointP2 = new JLabel("Puntos: ");
        this.labelPointP2.setSize(LABELWIDTH,LABELHEIGHT);
        this.labelPointP2.setLocation(LABELINITIALPOSX + LABELGAPRIGHT ,LABELIN0ITIALPOSY + LABELGAPDOWN * 2);
        this.panel.add(this.labelPointP2);

        this.pointsP2 = new JLabel("0");
        this.pointsP2.setSize(LABELWIDTH,LABELHEIGHT);
        this.pointsP2.setLocation(LABELINITIALPOSX + LABELGAPRIGHT + LABELGAPRIGHT / 2,LABELIN0ITIALPOSY + LABELGAPDOWN * 2);
        this.panel.add(this.pointsP2);


        // Inicializar los pisos
        ground = buildGround(GROUND);
        secondFloor = buildGround(SECONDFLOOR);
        subSecond = buildFloor(SUBSECOND);
        thirdFloor = buildGround(THIRDFLOOR);
        subThird = buildFloor(SUBTHIRD);
        fourthFloor = buildGround(FOURTHFLOOR);
        subFourth = buildFloor(SUBFOURTH);
        fifthFloor = buildGround(FIFTHFLOOR);
        subFifth = buildFloor(SUBFIFTH);


        // Imagen de fondo
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setSize(WIDTH, HEIGHT);
        backgroundLabel.setLocation(0, 0);

        //panel.add(backgroundLabel);



        //newFloors();
/*

       if (!admin){ USAR PARA CLIENTE OBSERVADOR O JUGADOR
            usersAdminButton.setEnabled(false);
        }
*/

        this.panel.repaint();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setClient(Client client){
        this.client = client;
    }

    public JLabel[] buildFloor (int floorPosition){
        Random rand = new Random();
        JLabel[] floor = new JLabel[BLOCKS];
        for (int i = 0; i < BLOCKS; i++){
            if (rand.nextInt((1) + 1) == 1) {
                JLabel newBlock = new JLabel(new ImageIcon(iceIcon));
                newBlock.setSize(DEFAULTSIZE, DEFAULTSIZE);
                newBlock.setLocation(i * DEFAULTSIZE, floorPosition);
                panel.add(newBlock);
                floor[i] = newBlock;
            }
        }
        return floor;
    }

    public JLabel[]  buildGround (int floorPosition){
        JLabel[] floor = new JLabel[BLOCKS];
        for (int i = 0; i < BLOCKS; i++){
            JLabel newBlock = new JLabel(new ImageIcon(iceIcon));
            newBlock.setSize(DEFAULTSIZE, DEFAULTSIZE);
            newBlock.setLocation(i*DEFAULTSIZE, floorPosition);
            panel.add(newBlock);
            floor[i] = newBlock;
        }
        return floor;
    }

    public void newFloors(){
        deleteFloor(ground);
        deleteFloor(secondFloor);
        deleteFloor(subSecond);
        deleteFloor(thirdFloor);
        deleteFloor(subThird);
        deleteFloor(subFourth);
        adjustYPos(GROUND, fourthFloor);
        adjustYPos(SECONDFLOOR, fifthFloor);
        adjustYPos(SUBSECOND, subFifth);
        ground = fourthFloor;
        secondFloor = fifthFloor;
        subSecond = subFifth;
        thirdFloor = buildGround(THIRDFLOOR);
        subThird = buildFloor(SUBTHIRD);
        fourthFloor = buildGround(FOURTHFLOOR);
        subFourth = buildFloor(SUBFOURTH);
        fifthFloor = buildGround(FIFTHFLOOR);
        subFifth = buildFloor(SUBFIFTH);
        this.panel.repaint();
    }

    public void adjustYPos(int floorPosition, JLabel[] floor){
        try{
            for(int i = 0; i < BLOCKS; i++){
                floor[i].setLocation(floor[i].getX(), floorPosition);
            }
        } catch (Exception e){

        }
    }

    public void deleteFloor(JLabel[] floor){
        try{
            for(int i = 0; i < BLOCKS; i++){
                this.remove(floor[i]);
                this.revalidate();
            }
        } catch (Exception e){
        }
    }

    /* Toma los npcs que manda el servidor y lo actualiza en pantalla */
    public void updateNpcs(Dictionary<String, int[]> npcsServer)
    {
        this.npcsServer = npcsServer;
        // Coordenadas para npc
        int x;
        int y;

        for (Enumeration npc = npcsServer.keys(); npc.hasMoreElements();) {
            Object keyId = npc.nextElement(); // Este key ayuda a obtener el elemento del diccionario
            String id = keyId.toString(); // Este string será para identificar el ID para cada imagen

            x = npcsServer.get(keyId)[0];
            y = npcsServer.get(keyId)[1];

            if(this.npcs.isEmpty()) {
                //System.out.println("Esta vacio, crear primer npc, el id es:  "+ id);
                createNpc(id, x, y);
            } else if (isKeyPresent(id)) {
                //System.out.println("Update npc:  "+ id + " en las coordenadas ("+x+","+y+")");
                this.npcs.get(keyId).setLocation(x, y);
                this.panel.repaint();
            }
            else {
                //System.out.println("No hay un id. Entonces voy a crearlo el id es: "+ id);
                createNpc(id, x, y);
            }
        }
    }

    /*Esto es solo para cliente observador*/
    public void updatePlayer(int x1, int x2, int y1, int y2)
    {
        this.player1.setLocation(x1,y1);
        this.player2.setLocation(x2,y2);
    }

    /* Revisa si ya existe el id de los npcs, y si no existe retorna True*/
    private boolean isKeyPresent(String id)
    {
        for (Enumeration npc = this.npcs.keys(); npc.hasMoreElements();) {
            Object keyId = npc.nextElement(); // Este key ayuda a obtener el elemento del diccionario
            String idToCheck = keyId.toString(); // Este string será para identificar el ID para cada imagen

            if(id.equals(idToCheck)){
                return true;
            }
        }
        return false;
    }

    private void createNpc(String id, int x, int y)
    {

        char identifier = id.charAt(0);
        switch (identifier) {
            case 'S': // Seals
                //System.out.println("Create seat at x="+x+" y="+y);
                JLabel seal = new JLabel(new ImageIcon(sealIcon));
                seal.setSize(DEFAULTSIZE, DEFAULTSIZE);
                seal.setLocation(x, y);
                this.panel.add(seal);
                this.panel.repaint();
                this.panel.revalidate();
                this.npcs.put(id,seal);
                break;
            case 'B': // Birds
                //System.out.println("Create bird at x="+x+" y="+y);
                JLabel bird = new JLabel(new ImageIcon(birdIcon));
                bird.setSize(DEFAULTSIZE, DEFAULTSIZE);
                bird.setLocation(x, y);
                this.panel.add(bird);
                this.panel.repaint();
                this.panel.revalidate();
                this.npcs.put(id,bird);
                break;
            case 'P': // Picos
                System.out.println("Create pico at x="+x+" y="+y);
                JLabel image = new JLabel(new ImageIcon(picoIcon));
                image.setSize(DEFAULTSIZE, DEFAULTSIZE);
                image.setLocation(x, y);
                this.panel.add(image);
                this.panel.repaint();
                this.panel.revalidate();
                this.npcs.put(id,image);
                break;
            case 'O': // Oranges
                System.out.println("Create orange at x="+x+" y="+y);
                JLabel orange = new JLabel(new ImageIcon(orangeIcon));
                orange.setSize(DEFAULTSIZE, DEFAULTSIZE);
                orange.setLocation(x, y);
                this.panel.add(orange);
                this.npcs.put(id,orange);
                this.panel.revalidate();
                this.panel.repaint();
                break;
            case 'A': // Bananas
                System.out.println("Create banana at x="+x+" y="+y);
                JLabel banana = new JLabel(new ImageIcon(bananaIcon));
                banana.setSize(DEFAULTSIZE, DEFAULTSIZE);
                banana.setLocation(x, y);
                this.panel.add(banana);
                this.npcs.put(id,banana);
                this.panel.revalidate();
                this.panel.repaint();
                break;
            case 'E': // Eggplant
                System.out.println("Create eggplant at x="+x+" y="+y);
                JLabel eggplant = new JLabel(new ImageIcon(eggplantIcon));
                eggplant.setSize(DEFAULTSIZE, DEFAULTSIZE);
                eggplant.setLocation(x, y);
                this.panel.add(eggplant);
                this.npcs.put(id,eggplant);
                this.panel.revalidate();
                this.panel.repaint();
                break;
            case 'L': // Lettuce
                System.out.println("Create lettuce at x="+x+" y="+y);
                JLabel lettuce = new JLabel(new ImageIcon(lettuceIcon));
                lettuce.setSize(DEFAULTSIZE, DEFAULTSIZE);
                lettuce.setLocation(x, y);
                this.panel.add(lettuce);
                this.npcs.put(id,lettuce);
                this.panel.repaint();
                break;
        }
    }

    public void updatePointsAndLifes(int points1, int points2, int lifes1, int lifes2) throws InterruptedException {
        if(this.totalPlayer == 2) {
            System.out.println(lifes1 +" "+this.lifeP1int+" "+lifeP2int+ " "+ lifes2);
            String pointP1 = Integer.toString(points1);
            String pointP2 = Integer.toString(points2);
            String lifeP1 = Integer.toString(lifes1);
            String lifeP2 = Integer.toString(lifes2);
            if(lifes1 == this.lifeP1int - 1){
                //loseLife(this.player1, 1);
            }
            else if(lifes1 == 0)
            {
                die(1);

            }
            if(lifes2 == 0)
            {
                die(2);

            } else if(lifes2 == this.lifeP2int - 1 ){
                //loseLife(this.player2, 2);
            }
            if(lifes2 == 0 && lifes1==0)
                // termianr juego
            this.lifeP2int = lifes2;
            this.lifeP1int = lifes1;
            this.lifeP1.setText(lifeP1);
            this.lifeP2.setText(lifeP2);
            this.pointsP1.setText(pointP1);
            this.pointsP2.setText(pointP2);
        }else {
            String pointP1 = Integer.toString(points1);
            String lifeP1 = Integer.toString(lifes1);
            if(lifes1 == 0)
            {
                die(1);
            }
            else if(lifes1 < this.lifeP1int){
                loseLife(this.player1, 1);
            }
            this.lifeP1.setText(lifeP1);
            this.pointsP1.setText(pointP1);
        }
        updatePlayerCoords();

        // Validar si tiene nuevas vidas o nuevos puntos
    }

    public void updatePointsAndLifesInObserver(String points1, String points2, String lifes1, String lifes2)
    {
        if(this.totalPlayer == 2) {
            this.lifeP1.setText(lifes1);
            this.lifeP2.setText(lifes2);
            this.pointsP1.setText(points1);
            this.pointsP2.setText(points2);
        }else {
            this.lifeP1.setText(lifes1);
            this.pointsP1.setText(points1);
        }

        // Validar si tiene nuevas vidas o nuevos puntos
    }
    public void updatePlayerCoords()
    {
        if(this.totalPlayer == 2) {
            this.playerCoordx1 = this.player1.getX();
            this.playerCoordy1 = this.player1.getY();
            this.playerCoordx2 = this.player2.getX();
            this.playerCoordy2 = this.player2.getY();
        }
        else{
            this.playerCoordx1 = this.player1.getX();
            this.playerCoordy1 = this.player1.getY();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(this.typeInterface != "Observer"){
            infoPackage i = new infoPackage();
            if (keyEvent.getKeyChar() == 'a') { // mover a la izquierda para jugador 1
                playerMovingLeft(this.player1);
            } else if (keyEvent.getKeyChar() == 'd') { // mover a la derecha para jugador 1
                playerMovingRight(this.player1);
            } else if (keyEvent.getKeyChar() == 's') { // Para funcion martillo de jugador 1
                i.event = "sledgehammer";
                i.playerId = "P1";
                i.blockNumber = 0;
                i.floorNumber = 0;
                i.isFloorMoving = 0;
                i.playerCoordx = this.playerCoordx1;
                i.playerCoordy = this.playerCoordy1;
                try {
                    this.client.sendSledgehammer(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (keyEvent.getKeyCode() == keyEvent.VK_LEFT) { // Mover a la izquierda jugador 2
                playerMovingLeft(this.player2);
            } else if (keyEvent.getKeyCode() == keyEvent.VK_RIGHT) { // Mover a la derecha jugador 2
                playerMovingRight(this.player2);
            } else if (keyEvent.getKeyCode() == keyEvent.VK_DOWN) { // Para función de martillo
                i.event = "sledgehammer";
                i.playerId = "P2";
                i.blockNumber = 0;
                i.floorNumber = 0;
                i.isFloorMoving = 0;
                i.playerCoordx = this.playerCoordx2;
                i.playerCoordy = this.playerCoordy2;
                try {
                    this.client.sendSledgehammer(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(this.typeInterface != "Observer"){
            if(keyEvent.getKeyChar()=='w'){ // Salta y romper bloque de jugador 1
                jump(this.player1);
            }
            else if (keyEvent.getKeyCode() == keyEvent.VK_UP) { // Saltar y romper de jugador 2
                jump(this.player2);
        }
    }

}
    private void playerMovingRight(JLabel player){
        int newx = player.getX() + MOVEMENT;
        int newy = player.getY();
        if(newx < WIDTH - DEFAULTSIZE)
            player.setLocation(newx, newy);
    }
    private void playerMovingLeft(JLabel player){
        int newx = player.getX() - MOVEMENT;
        int newy = player.getY();
        if(newx > DEFAULTSIZE)
            player.setLocation(newx, newy);
    }

    private void jump(JLabel player){
        int newx = player.getX();
        int newy = player.getY();
        int n = newy;
        if(newx < HEIGHT - DEFAULTSIZE) {
            System.out.println("I am here");
            while (n >= newy - MOVEMENT*39) {
                n = n - MOVEMENT;
                player.setLocation(newx, n);
            }
        }
    }

    private void die(int player) {
        System.out.println("Me voy a morir");
        if (player == 1)
        {
            this.panel.remove(this.player1);
        }else {
            this.panel.remove(this.player2);
        }
    }
    private void loseLife(JLabel player, int playerId) throws InterruptedException {
        System.out.println("Perdi una vida id "+playerId);
        int newx = player.getX();
        int newy = player.getY();
        if(playerId == 1){
            this.player1.setIcon(new ImageIcon());
            this.player1.setLocation(newx, newy+DEFAULTSIZE*10);
            updatePlayerCoords();
            Thread.sleep(250);
            this.player2.setIcon(new ImageIcon(player1Icon));
            player1.setLocation(newx, newy);
            updatePlayerCoords();
        } else if (playerId == 2) {
            this.player2 = new JLabel(new ImageIcon(player1Icon));
            player2.setSize(DEFAULTSIZE, 2 * DEFAULTSIZE);
            player2.setLocation(newx, newy);
            panel.add(player2);
        }

    }

}
