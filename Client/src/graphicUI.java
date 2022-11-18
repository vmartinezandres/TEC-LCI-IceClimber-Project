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
import java.util.Random;

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
    public JPanel panel;
    JLabel backgroundLabel;
    public BufferedImage backgroundImage;
    public BufferedImage iceIcon;
    public BufferedImage sealIcon;
    public BufferedImage birdIcon;
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

        JLabel newPlayer = new JLabel(new ImageIcon(playerIcon));
        newPlayer.setSize(DEFAULTSIZE, 2 * DEFAULTSIZE);
        newPlayer.setLocation(0, GROUND - 2 * DEFAULTSIZE + SPACING);
        panel.add(newPlayer);

        JLabel newBird = new JLabel(new ImageIcon(birdIcon));
        newBird.setSize(DEFAULTSIZE, DEFAULTSIZE);
        newBird.setLocation(0, FIFTHFLOOR - DEFAULTSIZE + SPACING);
        panel.add(newBird);

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
        panel.add(backgroundLabel);

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
