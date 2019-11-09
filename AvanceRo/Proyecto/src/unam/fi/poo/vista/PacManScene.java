
package unam.fi.poo.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.util.HashMap;

import unam.fi.poo.estructuras.Graph;
import unam.fi.poo.estructuras.Grafo;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.objetos.Sprite;
import unam.fi.poo.objetos.Administrador;
import unam.fi.poo.objetos.Hilo;


public class PacManScene extends Application{

	private Stage window;
	private Scene principal;
	private Group root;
	private Grafo g;
	Ghost blinky;
	Ghost inky;
	Ghost pinky;
	Ghost clyde;
	PacMan pac;
	private int TAMX = 26, TAMY = 29;
	private final double x = 12, y = 12, r = 1, incX = 8, incY = 8;
	//private final double x = 12, y = 12, r = 1, incX = 20, incY = 20;

	private Vertex origen;
	private HashMap<String, Integer> nodos;
	private Administrador admon;

	int start = 10;

	public PacManScene(){
		//createAleatoryGraph();
		this.admon = new Administrador();
		this.nodos = admon.getNodos();
		//admon.generaPuntos();
		
		this.g = new Grafo(TAMX, TAMY, x, y, incX);
		g.createGraph();
		g.addSpecialNode("325");
		g.addSpecialNode("326");
		g.addSpecialNode("299");
		g.addSpecialNode("300");
		g.addEdge( "339", "364" );
		
		origen = g.getVertex("351");
		
		this.pac = new PacMan(g.getVertex("585"), this.g);
		this.blinky =  new Ghost("./imagenes/GHR_", "blinky", origen, this.g, this.pac);
		this.inky =  new Ghost("./imagenes/GHB_", "inky", origen, this.g, this.pac, this.blinky);
		this.pinky =  new Ghost("./imagenes/GHP_", "pinky", origen, this.g, this.pac);
		this.clyde =  new Ghost("./imagenes/GHO_", "clyde", origen, this.g, this.pac);
	}

	public void start(Stage primaryStage){

		Stage window = primaryStage;
		this.root = new Group();

		this.principal = new Scene(root, 224, 248 );
		this.principal.setFill( new ImagePattern( new Image( new File("./imagenes/Tablero0.png").toURI().toString())));
		
		this.principal.setOnKeyPressed( new EventHandler<KeyEvent>() {
			
			public void handle( KeyEvent e ){
				Vertex destino = new Vertex();
				boolean done = false;
				
				switch( e.getCode() ){
					case UP:
					{
                    	destino = g.getVertex( pac.getOrigen().getIntName() - TAMX );
                    	if( destino != null && pac.getOrigen().getNeighbors().contains(destino)){
                    		pac.setEstado("UP");
                    		//rGhost.setEstado("UP");
                    		done = true;
							pac.setDirection(0);
                    	}
					}
					break;
					case DOWN:
					{
                    	destino = g.getVertex( pac.getOrigen().getIntName() + TAMX );
                    	if( destino != null && pac.getOrigen().getNeighbors().contains(destino)){
                    		pac.setEstado("DOWN");
                    		//rGhost.setEstado("DOWN");
                    		done = true;
							pac.setDirection(1);
                    	}
					}
					break;
					case LEFT:
					{
                    	destino = g.getVertex( pac.getOrigen().getIntName() - 1 );
                    	if( destino != null && pac.getOrigen().getNeighbors().contains(destino)){
                    		pac.setEstado("LEFT");
                    		//rGhost.setEstado("LEFT");
                    		done = true;
							pac.setDirection(2);
                    	}
                    	else{
                    		destino = g.getVertex( pac.getOrigen().getIntName() + 25 );

                    		if( destino != null && pac.getOrigen().getNeighbors().contains(destino)){
                    		pac.setEstado("LEFT");
                    		//rGhost.setEstado("LEFT");
                    		done = true;
							pac.setDirection(2);
                    		}
                    	}

					}
					break;
					case RIGHT:
					{
                    	destino = g.getVertex( pac.getOrigen().getIntName() + 1 );
                    	if( destino != null && pac.getOrigen().getNeighbors().contains(destino)){
                    		pac.setEstado("RIGHT");
                    		//rGhost.setEstado("RIGHT");
                    		done = true;
							pac.setDirection(3);
                    	}
                    	else{
                    		destino = g.getVertex( pac.getOrigen().getIntName() - 25 );

                    		if( destino != null && pac.getOrigen().getNeighbors().contains(destino)){
                    		pac.setEstado("LEFT");
                    		//rGhost.setEstado("LEFT");
                    		done = true;
							pac.setDirection(3);
                    		}
                    	}
					}
					break;
					case ESCAPE:
					{
	          			if(root.getChildren().contains(pac.getOrigen().getCircle()))
	          				root.getChildren().remove(pac.getOrigen().getCircle());
	          			//admon.setNodos(this.root.getChildren());
					}
					break;

				}
				if(done){
					
					pac.getImageV().setX(destino.getX()-6);
					pac.getImageV().setY(destino.getY()-6);
					origen.getCircle().setRadius(1);
					destino.getCircle().setRadius(3);
					pac.setOrigen( destino );
					pac.isChase();
					if(pac.getChase() == true){
						blinky.setFrightened(true);
						inky.setFrightened(true);
						pinky.setFrightened(true);
						clyde.setFrightened(true);
					}
					long current = System.currentTimeMillis();
					pac.endChase(current);
					
					if(start == 0){
						Thread g1 = new Thread(inky);
						g1.start();
						start--;
					}else if(start == 2){
						Thread g2 = new Thread(blinky);
						g2.start();
						start--;
					}else if(start == 4){
						Thread g3 = new Thread(pinky);
						g3.start();
						start--;
					}else if(start == 6){
						Thread g4 = new Thread(clyde);
						g4.start();
						start--;
					}else if(start >= 8){
						blinky.obtenerMovimineto(pac.getOrigen());
						inky.obtenerMovimineto(pac.getOrigen());
						pinky.obtenerMovimineto(pac.getOrigen());
						clyde.obtenerMovimineto(pac.getOrigen());
						start--;
					}else{
						start--;
						/*if(pac.getStatus() == false){
							System.out.println("GAME OVER");
							pac.setEstado("DIE");
						}*/
					}
				
					if(root.getChildren().contains(destino.getCircle())){
						root.getChildren().remove(destino.getCircle());
					}
				}
			}
		});
		
		//addObjects( new ImageView(new Image( new File("./imagenes/Tablero0.png").toURI().toString())));
		addObjects();
		addObjects( this.pac.getImageV() );
		addObjects( this.blinky.getImageV() );
		addObjects( this.inky.getImageV() );
		addObjects( this.pinky.getImageV() );
		addObjects( this.clyde.getImageV() );

		//g.goToVertex("225","100");

		window.setScene(principal);
		window.show();
		pac.start();
		blinky.start();
		inky.start();
		pinky.start();
		clyde.start();

	}

	private void addObjects( Node pp){
		this.root.getChildren().add(pp);
	}

	private void addObjects(){
		for(Vertex v : this.g.getMap().values()){
			this.root.getChildren().add( v.getCircle() );
		}
	}

	public static void main(String [] args){
		//PacManScene p = new PacManScene();
		launch(args);

	}

}
