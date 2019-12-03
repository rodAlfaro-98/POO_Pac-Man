
package unam.fi.poo.interfaces;

import javafx.scene.Scene;
import java.util.Stack;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.estructuras.Plano;
import unam.fi.poo.objetos.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;

public abstract class Ghost{

	public static final int FPS = 4; 				// Cuadros por segundo
	public static final int FRAMES = 2;				// 2 cuadros de animacion
	public static final int VELOCIDAD = 110; 		// 110 milisegundos por vertice
	public static final int VELOCIDAD_HOME = 60;	// 60 milisegundos por vertice
	public static final int TIME_OF_FEAR = 9;		// 9 segundos de miedo

	public String nombre;
	public String orientacion = "RIGHT";
	public String pacOrientacion = "RIGHT";
	public String state;
	public Vertex startVertex, initVertex, endVertex, pacVertex;
	public Stack<String> stackPath;
	public static int blinkyLenPath;
	public static String blinkyVertex;
	public TranslateTransition tTransition;
	public AnimationTimer aTimer;
	public ImageView imageV;
	public Sprite sprite;
	public Plano plano;
	public long lastTime;
	public static boolean isPacManAlive = false;
	public boolean firstTime = true;
	public double origenX = 0.0, origenY = 0.0;
	public double destinoX = 0.0, destinoY = 0.0;
	public Stack<String> movimientos;
	public int movement = 0;
	public long timer;
	public boolean scatter;
	public Stack<String> scatterPath;

	/**
	* @brief Funciones abstractas.
	*/
	public abstract void scatter();
	public abstract void playOnChasePath();

	/**
	* @brief Función que detiene el hilo principal de animación.
	*/
	public void stopTimer(){
		this.aTimer.stop();
	}

	/**
	* @brief Función que activa el hilo principal de animación.
	*/
	public void startTimer(){
		this.aTimer.start();
	}

	/**
	* @brief Función que resetea al fantasma a su posición inicial.
	*/
	public void restart(){
		
		this.scatter = false;
		this.firstTime = true;
		this.state = "WAIT";
		this.orientacion = "RIGHT";
		this.initVertex = this.startVertex;
		this.endVertex = this.startVertex;
		this.imageV.relocate(
			initVertex.getX()-6, initVertex.getY()-6 );
		this.imageV.setVisible(true);
		this.tTransition.setDuration( Duration.millis( VELOCIDAD ) );
	}

	/**
	* @brief Función que detiene el desplazamiento del fantasma.
	*/
	public void stopTransition(){
		this.tTransition.stop();
		this.isPacManAlive = false;
		this.state = this.nombre;
	}

	/**
	* @brief Función que pausa el desplazamiento del fantasma.
	*/
	public void pauseTransition(){
		this.tTransition.pause();
	}

	/**
	* @brief Función que activa el desplazamiento del fantasma.
	*/
	public void playTransition(){
		this.tTransition.play();
	}

	/**
	* @brief Función para recoleccion de datos.
	* @param vp de tipo Vertex. Es el vértice donde se encuentra PacMan.
	*/
	public void setPacManVertex( Vertex vp ){
		this.pacVertex = vp;
	}

	/**
	* @brief Función para recoleccion de datos.
	* @param str de tipo String. Es la orientacion del PacMan.
	*/
	public void setPacManOrientation( String str ){
		this.pacOrientacion = str;
	}

	/**
	* @brief Función para recoleccion de datos.
	* @param b de tipo boolean. Es el estado del PacMan. True = Vivo.
	*/
	public void setPacManState( boolean b ){
		this.isPacManAlive = b;
	}

