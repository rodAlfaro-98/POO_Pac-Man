package pacman.actor;

import Pac.man.PacmanActor;
import Pac.man.PacmanJuego;
import Pac.man.PacmanJuego.State;

/**
 *  
 */
public class Ready extends PacmanActor {

    public Ready(PacmanJuego game) {
        super(game);
    }

    @Override
    public void init() {
        x = 11 * 8;
        y = 20 * 8;
        loadFrames("/Sprites/ready.png");
    }

    @Override
    public void updateReady() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    game.restoreCurrentFoodCount();
                    //game.sounds.get("start").play();
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 2000) { 
                        break yield;
                    }
                    game.setState(State.READY2);
                    break yield;
            }
        }
    }
    
    @Override
    public void updateReady2() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    game.broadcastMessage("showAll");
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 2000) { //
                        break yield;
                    }
                    game.setState(State.PLAYING);
                    break yield;
            }
        }
    }

    

    @Override
    public void stateChanged() {
        visible = false;
        if (game.getState() == PacmanJuego.State.INICIO
                || game.getState() == PacmanJuego.State.READY2) {
            
            visible = true;
            instructionPointer = 0;
        }
    }
    
}
