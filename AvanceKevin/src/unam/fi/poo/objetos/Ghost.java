
package unam.fi.poo.objetos;

import javafx.scene.Scene;
import java.util.ArrayDeque;
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


public class Ghost extends AnimationTimer {
	
	private static final int FPS = 4; 		// Cuadros por segundo
	private static final int FRAMES = 2;	// 2 cuadros de animacion
	private static final int VELOCIDAD = 120; // 110 milisegundos por vertice
	private static final int VELOCIDAD_HOME = 60; // 110 milisegundos por vertice
	private static final int TIME_OF_FEAR = 9;  // 9 segundos de miedo
	private String nombre;
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


	public Ghost( String name, Plano g, String v0 ){
		this.nombre = name;
		this.sprite = new Sprite( name, FRAMES, 4, FPS );
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

	}

	public Bounds getBounds(){
		return this.imageV.getBoundsInParent();
	}

	public ImageView getSprite(){
		return this.imageV;
	}

	public void restart(){
		this.initVertex = this.startVertex;
		this.endVertex = this.startVertex;
		this.imageV.relocate(
			initVertex.getX()-6, initVertex.getY()-6 );
		this.imageV.setVisible(true);
		//this.stop();
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

	public void setPacManVertex( Vertex vp ){
		this.pacVertex = vp;
	}

	public void setPacManOrientation( String str ){
		this.pacOrientacion = str;
	}

	public void setPacManState( boolean b ){
		this.isPacManAlive = b;
	}

	private void setOrigen(){
		this.origenX = this.imageV.getBoundsInParent().getMinX();
		this.origenY = this.imageV.getBoundsInParent().getMinY();
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
			
			this.tTransition.setDuration( Duration.millis( VELOCIDAD_HOME ) );
			
			this.stackPath = plano.getPathTo(
				this.initVertex, this.startVertex );
			
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

	public void playBlinkyPath(){
		/*
		if( this.firstTime ){

			this.stackPath = this.plano.getPathTo(
				this.initVertex, this.pacVertex );

			this.blinkyLenPath = this.stackPath.size();

			this.firstTime = false;
		}
		*/
		this.endVertex = this.plano.goToNextVertexInPath(
			this.initVertex, this.pacVertex );

		this.blinkyLenPath = this.pacVertex.getDistance();
		this.blinkyVertex = this.initVertex.getName();

		//System.out.println("Blinky: "+ this.blinkyVertex );
		
		/*
		if( this.stackPath.size() > ( this.blinkyLenPath / 4) ){
			if( this.stackPath.peek() != null )
				this.endVertex = this.plano.getVertex( this.stackPath.pop() );
		}else{
			this.firstTime = true;
		}
		*/
		moveGhost();
	}

	public void playPinkyPath(){

		Vertex tile = plano.getNextVertex(
			this.pacVertex, 4, this.pacOrientacion, false );

		if( tile != null ){
			this.endVertex = tile;
		}
		else{
			this.endVertex = plano.getNextVertex(
				this.pacVertex, 3, this.pacOrientacion, true );
		}

		//this.stackPath = this.plano.getPathTo(
		//	this.initVertex, this.endVertex );
			
		//this.endVertex = plano.getVertex( this.stackPath.pop() );

		this.endVertex = this.plano.goToNextVertexInPath(
			this.initVertex, this.pacVertex );
		
		moveGhost();
	}

	public void playInkyPath(){

		//Pendiente

		Vertex tile = plano.getNextVertex(
			this.pacVertex, 2, this.pacOrientacion, true );

		//this.stackPath = this.plano.getDoublePathTo(
		//	this.initVertex, this.endVertex, this.blinkyLenPath );

		moveGhost();
	}

	public void playClydePath(){

		//Casi listo

		//this.stackPath = plano.getPathTo(
		//	this.initVertex, this.pacVertex );

		this.endVertex = this.plano.goToNextVertexInPath(
			this.initVertex, this.pacVertex );

		if( this.pacVertex.getDistance() <= 8 ){
			this.endVertex = this.plano.goToNextVertexInPath(
				this.initVertex, this.plano.getVertex("729") );
		}

		moveGhost();
	}

	public void handle( long now ){

		if( this.isPacManAlive ){
			if( this.tTransition.getStatus() == Status.STOPPED ){
				
				setOrigen();
				
				switch( this.state ){
					case "BLINKY":
						playBlinkyPath(); break;
					case "PINKY":
						playPinkyPath(); break;
					case "CLYDE":
						playClydePath(); break;
					case "INKY":
						playInkyPath(); break;
					case "FEAR":
						playOnFearPath(); break;
					case "HOME":
						playToHomePath(); break;
				}
				setOrientacion();
				setImageOrientation( now );
			}
		}
		setImageOrientation( now );
	}
}