	/**
	* @brief Función que verifica si es tiempo de entrar a modo Scatter.
	*/
	public void seeIfScatter(){
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
	
	/**
	* @brief Función que reproduce un movimiento aleatorio cuanto el estado del fantasma es FEAR.
	*/
	public void playOnFearPath(){

		if( this.firstTime ){
			
			this.stackPath = plano.getPathTo(
				this.initVertex, plano.getRandomVertex() );

			this.firstTime = false;
		}

		if( !this.stackPath.isEmpty() ){
			if( this.stackPath.peek() != null ){
				this.endVertex = this.plano.getVertex( this.stackPath.pop() );
			}
		}
		else{
			this.firstTime = true;
		}
		moveGhost();
	}
	
	/**
	* @brief Función que lleva al fantasma de regreso a la casa. Estado HOME.
	*/
	public void playToHomePath(){

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
			this.state = "WAIT";
			this.tTransition.setDuration( Duration.millis( VELOCIDAD ) );
		}
	
		moveGhost();
	}

	/**
	* @brief Función que espera cierto tiempo antes de que el fantasma salga de la casa. Estado WAIT.
	*/
	public void playWaitPath( long timeTotal ){

		if( this.firstTime ){
			this.timer = System.currentTimeMillis();
			this.firstTime = false;
		}

		long time = System.currentTimeMillis() - this.timer;
		
		if( time < timeTotal ){
			if( (time/300) % 2 == 0 )
				this.endVertex = this.plano.getNextVertex( this.startVertex, 1, "DOWN", true );
			else{
				this.endVertex = this.plano.getNextVertex( this.startVertex, 1, "UP", true );
			}
			moveGhost();
		}
		else{
			this.firstTime = true;
			this.state = this.nombre;
		}
	}
	
	/**
	* @brief Función que actualiza la posición del fantasma.
	*/
	public void moveGhost(){
		try{
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
		} catch( NullPointerException ne ){ }
	}
	
	/**
	* @brief Función que establece la imagen del fantsma de acuerdo a su estado.
	*/
	public void setImageOrientation( long now ){
		
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
		//this.imageV.toFront();
	}
	
	/**
	* @brief Función que establece la orientacion del fantasma.
	*/
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
	
	/**
	* @brief Función que establece la posición del fantasma antes de ser desplazado.
	*/
	public void setOrigen(){
		this.origenX = this.imageV.getBoundsInParent().getMinX();
		this.origenY = this.imageV.getBoundsInParent().getMinY();
	}
	
	/**
	* @brief Función que compara el estado del fantasma.
	* @return True si el estado es FEAR.
	*/
	public boolean isFear(){
		return this.state == "FEAR" ;
	}

	/**
	* @brief Función que compara el estado del fantasma.
	* @return True si el estado es HOME.
	*/
	public boolean goingToHome(){
		return this.state == "HOME" ;
	}

	/**
	* @brief Función que compara el estado del fantasma.
	* @return True si el estado es WAIT.
	*/
	public boolean inHome(){
		return this.state == "WAIT";
	}

	/**
	* @brief Función que compara el estado del fantasma.
	* @return True si esta en modo SCATTER.
	*/
	public boolean isScatter(){
		return this.scatter;
	}

	/**
	* @brief Función Setter del modo Scatter.
	* @param sc de tipo boolean. 
	* @return True si el estado es FEAR.
	*/
	public void setScatter( boolean sc ){
		this.scatter = sc;
	}

	/**
	* @brief Función Setter de visibilidad de la imagen.
	* @param vs de tipo boolean. True = Visible.
	*/
	public void setVisible( boolean vs ){
		this.imageV.setVisible( vs );
	}

	/**
	* @brief Función Setter del estado.
	* @param st de tipo String. 
	*/
	public void setState( String st ){
		this.state = st;
		this.lastTime = System.nanoTime();
		this.firstTime = true;
	}
	
	/**
	* @brief Función Getter de los limites de la imagen.
	* @return Los límites de la imagen.
	*/
	public Bounds getBounds(){
		return this.imageV.getBoundsInParent();
	}

	/**
	* @brief Función Getter de la imagen.
	* @return La imagen a dibujar.
	*/
	public ImageView getSprite(){
		return this.imageV;
	}

}
