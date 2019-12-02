
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

public abstract class GhostClass{

	public static final int FPS = 4; 		// Cuadros por segundo
	public static final int FRAMES = 2;	// 2 cuadros de animacion
	public static final int VELOCIDAD = 110; // 110 milisegundos por vertice
	public static final int VELOCIDAD_HOME = 60; // 110 milisegundos por vertice
	public static final int TIME_OF_FEAR = 9;  // 9 segundos de miedo

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

	public abstract void scatter();
	public abstract void playOnChasePath();

	/**
	* @brief Función que frena el hilo de la animación de pacman
	*/
	public void stop(){
		this.aTimer.stop();
	}

	/**
	* @brief Función que inicia el hilo de la animación de pacman
	*/
	public void start(){
		this.aTimer.start();
	}

	/**
	* @Función que reinicia el juego, lo que hace es regresar al fantasma a su estado y posición de antes de iniciar el juego
	*/
	public void restart(){

		this.scatter = false;
		this.state = this.nombre;
		this.orientacion = "RIGHT";	
		//Hacemos que tanto el vértice de origen como el de destino sean iguales al vértice del inicio del juego
		this.initVertex = this.startVertex;
		this.endVertex = this.startVertex;
		this.imageV.relocate(
			initVertex.getX()-6, initVertex.getY()-6 );
		this.imageV.setVisible(true);
		this.tTransition.setDuration( Duration.millis( VELOCIDAD ) );
		
	}

	/**
	* @brief Función que detiene el hilo del fantasma
	*/
	public void stopGhost(){
		this.isPacManAlive = false;
		this.tTransition.setOnFinished( new EventHandler<ActionEvent>(){
			
			public void handle( ActionEvent e ){
				tTransition.stop();
			}
		});
		this.state = this.nombre;
	}

	/**
	* @brief Función que pone en pausa el hilo del fantasma
	*/
	public void pause(){
		this.tTransition.pause();
	}

	/**
	* @brief Función que reanuda el hilo del fantasma
	*/
	public void play(){
		this.tTransition.play();
	}

	/**
	* @brief Actualiza los datos de localización del pacman
	* @param Un objeto de tipo Vertex que es el lugar donde se encuentra el pacman al instante
	*/
	//Métodos Obtención datos Pac-Man
	public void setPacManVertex( Vertex vp ){
		this.pacVertex = vp;
	}

	/**
	* @brief Actualiza los datos de la orientación del pacman
	* @param Un objeto de tipo String que es la orientación en tiempo real de pacman
	*/
	public void setPacManOrientation( String str ){
		this.pacOrientacion = str;
	}

	/**
	* @brief Actualiza los datos de la orientación del pacman
	* @param Un objeto de tipo String que es la orientación en tiempo real de pacman
	*/
	public void setPacManState( boolean b ){
		this.isPacManAlive = b;
	}

	/**
	* @brief Función que revisa si es momento de cambiar el estado del fantasma de Chase a Scatter
	*/
	public void seeIfScatter(){
		long now = System.currentTimeMillis();
				
		//Esta parte fue tomada del pacman original, cada 20 segundos cambiaba de Chase a Scatter
		if(this.scatter == false && (now - this.timer) >= 20000){
			this.scatter = true;
			//this.state = "SCATTER";
			this.timer = System.currentTimeMillis();
		}
		//Y después de 7 segundos regresaba a chase
		if(this.scatter == true && (now - this.timer) >= 7000){
			this.scatter = false;
			this.state = this.nombre;
			this.timer = System.currentTimeMillis();
		}
		
	}
	
	/**
	* @brief Función que indica al fantasma como huir de pacman
	*/
	public void playOnFearPath(){

		
		if( this.firstTime ){
			
			//Se generará un camino que va desde el lugar actual hasta uno al azar
			this.stackPath = plano.getPathTo(
				this.initVertex, plano.getRandomVertex() );

			this.firstTime = false;
		}
		
		if( !this.stackPath.isEmpty() ){ //En caso de que la pila no esté vacía el siguiente vertice se sacara de la pila
			if( this.stackPath.peek() != null ){
				this.endVertex = this.plano.getVertex( this.stackPath.pop() );
			}else{
				this.firstTime = true;
			}
		}
		else{ //En caso de que se vacie la pilase regresa a la primera condicional
			this.firstTime = true;
		}
		moveGhost();
	}
	
