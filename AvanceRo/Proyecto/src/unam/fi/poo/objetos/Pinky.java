package unam.fi.poo.objetos;

import javafx.scene.Scene;
import java.util.Stack;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.estructuras.Plano;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;

public class Pinky extends AnimationTimer implements Ghost{

	private static final int FPS = 4; 		// Cuadros por segundo
	private static final int FRAMES = 2;	// 2 cuadros de animacion
	private static final int VELOCIDAD = 110; // 110 milisegundos por vertice
	private static final int VELOCIDAD_HOME = 60; // 110 milisegundos por vertice
	private static final int TIME_OF_FEAR = 9;  // 9 segundos de miedo
	private String nombre = "PINKY";
	private String orientacion = "RIGHT";
	private String pacOrientacion = "RIGHT";
	private String state;
	private Vertex startVertex, initVertex, endVertex, pacVertex;
	private Stack<String> stackPath;
	private static int blinkyLenPath;
	private static String blinkyVertex;
	private TranslateTransition tTransition;
	private ImageView imageV;
	private Sprite sprite;
	private Plano plano;
	private long lastTime;
	private static boolean isPacManAlive = false;
	private boolean firstTime = true;
	private double origenX = 0.0, origenY = 0.0;
	private double destinoX = 0.0, destinoY = 0.0;
	private Stack<String> movimientos;
	private int movement = 0;
	private long timer;
	private boolean scatter;
	private Stack<String> scatterPath;

	//Constructor
	public Pinky( Plano g, String v0 ){
		
		this.sprite = new Sprite( this.nombre, FRAMES, 4, FPS );
		this.plano = g;
		this.startVertex = g.getVertex(v0);
		this.initVertex = g.getVertex(v0);
		this.endVertex = g.getVertex(v0);
		this.stackPath = new Stack<String>();

		this.imageV = new ImageView();
		this.imageV.setX( initVertex.getX()-6 );
		this.imageV.setY( initVertex.getY()-6 );

		this.tTransition = new TranslateTransition();
		this.tTransition.setDuration( new Duration( VELOCIDAD ) );
		this.tTransition.setNode( this.imageV );
		this.tTransition.setCycleCount(1);
		this.state = this.nombre;
		
		this.movimientos = new Stack<String>();
		this.timer=System.currentTimeMillis();
		this.scatter=false;
		this.scatterPath = new Stack<String>();
		
	}
	
	//Métodos de AnimationTimer
	public void restart(){
		
		this.initVertex = this.startVertex;
		this.endVertex = this.startVertex;
		this.imageV.relocate(
			initVertex.getX()-6, initVertex.getY()-6 );
		this.imageV.setVisible(true);
		
	}
	
	public void stop(){
		this.isPacManAlive = false;
		this.tTransition.setOnFinished( new EventHandler<ActionEvent>(){
			
			public void handle( ActionEvent e ){
				tTransition.stop();
			}
		});
		this.imageV.setVisible(false);
	}
	
	public void pause(){
		this.tTransition.pause();
	}

	//Métodos Obtención datos Pac-Man
	public void setPacManVertex( Vertex vp ){
		this.pacVertex = vp;
	}

	public void setPacManOrientation( String str ){
		this.pacOrientacion = str;
	}

	public void setPacManState( boolean b ){
		this.isPacManAlive = b;
	}
	
	//Métodos movimiento del fantasma	
	private void seeIfScatter(){
		long now = System.currentTimeMillis();
				
		if(this.scatter == false && (now - this.timer) >= 20000){
			this.scatter = true;
			this.timer = System.currentTimeMillis();
		}
		if(this.scatter == true && (now - this.timer) >= 7000){
			this.scatter = false;
			this.timer = System.currentTimeMillis();
		}
		
	}
	
	private void playOnFearPath(){

		if( this.firstTime ){
			
			this.stackPath = plano.getPathTo(
				this.initVertex, plano.getRandomVertex() );

			this.firstTime = false;
		}

		if( !this.stackPath.isEmpty() ){
			if( this.stackPath.peek() != null )
				this.endVertex = this.plano.getVertex( this.stackPath.pop() );
		}
		else{
			this.firstTime = true;
		}
		moveGhost();
	}
	
	private void playToHomePath(){

		if( this.firstTime ){
			
			this.scatter = false;
			this.timer = System.currentTimeMillis();
			
			this.tTransition.setDuration( Duration.millis( VELOCIDAD_HOME ) );
			
			this.stackPath = plano.getPathTo(
				this.initVertex, this.startVertex );
				
			this.movimientos = this.plano.getPathTo(initVertex, startVertex);
			
			this.firstTime = false;
		}

		if( !this.stackPath.isEmpty() ){
			if( this.stackPath.peek() != null )
				this.endVertex = this.plano.getVertex( this.stackPath.pop() );
		}else{
			this.firstTime = true;
		}

		if( this.endVertex == this.startVertex ){
			this.state = this.nombre;
			this.tTransition.setDuration( Duration.millis( VELOCIDAD ) );
		}
	
		moveGhost();
	}

