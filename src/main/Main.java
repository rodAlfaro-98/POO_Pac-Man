package main;

import Pac.man.PacmanJuego;
import pacman.infra.Pantalla;
import pacman.infra.Juego;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Juego game = new PacmanJuego();
                Pantalla view = new Pantalla(game);
                JFrame frame = new JFrame();
                frame.setTitle("PACMAN-POOderoso");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(view);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                view.requestFocus();
                view.start();
            }

        });
    }
    
}
