package pacman.infra;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Teclado extends KeyAdapter {
    
    public static boolean[] TeclaPresionada = new boolean[256];

    @Override
    public void keyPressed(KeyEvent e) {
        TeclaPresionada[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        TeclaPresionada[e.getKeyCode()] = false;
    }
    
}
