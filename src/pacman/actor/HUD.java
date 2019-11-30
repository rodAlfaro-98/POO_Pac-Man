package pacman.actor;

import Pac.man.PacmanActor;
import Pac.man.PacmanJuego;
import Pac.man.PacmanJuego.State;
import java.awt.Graphics2D;

/**
 * Clase de Estadisiticas y vistas del menu
 * 
 */
public class HUD extends PacmanActor {

    public HUD(PacmanJuego game) {
        super(game);
    }

    @Override
    public void init() {
        loadFrames("/Sprites/pacman_life.png");    
    }

    @Override
    public void draw(Graphics2D g) {
        if (!visible) {
            return;
        }
        game.drawText(g, "Puntaje", 10, 1);
        game.drawText(g, game.getScore(), 10, 10);
        game.drawText(g, "Puntaje Mas Alto ", 78, 1);
        game.drawText(g, game.getHiscore(), 90, 10);
        game.drawText(g, "Vidas: ", 10, 274);
        game.drawText(g, "POO",150, 274);
        //Ciclo For para contar y mostrar vidas del jugador
        for (int Vidas = 0; Vidas < game.Vidas; Vidas++) {
            g.drawImage(frame, 60 + 20 * Vidas, 272, null);
        }
    }

    // Cargar mensaje
    
    @Override
    public void stateChanged() {
        visible = (game.state != State.INITIALIZING)
                && (game.state !=State.PRESENTACION);
    }
    
}
