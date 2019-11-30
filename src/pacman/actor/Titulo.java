package pacman.actor;


import Pac.man.PacmanActor;
import Pac.man.PacmanJuego;
import Pac.man.PacmanJuego.State;
import pacman.infra.Teclado;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Clase Titulo
 */
public class Titulo extends PacmanActor {
    
    private boolean pushSpaceToStartVisible;

    public Titulo(PacmanJuego game) {
        super(game);
    }

    @Override
    public void init() {
        loadFrames("/Sprites/title.png");
        x = 21;
        y = 100;
    }
    
    @Override
    public void updateTitle() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 500) {
                        break yield;
                    }
                    instructionPointer = 2;
                case 2:
                    double dy = 100 - y;
                    y = y + dy * 0.1;
                    if (Math.abs(dy) < 1) {
                        waitTime = System.currentTimeMillis();
                        instructionPointer = 3;
                    }
                    break yield;
                case 3:
                    if (System.currentTimeMillis() - waitTime < 200) {
                        break yield;
                    }
                    instructionPointer = 4;
                case 4:
                    pushSpaceToStartVisible = ((int) (System.nanoTime() * 0.0000000075) % 3) > 0;
                    if (Teclado.TeclaPresionada[KeyEvent.VK_SPACE]) {
                        game.startGame();
                    }
                    break yield;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (!visible) {
            return;
        }
        super.draw(g);
        if (pushSpaceToStartVisible) {
            game.drawText(g, "Presiona la tecla \nEspacio para iniciar", 37, 170);
        }
        //game.drawText(g, "PROGRAMACION ORIENTADA\n   A OBJETOS", 5, 255);
        game.drawText(g, "  PROGRAMACION ORIENTADA\n\n        A OBJETOS", 10, 215);
    }

    
    
    
    @Override
    public void stateChanged() {
        visible = false;
        if (game.state == State.TITULO) {
            y = -150;
            visible = true;
            pushSpaceToStartVisible = false;
            instructionPointer = 0;
        }
    }
        
}
