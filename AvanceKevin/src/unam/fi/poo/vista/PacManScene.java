
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
	//private Graph g;
	private Grafo g;
	private int TAMX = 26, TAMY = 29;
	private final double x = 12, y = 12, r = 1, incX = 8, incY = 8;
	//private final double x = , y = 12, r = 1, incX = 20, incY = 20;

	private Vertex origen;
	private HashMap<String, Integer> nodos;
	private Administrador admon;

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
		this.origen = g.getVertex("586");
	}

	public void start(Stage primaryStage){

		Stage window = primaryStage;
		this.root = new Group();

		Sprite pac = new Sprite("./imagenes/PM_",4, false, "RIGHT", origen.getX(), origen.getY());	
		Sprite rGhost = new Sprite("./imagenes/GHR_",2, false, "RIGHT",origen.getX(), origen.getY());

		pac.getImageV().setX(origen.getX()-6);
		pac.getImageV().setY(origen.getY()-6);

		this.principal = new Scene(root, 224, 248 );
		//File f = new File("./imagenes/Tablero.png");
		this.principal.setFill( new ImagePattern( new Image( new File("./imagenes/Tablero0.png").toURI().toString())));
		//this.principal.setFill(Color.BLACK);
		
		this.principal.setOnKeyPressed( new EventHandler<KeyEvent>() {
			
			public void handle( KeyEvent e ){
				Vertex destino = new Vertex();
				boolean done = false;

				switch( e.getCode() ){
					case UP:
					{
                    	destino = g.getVertex( origen.getIntName() - TAMX );
                    	if( destino != null && origen.getNeighbors().contains(destino)){
                    		pac.setEstado("UP");
                    		//rGhost.setEstado("UP");
                    		done = true;
                    	}
					}
					break;
					case DOWN:
					{
                    	destino = g.getVertex( origen.getIntName() + TAMX );
                    	if( destino != null && origen.getNeighbors().contains(destino)){
                    		pac.setEstado("DOWN");
                    		//rGhost.setEstado("DOWN");
                    		done = true;
                    	}
					}
					break;
					case LEFT:
					{
                    	destino = g.getVertex( origen.getIntName() - 1 );
                    	if( destino != null && origen.getNeighbors().contains(destino)){
                    		pac.setEstado("LEFT");
                    		//rGhost.setEstado("LEFT");
                    		done = true;
                    	}
                    	else{
                    		destino = g.getVertex( origen.getIntName() + 25 );

                    		if( destino != null && origen.getNeighbors().contains(destino)){
                    		pac.setEstado("LEFT");
                    		//rGhost.setEstado("LEFT");
                    		done = true;
                    		}
                    	}

					}
					break;
					case RIGHT:
					{
                    	destino = g.getVertex( origen.getIntName() + 1 );
                    	if( destino != null && origen.getNeighbors().contains(destino)){
                    		pac.setEstado("RIGHT");
                    		//rGhost.setEstado("RIGHT");
                    		done = true;
                    	}
                    	else{
                    		destino = g.getVertex( origen.getIntName() - 25 );

                    		if( destino != null && origen.getNeighbors().contains(destino)){
                    		pac.setEstado("LEFT");
                    		//rGhost.setEstado("LEFT");
                    		done = true;
                    		}
                    	}
					}
					break;
					case ESCAPE:
					{
	          			System.out.println(origen.getName());
	          			//admon.setNodo(origen.getName());
	          			///if(root.getChildren().contains(origen.getCircle()))
	          				//root.getChildren().remove(origen.getCircle());
					}
					break;

				}
				if(done){
					
					pac.getImageV().setX(destino.getX()-6);
					pac.getImageV().setY(destino.getY()-6);
					origen.getCircle().setRadius(1);
					destino.getCircle().setRadius(3);
					origen = destino;
					
					if(root.getChildren().contains(destino.getCircle()))
	          			root.getChildren().remove(destino.getCircle());
				}
			}
		});
		
		//addObjects( new ImageView(new Image( new File("./imagenes/Tablero0.png").toURI().toString())));
		addObjects();
		addObjects( pac.getImageV() );
		//addObjects( rGhost.getImageV() );

		//g.goToVertex("225","100");

		window.setScene(principal);
		window.show();
		pac.start();
		//rGhost.start();

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
