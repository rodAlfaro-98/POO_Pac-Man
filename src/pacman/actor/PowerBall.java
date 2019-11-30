package pacman.actor;

import Pac.man.PacmanActor;
import Pac.man.PacmanJuego;
import Pac.man.PacmanJuego.State;
import java.awt.Rectangle;

/**
 * Clase de comida
 * 
 * 
 */
public class PowerBall extends PacmanActor {
    
    private int col;
    private int fila;
    private boolean eated;
    
    public PowerBall(PacmanJuego game, int col, int row) {
        super(game);
        this.col = col;
        this.fila = row;
    }

    @Override
    public void init() {
        loadFrames("/Sprites/powerBall.png");
        x = col * 8 + 1 - 32;
        y = (fila + 3) * 8 + 1;
        collider = new Rectangle(0, 0, 4, 4);
        eated = true;
    }

    @Override
    public void update() {
        visible = !eated && (int) (System.nanoTime() * 0.0000000075) % 2 == 0;
        if (eated || game.getState() == State.PACMAN_DIED) {
            return;
        }
        if (game.checkCollision(this, Pacman.class) != null) {
            eated = true;
            visible = false;
            game.addScore(50);
            game.startGhostVulnerableMode();
        }
    }
    
    

    @Override
    public void stateChanged() {
        if (game.getState() == PacmanJuego.State.TITULO 
                || game.getState() == State.LEVEL_CLEARED 
                || game.getState() == State.GAME_OVER) {
            eated = true;;
        }
        else if (game.getState() == PacmanJuego.State.INICIO) {
            eated = false;
            visible = true;
        }
    }

    public void hideAll() {
        visible = false;
    }
    
}
