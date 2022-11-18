package Client.src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class graphicUI extends JFrame implements KeyListener {
    public int BLOCKS = 24;
    public int DEFAULTSIZE = 40;
    public int SPACING = 5;
    public int HEIGHT = BLOCKS*DEFAULTSIZE;
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
    public JPanel panel;
    JLabel backgroundLabel;
    public BufferedImage backgroundImage;
    public BufferedImage iceIcon;
    public BufferedImage sealIcon;
    public BufferedImage birdIcon;
    public BufferedImage picoIcon;
    public BufferedImage orangeIcon;
    public BufferedImage bananaIcon;
    public BufferedImage eggplantIcon;
    public BufferedImage lettuceIcon;
    public BufferedImage playerIcon;
    public String background = "#5c5b85";

    Font font = new Font("Arial", Font.PLAIN, 16);

    public graphicUI() {
        this.setTitle("Ice Climber");
        this.setVisible(true);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode(background));
        this.getContentPane().add(this.panel);

        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/background.png"));
            iceIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/ice.png"));
            sealIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/seall.png"));
            birdIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/birdl.png"));
            playerIcon = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/right3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel newSeal = new JLabel(new ImageIcon(sealIcon));
        newSeal.setSize(DEFAULTSIZE, DEFAULTSIZE);
        newSeal.setLocation(0, FOURTHFLOOR - DEFAULTSIZE + SPACING);
        panel.add(newSeal);

        this.npcs = new Hashtable();
        this.npcs.put("S1", newSeal);

        JLabel newPlayer = new JLabel(new ImageIcon(playerIcon));
        newPlayer.setSize(DEFAULTSIZE, 2 * DEFAULTSIZE);
        newPlayer.setLocation(0, GROUND - 2 * DEFAULTSIZE + SPACING);
        panel.add(newPlayer);

        JLabel newBird = new JLabel(new ImageIcon(birdIcon));
        newBird.setSize(DEFAULTSIZE, DEFAULTSIZE);
        newBird.setLocation(0, FIFTHFLOOR - DEFAULTSIZE + SPACING);
        panel.add(newBird);

        this.npcs.put("B2", newBird);

        ground = buildGround(GROUND);
        secondFloor = buildGround(SECONDFLOOR);
        subSecond = buildFloor(SUBSECOND);
        thirdFloor = buildGround(THIRDFLOOR);
        subThird = buildFloor(SUBTHIRD);
        fourthFloor = buildGround(FOURTHFLOOR);
        subFourth = buildFloor(SUBFOURTH);
        fifthFloor = buildGround(FIFTHFLOOR);
        subFifth = buildFloor(SUBFIFTH);



        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setSize(WIDTH, HEIGHT);
        backgroundLabel.setLocation(0, 0);
        this.add(backgroundLabel);
        //panel.add(backgroundLabel);



        //newFloors();
/*

       if (!admin){ USAR PARA CLIENTE OBSERVADOR O JUGADOR
            usersAdminButton.setEnabled(false);
        }
*/

        this.panel.repaint();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        System.out.println("I am in update Npcs");
        // Coordenadas para npc
        int x;
        int y;

        for (Enumeration npc = npcsServer.keys(); npc.hasMoreElements();) {
            Object keyId = npc.nextElement(); // Este key ayuda a obtener el elemento del diccionario
            String id = keyId.toString(); // Este string será para identificar el ID para cada imagen

            x = npcsServer.get(keyId)[0];
            y = npcsServer.get(keyId)[1];

            if(this.npcs.isEmpty()) {
                createNpc(id, x, y);
            } else if (isKeyPresent(id)) {
                this.npcs.get(keyId).setLocation(x, y);
            }
            else {
                System.out.println("No hay un id. Entonces voy a crearlo el id es: "+ id);
                createNpc(id, x, y);
            }
        }
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
                System.out.println("Create seat at x="+x+" y="+y);
                JLabel seal = new JLabel(new ImageIcon(sealIcon));
                seal.setSize(DEFAULTSIZE, DEFAULTSIZE);
                seal.setLocation(x, y);
                this.panel.add(seal);
                this.npcs.put(id,seal);
                this.panel.revalidate();
                this.panel.repaint();
                break;
            case 'B': // Birds
                System.out.println("Create bird at x="+x+" y="+y);
                JLabel bird = new JLabel(new ImageIcon(birdIcon));
                bird.setSize(DEFAULTSIZE, DEFAULTSIZE);
                bird.setLocation(x, y);
                this.panel.add(bird);
                this.npcs.put(id,bird);
                this.panel.revalidate();
                this.panel.repaint();
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

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