	public void scatter(){
		
		if((System.currentTimeMillis() - this.timer) <5 || this.initVertex.getName().equals("26")){
			this.scatterPath = this.plano.getPathTo(this.plano.goToNextVertexInPath(this.initVertex, 
			this.plano.getVertex("119")),this.plano.getVertex("119"));
		}else if(this.initVertex.getName().equals("119")){
			this.scatterPath = this.plano.getPathTo(this.plano.goToNextVertexInPath(this.initVertex, 
			this.plano.getVertex("26")),this.plano.getVertex("26"));
		}
		this.endVertex = this.plano.getVertex(this.scatterPath.pop());
		moveGhost();
		
	}
	
	public void playOnChasePath(){
		
		Vertex tile = plano.getNextVertex(
			this.pacVertex, 4, this.pacOrientacion, false );

		if( tile != null ){
			this.endVertex = tile;
		}
		else{
			this.endVertex = plano.getNextVertex(
				this.pacVertex, 3, this.pacOrientacion, true );
		}

		this.endVertex = this.plano.goToNextVertexInPath(
			this.initVertex, this.pacVertex );
		
		moveGhost();
		
	}
	
	//Método actualización del Sprite
	private void moveGhost(){

		this.imageV.setLayoutX( this.endVertex.getX() - 6 - 
			this.imageV.getLayoutBounds().getMinX() );

		this.imageV.setLayoutY(	this.endVertex.getY() - 6 - 
			this.imageV.getLayoutBounds().getMinY() );

		this.tTransition.setByX( this.imageV.getLayoutX() );
		this.tTransition.setByY( this.imageV.getLayoutY() );

		this.tTransition.setToX( this.imageV.getTranslateX() );
		this.tTransition.setToY( this.imageV.getTranslateY() );

		this.destinoX = this.imageV.getBoundsInParent().getMinX();
		this.destinoY = this.imageV.getBoundsInParent().getMinY();
			
		this.initVertex = this.endVertex;

		this.tTransition.play();
	}
	
	private void setImageOrientation( long now ){
		
		if( this.state == "FEAR" ){

			int sec = (int) Math.floor(( now - this.lastTime) / (1000000000));
			
			if( sec < (TIME_OF_FEAR - 3) ){
				this.imageV.setImage( this.sprite.getImageFearBlue( sec ) );
			}
			else if ( sec < TIME_OF_FEAR ){
				this.imageV.setImage( this.sprite.getImage( "FEAR", now ) );
			}
			else{
				this.state = this.nombre;
			}
		}
		else if( this.state == "HOME" ){
			this.imageV.setImage( this.sprite.getImageEyes( this.orientacion ) );
		}
		else{
			this.imageV.setImage( this.sprite.getImage( this.orientacion, now ) );
		}
	}
	
	public void setOrientacion(){
		
		if( origenY == destinoY ){
			if( ( origenX - destinoX ) > 0 ){
				this.orientacion = "LEFT";
			}else{
				this.orientacion = "RIGHT";
			}
		}
		else if( (origenY - destinoY ) < 0 ){
			this.orientacion = "DOWN";
		}
		else{
			this.orientacion = "UP";
		}
		
	}
	
	private void setOrigen(){
		this.origenX = this.imageV.getBoundsInParent().getMinX();
		this.origenY = this.imageV.getBoundsInParent().getMinY();
	}
	
	public boolean isFear(){
		return this.state == "FEAR" ;
	}

	public boolean isToHome(){
		return this.state == "HOME" ;
	}

	public void setState( String st ){
		this.state = st;
		this.lastTime = System.nanoTime();
		this.firstTime = true;
	}
	
	public Bounds getBounds(){
		return this.imageV.getBoundsInParent();
	}

	public ImageView getSprite(){
		return this.imageV;
	}
	
	//Método manejador de todos los eventos
	public void handle( long now ){

		if( this.isPacManAlive ){
			if( this.tTransition.getStatus() == Status.STOPPED ){
				
				setOrigen();
				
				if(!this.state.equals("FEAR") && !this.state.equals("HOME"));
					seeIfScatter();
				
				if(scatter){
					scatter();
				}else{
				
					switch( this.state ){
						case "PINKY":
							playOnChasePath(); break;
						case "FEAR":
							playOnFearPath(); break;
						case "HOME":
							playToHomePath(); break;
					}
				}
				setOrientacion();
				setImageOrientation( now );
			}
		}
		setImageOrientation( now );
	}

}