
package unam.fi.poo.aplicacion;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import unam.fi.poo.vista.MenuGrid;
import unam.fi.poo.vista.GameScene;
import unam.fi.poo.vista.CreditsGrid;
import unam.fi.poo.vista.ScoreGrid;
import unam.fi.poo.vista.HighScoreGrid;
import unam.fi.poo.controles.Grupo;
import unam.fi.poo.eventos.ManejadorEventos;

public class Aplicacion extends Application{
	
	private static final int WIDTH = 224, HEIGHT = 270;
	public Stage window;
	public Scene menuScene , creditsScene, scoreScene, highScoreScene;
	private GameScene gameScene;
	private Grupo rootGame;
	private MenuGrid menuGrid;
	private CreditsGrid creditsGrid;
	private ManejadorEventos me;
	private ScoreGrid scoreGrid;
	private HighScoreGrid highScoreGrid;

	public Aplicacion(){
		this.me = new ManejadorEventos(this);
		this.rootGame = new Grupo( me );
		this.menuGrid = new MenuGrid( me );
		this.creditsGrid = new CreditsGrid( me );
		this.scoreGrid = new ScoreGrid( me );
		this.highScoreGrid = new HighScoreGrid( me );

		this.menuScene = new Scene( menuGrid, WIDTH, HEIGHT);
		this.gameScene = new GameScene(rootGame, WIDTH, HEIGHT ); //224 x 248
		this.creditsScene = new Scene( creditsGrid, WIDTH, HEIGHT); //224 x 248
		this.scoreScene = new Scene( scoreGrid, WIDTH, HEIGHT );
		this.highScoreScene = new Scene( highScoreGrid, WIDTH, HEIGHT );
	}

	public void start(Stage primaryStage){

		this.window = primaryStage;
		this.window.setResizable(false);
		this.window.setTitle("Pac-Man");

		//window.setScene( this.creditsScene );
		window.setScene( this.menuScene );
		//window.setScene( this.gameScene );
		window.show();

	}

	public static void main(String [] args){
		launch(args);

	}

	public MenuGrid getMenuGrid(){
		return this.menuGrid;
	}

	public CreditsGrid getCreditsGrid(){
		return this.creditsGrid;
	}

	public ScoreGrid getScoreGrid(){
		return this.scoreGrid;
	}
	
	public HighScoreGrid getHighScoreGrid(){
		return this.highScoreGrid;
	}

	public void setScene( Scene sc ){
		this.window.setScene( sc );
	}

	public Scene getMenuScene(){
		return this.menuScene;
	}

	public Scene getCreditsScene(){
		return this.creditsScene;
	}

	public GameScene getGameScene(){
		return this.gameScene;
	}

	public Scene getScoreScene(){
		return this.scoreScene;
	}
	
	public Scene getHighScoreScene(){
		return this.highScoreScene;
	}
}
