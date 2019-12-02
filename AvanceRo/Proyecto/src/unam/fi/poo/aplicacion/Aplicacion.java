
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
	private HighScoreGrid highScoreGrid;
	private ManejadorEventos me;
	private ScoreGrid scoreGrid;

	/**
	* @brief Constructor de Aplicación, su finalidad es generar y guardar las distintas ventanas y escenarios necesarios para el funcionamiento del código
	*/
	public Aplicacion(){
		this.me = ManejadorEventos.getInstance(this);
		this.rootGame = new Grupo();
		this.menuGrid = new MenuGrid();
		this.creditsGrid = new CreditsGrid();
		this.scoreGrid = new ScoreGrid();
		this.highScoreGrid = new HighScoreGrid();

		this.menuScene = new Scene( menuGrid, WIDTH, HEIGHT);
		this.gameScene = new GameScene(rootGame, WIDTH, HEIGHT ); //224 x 248
		this.creditsScene = new Scene( creditsGrid, WIDTH, HEIGHT); //224 x 248
		this.scoreScene = new Scene( scoreGrid, WIDTH, HEIGHT );
		this.highScoreScene = new Scene( highScoreGrid, WIDTH, HEIGHT );

	}

	/**
	* @brief Función que inicia toda la interfaz gráfica.
	*/
	public void start(Stage primaryStage){

		this.window = primaryStage;
		this.window.setResizable(false);
		this.window.setTitle("Pac-Man");
		
		window.setScene( this.menuScene );
		window.show();

	}

	public static void main(String [] args){
		launch(args);

	}

	/**
	* @brief Getter del atributo menuGrid
	* @return Un objeto de tipo MenuGrid que contiene a menuGrid
	*/
	public MenuGrid getMenuGrid(){
		return this.menuGrid;
	}

	/**
	* @brief Getter del atributo creditsGrid
	* @return Un objeto de tipo CreditsGrid que contiene a creditsGrid
	*/
	public CreditsGrid getCreditsGrid(){
		return this.creditsGrid;
	}

	/**
	* @brief Getter del atributo scoreGrid
	* @return Un objeto de tipo ScoreGrid que contiene a scoreGrid
	*/
	public ScoreGrid getScoreGrid(){
		return this.scoreGrid;
	}
	
	/**
	* @brief Getter del atributo highScoreGrid
	* @return Un objeto de tipo HighScoreGrid que contiene a highScoreGrid
	*/
	public HighScoreGrid getHighScoreGrid(){
		return this.highScoreGrid;
	}

	/**
	* @brief Setter del atributo sc de tipo Scene
	* @param Objeto de tipo Scene que será aquel que ocupe el valor de sc
	*/
	public void setScene( Scene sc ){
		this.window.setScene( sc );
	}

	/**
	* @brief Getter del atributo menuScene
	* @return Un objeto de tipo MenuScene que contiene a menuScene
	*/
	public Scene getMenuScene(){
		return this.menuScene;
	}

	/**
	* @brief Getter del atributo creditsScene
	* @return Un objeto de tipo CreditsScene que contiene a creditsScene
	*/
	public Scene getCreditsScene(){
		return this.creditsScene;
	}

	/**
	* @brief Getter del atributo gameScene
	* @return Un objeto de tipo GameScene que contiene a gameScene
	*/
	public GameScene getGameScene(){
		return this.gameScene;
	}

	/**
	* @brief Getter del atributo scoreScene
	* @return Un objeto de tipo ScoreScene que contiene a scoreScene
	*/
	public Scene getScoreScene(){
		return this.scoreScene;
	}

	/**
	* @brief Getter del atributo highScoreScene
	* @return Un objeto de tipo HighScoreScene que contiene a highScoreScene
	*/
	public Scene getHighScoreScene(){
		return this.highScoreScene;
	}
}
