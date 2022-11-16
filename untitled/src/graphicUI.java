import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class graphicUI extends JFrame implements MouseListener{
    public int HEIGHT = 960;
    public int WIDTH = 960;
    public int DEFAULTSIZE = 40;
    public int GROUND = HEIGHT - 2*DEFAULTSIZE;
    public int SECONDFLOOR = GROUND - 4*DEFAULTSIZE;
    public int THIRDLOOR = SECONDFLOOR - 4*DEFAULTSIZE;
    public int FOURTHFLOOR = THIRDLOOR - 4*DEFAULTSIZE;
    public int FIFTHFLOOR = FOURTHFLOOR - 4*DEFAULTSIZE;

    public int SIXTHFLOOR = FIFTHFLOOR - 4*DEFAULTSIZE;
    public JPanel panel;
    public JButton employeesButton;
    public JButton positionsButton;
    public JButton payrollButton;
    public JButton usersAdminButton;
    public JButton accountButton;
    public JLabel employeesLabel;
    public JLabel positionsLabel;
    public JLabel payrollLabel;
    public JLabel usersAdminLabel;
    public JLabel accountLabel;
    public BufferedImage iceIcon;
    public String background = "#5c5b85";
    public String darkerButtonColor = "#46466b";

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

        employeesButton = new JButton(new ImageIcon(iceIcon));
        positionsButton = new JButton(new ImageIcon(iceIcon));
        payrollButton = new JButton(new ImageIcon(iceIcon));
        usersAdminButton = new JButton(new ImageIcon(iceIcon));
        accountButton = new JButton(new ImageIcon(iceIcon));

        employeesButton.setSize(DEFAULTSIZE, DEFAULTSIZE);
        positionsButton.setSize(DEFAULTSIZE, DEFAULTSIZE);
        payrollButton.setSize(DEFAULTSIZE, DEFAULTSIZE);
        usersAdminButton.setSize(DEFAULTSIZE, DEFAULTSIZE);
        accountButton.setSize(DEFAULTSIZE, DEFAULTSIZE);

        employeesButton.setLocation(0, GROUND);
        positionsButton.setLocation(0, SECONDFLOOR);
        payrollButton.setLocation(0, THIRDLOOR);
        accountButton.setLocation(0, FOURTHFLOOR);
        usersAdminButton.setLocation(0, FIFTHFLOOR);

        employeesButton.setBorder(BorderFactory.createEmptyBorder());
        positionsButton.setBorder(BorderFactory.createEmptyBorder());
        payrollButton.setBorder(BorderFactory.createEmptyBorder());
        usersAdminButton.setBorder(BorderFactory.createEmptyBorder());
        accountButton.setBorder(BorderFactory.createEmptyBorder());

        /*employeesButton.setContentAreaFilled(false);
        positionsButton.setContentAreaFilled(false);
        payrollButton.setContentAreaFilled(false);
        usersAdminButton.setContentAreaFilled(false);
        accountButton.setContentAreaFilled(false);

        employeesButton.setBackground(Color.decode(darkerButtonColor));
        positionsButton.setBackground(Color.decode(darkerButtonColor));
        payrollButton.setBackground(Color.decode(darkerButtonColor));
        usersAdminButton.setBackground(Color.decode(darkerButtonColor));
        accountButton.setBackground(Color.decode(darkerButtonColor));


        employeesButton.addMouseListener(this);
        positionsButton.addMouseListener(this);
        payrollButton.addMouseListener(this);
        usersAdminButton.addMouseListener(this);
        accountButton.addMouseListener(this);*/

        panel.add(employeesButton);
        panel.add(positionsButton);
        panel.add(payrollButton);
        panel.add(accountButton);
        panel.add(usersAdminButton);

/*        if (!admin){ USAR PARA CLIENTE OBSERVADOR O JUGADOR
            usersAdminButton.setEnabled(false);
        } */

        this.panel.repaint();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        /*if (e.getSource() == employeesButton) {
            employeesButton.setContentAreaFilled(true);

        } else if (e.getSource() == positionsButton) {
            positionsButton.setContentAreaFilled(true);

        } else if (e.getSource() == payrollButton) {
            payrollButton.setContentAreaFilled(true);

        } else if (e.getSource() == usersAdminButton) {
            usersAdminButton.setContentAreaFilled(true);

        } else if (e.getSource() == accountButton) {
            accountButton.setContentAreaFilled(true);
        }*/
    }

    @Override
    public void mouseExited(MouseEvent e) {
        /*
        if (e.getSource() == employeesButton) {
            employeesButton.setContentAreaFilled(false);

        } else if (e.getSource() == positionsButton) {
            positionsButton.setContentAreaFilled(false);

        } else if (e.getSource() == payrollButton) {
            payrollButton.setContentAreaFilled(false);

        } else if (e.getSource() == usersAdminButton) {
            usersAdminButton.setContentAreaFilled(false);

        } else if (e.getSource() == accountButton) {
            accountButton.setContentAreaFilled(false);
        }*/
    }
}
