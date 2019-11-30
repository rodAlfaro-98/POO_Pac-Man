package pacman.actor;


import Pac.man.PacmanActor;
import Pac.man.PacmanJuego;
import Pac.man.PacmanJuego.State;
import java.awt.Graphics2D;

/**
 * Presentacion inicial
 */
public class PresentacionPOOderosa extends PacmanActor {
    
    private String text = "LOS POOderosos\n\n  Presentan";
    private int textIndex;

    public PresentacionPOOderosa(PacmanJuego game) {
        super(game);
    }

    @Override
    public void updateOLPresents() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    while (System.currentTimeMillis() - waitTime < 100) {
                        break yield;
                    }
                    textIndex++;
                    if (textIndex < text.length()) {
                        instructionPointer = 0;
                        break yield;
                    }
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 2;
                case 2:
                    while (System.currentTimeMillis() - waitTime < 3000) {
                        break yield;
                    }
                    visible = false;
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 3;
                case 3:
                    while (System.currentTimeMillis() - waitTime < 1500) {
                        break yield;
                    }
                    game.setState(State.TITULO);
                    break yield;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (!visible) {
            return;
        }
        game.drawText(g, text.substring(0, textIndex), 60, 130);
    }

    
    
    
    @Override
    public void stateChanged() {
        visible = false;
        if (game.state == State.PRESENTACION) {
            visible = true;
            textIndex = 0;
        }
    }
        
}
