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
    public int THIRDFLOOR = SECONDFLOOR - SPACING*DEFAULTSIZE;
    public int FOURTHFLOOR = THIRDFLOOR - SPACING*DEFAULTSIZE;
    public int FIFTHFLOOR = FOURTHFLOOR - SPACING*DEFAULTSIZE;
    public int SIXTHFLOOR = FIFTHFLOOR - SPACING*DEFAULTSIZE;
    public JLabel[] ground;
    public JLabel[] secondFloor;
    public JLabel[] thirdFloor;
    public JLabel[] fourthFloor;
    public JLabel[] fifthFloor;
    public JLabel[] sixthFloor;
    public JPanel panel;
    public BufferedImage iceIcon;
    public String background = "#5c5b85";

    Font font = new Font("Arial", Font.PLAIN, 16);

    public graphicUI(){
        this.setTitle("Ice Climber");
        this.setVisible(true);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode(background));
        this.getContentPane().add(this.panel);

        try {
            iceIcon = ImageIO.read(getClass().getClassLoader().getResource("assets/ice.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        secondFloor = buildFloor(SECONDFLOOR);
        thirdFloor = buildFloor(THIRDFLOOR);
        fourthFloor = buildFloor(FOURTHFLOOR);
        fifthFloor = buildFloor(FIFTHFLOOR);
        sixthFloor = buildFloor(SIXTHFLOOR);
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
        int blocksToFill = BLOCKS;
        int beginningPosition = 0;
        int randomLength = 0;
        while (blocksToFill > 0){
            if (blocksToFill < 8) {
                randomLength = BLOCKS - blocksToFill - 1;
            } else {
                randomLength = rand.nextInt((blocksToFill/2 + beginningPosition + 3) + 1) + 3;
                if ((randomLength + beginningPosition) > 24) {
                    randomLength = BLOCKS - blocksToFill - 1;
                }
            }
            System.out.println(randomLength);
            for (int i = beginningPosition; i <= beginningPosition + randomLength; i++){
                if (i < 24){
                    JLabel newBlock = new JLabel(new ImageIcon(iceIcon));
                    newBlock.setSize(DEFAULTSIZE, DEFAULTSIZE);
                    newBlock.setLocation(i*DEFAULTSIZE, floorPosition);
                    panel.add(newBlock);
                    System.out.println(i);
                    floor[i] = newBlock;
                }
            }
            beginningPosition = beginningPosition + randomLength + 3;
            blocksToFill = blocksToFill - beginningPosition;
        }
        return floor;
    }

    public void newFloors(){
        deleteFLoor(ground);
        deleteFLoor(secondFloor);
        deleteFLoor(thirdFloor);
        adjustYPos(GROUND, fourthFloor);
        adjustYPos(SECONDFLOOR, fifthFloor);
        adjustYPos(THIRDFLOOR, sixthFloor);
        ground = fourthFloor;
        secondFloor = fifthFloor;
        thirdFloor = sixthFloor;
        fourthFloor = buildFloor(FOURTHFLOOR);
        fifthFloor = buildFloor(FIFTHFLOOR);
        sixthFloor = buildFloor(SIXTHFLOOR);
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

    public void deleteFLoor(JLabel[] floor){
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
        newFloors();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
