package Pac.man;

import pacman.actor.Background;
import pacman.actor.Food;
import pacman.actor.GameOver;
import pacman.actor.Ghost;
import pacman.actor.HUD;
import pacman.actor.Iniciar;
import pacman.actor.PresentacionPOOderosa;
import pacman.actor.Pacman;
import pacman.actor.Point;
import pacman.actor.PowerBall;
import pacman.actor.Ready;
import pacman.actor.Titulo;
import pacman.infra.Actor;
import pacman.infra.Juego;
import java.awt.Dimension;
import java.awt.geom.Point2D;


public class PacmanJuego extends Juego {
    
    // Array[Renglones][Columnas] 
    // 36 x 31 
    // cols: 0-3|4-31|32-35
    public int Laberinto[][] = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
        {1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
        {1,1,1,1,1,3,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,3,1,1,1,1,1},
        {1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
        {1,1,1,1,1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,1},
        {1,1,1,1,1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,0,0,0,0,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,2,2,2,2,2,2,2,0,0,0,1,1,0,0,0,0,1,1,0,0,0,2,2,2,2,2,2,2,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
        {1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
        {1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
        {1,1,1,1,1,3,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,3,1,1,1,1,1},
        {1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1,1,1,1,1},
        {1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1},
        {1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };

    public static enum State { INITIALIZING, PRESENTACION, TITULO, INICIO, READY2
        , PLAYING, PACMAN_DIED, GHOST_CATCHED, LEVEL_CLEARED, GAME_OVER }
    
    public State state = State.INITIALIZING;
    public int Vidas = 3;
    public int Marcador;
    public int PuntajeMasAlto;
    
    public Ghost catchedGhost;
    public int currentCatchedGhostScoreTableIndex = 0;
    public final int[] catchedGhostScoreTable = { 200, 400, 800, 1600 };
    
    public int foodCount;
    public int currentFoodCount;
    
    public PacmanJuego() {
        screenSize = new Dimension(224, 288);
        screenScale = new Point2D.Double(2, 2);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (this.state != state) {
            this.state = state;
            broadcastMessage("stateChanged");
        }
    }
    
    public void addScore(int point) {
        Marcador += point;
        if (Marcador > PuntajeMasAlto) {
            PuntajeMasAlto = Marcador;
        }
    }
    
    public String getScore() {
        String scoreStr = "0000000" + Marcador;
        scoreStr = scoreStr.substring(scoreStr.length() - 7, scoreStr.length());
        return scoreStr;
    }

    public String getHiscore() {
        String hiscoreStr = "0000000" + PuntajeMasAlto;
        hiscoreStr = hiscoreStr.substring(hiscoreStr.length() - 7, hiscoreStr.length());
        return hiscoreStr;
    }
    
    @Override
    public void init() {
        addAllObjs();
        initAllObjs();
    }
    
    private void addAllObjs() {
        Pacman pacman = new Pacman(this);
        actors.add(new Iniciar(this));
        actors.add(new PresentacionPOOderosa(this));
        actors.add(new Titulo(this));
        actors.add(new Background(this));
        foodCount = 0;
        for (int row=0; row<31; row++) {
            for (int col=0; col<36; col++) {
                if (Laberinto[row][col] == 1) {
                    Laberinto[row][col] = -1; // Conversion de pared a -1  para la ruta mas corta
                }
                else if (Laberinto[row][col] == 2) {
                    Laberinto[row][col] = 0;
                    actors.add(new Food(this, col, row));
                    foodCount++;
                }
                else if (Laberinto[row][col] == 3) {
                    Laberinto[row][col] = 0;
                    actors.add(new PowerBall(this, col, row));
                }
            }
        }
        for (int i=0; i<4; i++) {
            actors.add(new Ghost(this, pacman, i));
        }
        actors.add(pacman);
        actors.add(new Point(this, pacman));
        actors.add(new Ready(this));
        actors.add(new GameOver(this));
        actors.add(new HUD(this));
    }
    
    private void initAllObjs() {
        for (Actor actor : actors) {
            actor.init();
        }
    }
    
    // ---

    public void restoreCurrentFoodCount() {
        currentFoodCount = foodCount;
    }

    public boolean isLevelCleared() {
        return currentFoodCount == 0;
    }
    
    public void startGame() {
        setState(State.INICIO);
    }
    
    public void startGhostVulnerableMode() {
        currentCatchedGhostScoreTableIndex = 0;
        broadcastMessage("startGhostVulnerableMode");
    }
    
    public void ghostCatched(Ghost ghost) {
        catchedGhost = ghost;
        setState(State.GHOST_CATCHED);
    }
    
    public void nextLife() {
        Vidas--;
        if (Vidas == 0) {
            setState(State.GAME_OVER);
        }
        else {
            setState(State.READY2);
        }
    }

    public void levelCleared() {
        setState(State.LEVEL_CLEARED);
    }

    public void nextLevel() {
        setState(State.INICIO);
    }

    public void returnToTitle() {
        Vidas = 3;
        Marcador = 0;
        setState(State.TITULO);
    }
    
}
