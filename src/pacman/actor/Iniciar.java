package pacman.actor;

import Pac.man.PacmanActor;
import Pac.man.PacmanJuego;
import Pac.man.PacmanJuego.State;

/**
 * Clase para lanzar el juego
 */
public class Iniciar extends PacmanActor {

    public Iniciar(PacmanJuego game) {
        super(game);
    }

    @Override
    public void updateInitializing() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 3000) {
                        break yield;
                    }
                    instructionPointer = 2;
                case 2:
                    game.setState(State.PRESENTACION);
                    break yield;
            }
        }
    }
    
}
