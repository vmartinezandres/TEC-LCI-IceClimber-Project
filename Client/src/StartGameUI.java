package Client.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGameUI extends JFrame implements ActionListener {
    private JButton boton1,boton2,boton3;
    public JPanel panel;
    public Game currentGame;
    public String background = "#5c5b85";

    Font font = new Font("Arial", Font.PLAIN, 16);

    public StartGameUI() {
        this.setTitle("Ice Climber Start Game");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);

        // Inicializar panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode(background));
        this.getContentPane().add(this.panel);


        boton1=new JButton("One Player");
        boton1.setBounds(10,100,90,30);
        this.panel.add(boton1);
        boton1.addActionListener(this);

        boton2=new JButton("Two Players");
        boton2.setBounds(110,100,90,30);
        this.panel.add(boton2);
        boton2.addActionListener(this);

        boton3=new JButton("Observer");
        boton3.setBounds(210,100,90,30);
        this.panel.add(boton3);
        boton3.addActionListener(this);

        this.panel.repaint();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==boton1) {
            this.currentGame = new Game();
            this.currentGame.startGame(1);
        }
        if (e.getSource()==boton2) {
            this.currentGame = new Game();
            this.currentGame.startGame(2);
        }
        if (e.getSource()==boton3) {
            if(this.currentGame != null){
                this.currentGame.observer();
            }
        }
    }
}
