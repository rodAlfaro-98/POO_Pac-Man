
package unam.fi.poo.vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;

import unam.fi.poo.estructuras.Plano;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.objetos.PacMan;
import unam.fi.poo.objetos.Blinky;
import unam.fi.poo.objetos.Inky;
import unam.fi.poo.objetos.Clyde;
import unam.fi.poo.objetos.Pinky;

import unam.fi.poo.interfaces.Ghost;

//import unam.fi.poo.eventos.ManejadorEventos;
import unam.fi.poo.controles.Grupo;

public class GameScene extends Scene{

	private final static int TAMX = 26, TAMY = 29;
	private final static double x = 12, y = 12, r = 1, incX = 8, incY = 8;
	private int width, height;
	private Grupo root;
	private Plano plano;
	private boolean pause = false;
	public PacMan pacMan;
	public Ghost clyde, blinky, inky, pinky;

	public GameScene( Grupo root, int width, int height){
		
		super(root, width, height);
		super.setFill( Color.BLACK );

		this.root = root;
		this.width = width;
		this.height = height;
		addObjects(
			new ImageView(
				new Image(
					new File("./imagenes/Tablero0.png").toURI().toString())) );
		

		setOnKeyPressedScene();
		initGraph();

		this.pacMan = new PacMan("PACMAN", this.plano, "586" );
		this.blinky = new Blinky(this.plano, "274" );
		this.inky = new Inky(this.plano, "349");
		this.pinky = new Pinky(this.plano, "351");
		this.clyde = new Clyde(this.plano, "353");

		this.pacMan.addGhost( this.blinky );
		this.pacMan.addGhost( this.pinky );
		this.pacMan.addGhost( this.inky );
		this.pacMan.addGhost( this.clyde );
		this.pacMan.setRoot( this.root );

	}

	public void startObjects(){

		this.pacMan.start();
		this.blinky.startTimer();		
		this.pinky.startTimer();
		this.inky.startTimer();
		this.clyde.startTimer();
	}

	public void stopObjects(){

		this.pacMan.stop();
		this.blinky.stopTimer();
		this.pinky.stopTimer();
		this.inky.stopTimer();
		this.clyde.stopTimer();
	}

	private void addObjects( Node pp){
		this.root.getChildren().add(pp);
	}

	private void initGraph(){
		
		this.plano = new Plano( x, y, TAMX, TAMY, incX );
		this.plano.cargarPlano();
		this.plano.addEdge( "339", "364" );

		for(Vertex v : this.plano.getMap().values()){
			if( v.getCircle().getRadius() > 0 ){
				addObjects( v.getCircle() );
			}
			else{
				//v.getCircle().setRadius(1);
				//addObjects( v.getCircle() );
				v.getCircle().setVisible(false);
			}
		}
	}

	private void updateRoot(){
		for(Vertex v : this.plano.getMap().values()){
			if( !this.root.getChildren().contains( v.getCircle() ) )
				addObjects( v.getCircle() );
		}
	}

	private void setOnKeyPressedScene(){

		super.setOnKeyPressed( new EventHandler<KeyEvent>() {	
			public void handle( KeyEvent e ){

				switch( e.getCode() ){
					case UP:
                    	pacMan.setOrientacion("UP"); break;
					case DOWN:
						pacMan.setOrientacion("DOWN"); break;
					case LEFT:
                    	pacMan.setOrientacion("LEFT"); break;
					case RIGHT:
                    	pacMan.setOrientacion("RIGHT"); break;
                    case Q:
                    {
                    	pacMan.restart();
                    	pacMan.restartScene();
                    	pacMan.restartLives();
                    	root.getManejadorEventos().setMenuScene();
                    } break;
                    case P:
                    	if( !pause ){
                    		pacMan.pause();
                    		pause = true;
                    	}
                    	else{
                    		pacMan.play();
                    		pause = false;
                    	}
                    case ENTER:
                    	try{
							pacMan.setIsAlive();
						}
						catch( Exception ex){
							System.out.println("Error!");
						}
						break;
				}
			} 
		});
	}


}