	/**
	* @brief Funcion que lleva al fantasma recién comido a su punto de partida
	*/
	public void playToHomePath(){

		if( this.firstTime ){
			
			this.scatter = false;
			this.timer = System.currentTimeMillis();
			
			this.tTransition.setDuration( Duration.millis( VELOCIDAD_HOME ) );
			
			//Generamos un camino desde el vértice inicio hasta el de partida
			this.stackPath = plano.getPathTo(
				this.initVertex, this.startVertex );
				
			this.movimientos = this.plano.getPathTo(initVertex, startVertex);
			
			this.firstTime = false;
		}

		//Vamos sacando los elementos del camino recién generado
		if( !this.stackPath.isEmpty() ){
			if( this.stackPath.peek() != null )
				this.endVertex = this.plano.getVertex( this.stackPath.pop() );
		}else{
			this.firstTime = true;
		}

		//En caso de que llegue al punto de partida terminamos el proceso
		if( this.endVertex == this.startVertex ){
			this.state = this.nombre;
			this.tTransition.setDuration( Duration.millis( VELOCIDAD ) );
		}
	
		moveGhost();
	}
	
	/**
	* @brief Funcion que mueve al fantasma a lo largo del tablero
	*/
	public void moveGhost(){

		//Método actualización del Sprite
		//Se actualiza la coordenada en X donde se encontrará la imagen en función a la coordenada en X del vértice
		this.imageV.setLayoutX( this.endVertex.getX() - 6 - 
			this.imageV.getLayoutBounds().getMinX() );
		
		//Se actualiza la coordenada en Y donde se encontrará la imagen en función a la coordenada en Y del vértice
		this.imageV.setLayoutY(	this.endVertex.getY() - 6 - 
			this.imageV.getLayoutBounds().getMinY() );

		//Al objeto jTransition se le indica de donde a donde será el movimiento
		this.tTransition.setByX( this.imageV.getLayoutX() );
		this.tTransition.setByY( this.imageV.getLayoutY() );

		this.tTransition.setToX( this.imageV.getTranslateX() );
		this.tTransition.setToY( this.imageV.getTranslateY() );

		this.destinoX = this.imageV.getBoundsInParent().getMinX();
		this.destinoY = this.imageV.getBoundsInParent().getMinY();
			
		//Actualizamos a que el nodo actual sea el de llegada
		this.initVertex = this.endVertex;

		//Iniciamos la animación
		this.tTransition.play();
	}
	
	/**
	* @brief Función que indica la orientación de la imagen en función del estado del fantasma
	* @param El tiempo actual en milisegundos
	*/
	public void setImageOrientation( long now ){
		
		//Relativo al estado de huir de pacman (imagen a usar fantasmas azules)
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
		}//Relativo al estado de regresar al punto de origen (imagen a usar ojos flotando)
		else if( this.state == "HOME" ){
			this.imageV.setImage( this.sprite.getImageEyes( this.orientacion ) );
		}
		else{//Relativo al estado base del fantasma (imagen a usar depende del fantasma)
			this.imageV.setImage( this.sprite.getImage( this.orientacion, now ) );
		}

		this.imageV.toFront();
	}
	
	/**
	* @Funcion que indica hacia donde esta viendo el fantasma para elegin la imagen adecuada de movimiento
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
	* @brief Funcion que nos retorna donde está el origen del pacman. Función obsoleta en versión actual, se deja únicamente por si en un futuro se requiere de su implementación 
	*/
	public void setOrigen(){
		this.origenX = this.imageV.getBoundsInParent().getMinX();
		this.origenY = this.imageV.getBoundsInParent().getMinY();
	}
	
	/**
	* @brief
	* @param
	*/
	public boolean isFear(){
		return this.state == "FEAR" ;
	}

	public boolean goingToHome(){
		return this.state == "HOME" ;
	}

	public boolean isScatter(){
		return this.scatter;
	}

	public void setScatter( boolean sc ){
		this.scatter = sc;
	}

	public void setVisible( boolean vs ){
		this.imageV.setVisible( vs );
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

}
