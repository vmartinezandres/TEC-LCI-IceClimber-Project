package Client.src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartGameUI extends JFrame implements ActionListener, MouseListener {
    private JButton boton1, boton2, boton3;
    public JLayeredPane welcomePanel;
    public int WIDTH = 960;
    public int HEIGHT = 960;
    public int BUTTONWIDTH = 175;
    public int BUTTONHEIGHT = 175;
    public BufferedImage backgroundImage;
    public BufferedImage oneImage;
    public BufferedImage twoImage;
    public BufferedImage observerImage;
    public JLabel backgroundLabel;
    public Game currentGame;
    public String background = "#5c5b85";

    Font font = new Font("Arial", Font.PLAIN, 16);

    public StartGameUI() {
        this.setTitle("Ice Climber Start Game");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);

        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/initial.png"));
            oneImage = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/one.png"));
            twoImage = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/two.png"));
            observerImage = ImageIO.read(getClass().getClassLoader().getResource("Client/src/assets/observer.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inicializar panel
        this.welcomePanel = new JLayeredPane();
        this.welcomePanel.setBounds(0,0, WIDTH, HEIGHT);
        this.welcomePanel.setBackground(Color.decode(background));
        this.welcomePanel.setLayout(null);
        this.getContentPane().add(this.welcomePanel);

        this.backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        this.backgroundLabel.setSize(960, 960);
        this.backgroundLabel.setLocation(0, 0);
        this.welcomePanel.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        this.boton1 = new JButton(new ImageIcon(oneImage));
        this.boton1.setBounds(100, 392, BUTTONWIDTH, BUTTONHEIGHT);
        this.welcomePanel.add(boton1, JLayeredPane.PALETTE_LAYER);
        this.boton1.addActionListener(this);
        this.boton1.setBackground(new Color(1f,0f,0f,.5f ));

        this.boton2 = new JButton(new ImageIcon(twoImage));
        this.boton2.setBounds(375, 392, BUTTONWIDTH, BUTTONHEIGHT);
        this.welcomePanel.add(boton2, JLayeredPane.PALETTE_LAYER);
        this.boton2.addActionListener(this);
        this.boton2.setBackground(new Color(1f,0f,0f,.5f ));

        this.boton3 = new JButton(new ImageIcon(observerImage));
        this.boton3.setBounds(650, 392, BUTTONWIDTH, BUTTONHEIGHT);
        this.welcomePanel.add(boton3, JLayeredPane.PALETTE_LAYER);
        this.boton3.addActionListener(this);
        this.boton3.setBackground(new Color(1f,0f,0f,.5f ));

        this.boton1.setBorder(BorderFactory.createEmptyBorder());
        this.boton2.setBorder(BorderFactory.createEmptyBorder());
        this.boton3.setBorder(BorderFactory.createEmptyBorder());

        this.boton1.setContentAreaFilled(false);
        this.boton2.setContentAreaFilled(false);
        this.boton3.setContentAreaFilled(false);

        this.boton1.addMouseListener(this);
        this.boton2.addMouseListener(this);
        this.boton3.addMouseListener(this);

        this.welcomePanel.repaint();
        //this.welcomePanel.revalidate();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton1) {
            this.currentGame = new Game();
            this.currentGame.startGame(1);
        }
        else if (e.getSource() == boton2) {
            this.currentGame = new Game();
            this.currentGame.startGame(2);
        }
        else if (e.getSource() == boton3) {
            if(this.currentGame != null);
            this.currentGame.observer();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == boton1) {
            boton1.setContentAreaFilled(true);

        } else if (e.getSource() == boton2) {
            boton2.setContentAreaFilled(true);

        } else if (e.getSource() == boton3) {
            boton3.setContentAreaFilled(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == boton1) {
            boton1.setContentAreaFilled(false);

        } else if (e.getSource() == boton2) {
            boton2.setContentAreaFilled(false);

        } else if (e.getSource() == boton3) {
            boton3.setContentAreaFilled(false);
        }
    }
}
