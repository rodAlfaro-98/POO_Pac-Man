
package unam.fi.poo.objetos;

import java.io.File;
import java.util.ArrayList;
import java.util.ArrayDeque;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.animation.Animation.Status;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;

import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.estructuras.Plano;

import unam.fi.poo.controles.Grupo;
import unam.fi.poo.interfaces.Ghost;

public class PacMan extends AnimationTimer{
	
	private static final int fps = 11,
		numFrames = 4, velocidad = 90;
	private int puntaje = 0, comida = 244, vidas = 3, nivel = 1;
	private long time = 0;
	private int cnt = 0;
	private String orientacion = "RIGHT",
					nextOrientacion = "RIGHT";
	private Vertex startVertex, initVertex, endVertex;
	private ImageView imageV, messageIV;
	private Sprite sprite;
	private Plano plano;
	private ArrayList<Ghost> fantasmas;
	private ArrayList<ImageView> lives;
	private boolean isAlive = false, bandera = true, isDead = false;
	//private Group root;
	private Grupo root;
	private Text score, level;
	private TranslateTransition tt;

	/**
	* @brief Constructor de la clase PacMan.
	* @param name de tipo String. Es el nombre que se usará para identificar las imágenes del sprite.
	* @param g de tipo Plano.
	* @param v0 de tipo Vertex. Es el vértice origen.
	*/
	public PacMan( String name, Plano g, String v0 ){
			
		this.sprite = new Sprite( name, numFrames, 11, fps );
		this.plano = g;
		this.startVertex = g.getVertex(v0);
		this.initVertex = g.getVertex(v0);
		this.endVertex = g.getVertex(v0);
		this.imageV = new ImageView();
		this.messageIV = new ImageView();
		this.fantasmas = new ArrayList<Ghost>();
		this.lives = new ArrayList<ImageView>();

		this.tt = new TranslateTransition();
		this.tt.setDuration( new Duration( velocidad ) );
		this.tt.setNode( this.imageV );
		this.tt.setCycleCount(1);
		
		this.imageV.setX( this.initVertex.getX() - 6 );
		this.imageV.setY( this.initVertex.getY() - 6 );

		setComplemets();

	}

	/**
	* @brief Función que agrega componentes gŕaficos extra a la ventana.
	*/
	private void setComplemets(){
		double x = 207, y = 252;

		this.score = new Text("Score ");
		this.score.setFont( new Font("Purisa",16) );
		this.score.setX( 0 ); this.score.setY( 266 );
		this.score.setFill( Color.YELLOW );

		this.level = new Text("L1");
		this.level.setFont( new Font("Purisa", 16) );
		this.level.setX( 130 ); this.level.setY( 266 );
		this.level.setFill( Color.YELLOW );

		this.messageIV.setImage( this.sprite.getReadyImage() );
		this.messageIV.setX(90); this.messageIV.setY(137);

		for( int i = 0; i < vidas; ++i ){
			ImageView imv = new ImageView();
			imv.setImage( this.sprite.getLiveImage() );
			imv.setX(x); imv.setY(y);
			this.lives.add( imv );
			x -= 16;
		}
	}

	/**
	* @brief Función Setter que estable la orientación del PacMan.
	* @param orient de tipo String. Es la nueva orientación.
	*/
	public void setOrientacion( String orient ){
		this.nextOrientacion = orient;
	}

	/**
	* @brief Función Setter que establece la raiz donde estarán los objetos.
	* @param root de tipo Grupo.
	*/
	public void setRoot( Grupo root ){
		
		this.root = root;
		
		for( Ghost g : this.fantasmas ){
			this.root.getChildren().add( g.getSprite() );
		}
		
		for( ImageView iv : this.lives ){
			this.root.getChildren().add( iv );
		}

		this.root.getChildren().add( this.score );
		this.root.getChildren().add( this.level );
		this.root.getChildren().add( this.imageV );
		this.root.getChildren().add( this.messageIV );
	}

	/**
	* @brief Función de activación para iniciar el juego.
	*/
	public void setIsAlive(){
		if( this.vidas >= 0 && !this.isDead && !this.isAlive ){
			this.messageIV.setVisible(false);
			this.isAlive = true;
			this.isDead = false;
			this.bandera = true;
		}
	}

	/**
	* @brief Función que pone en pausa el movimiento de todos los personajes.
	*/
	public void pause(){
		for( Ghost g : fantasmas )
			g.pauseTransition();
		this.tt.pause();
	}

	/**
	* @brief Función que activa el movimiento de todos los personajes.
	*/
	public void play(){
		this.tt.play();
		for( Ghost g : fantasmas )
			g.playTransition();
	}

	/**
	* @brief Función agrega un nuevo fantasma.
	* @param gh de tipo Ghost.
	*/
	public void addGhost( Ghost gh){
		this.fantasmas.add( gh );
	}

	/**
	* @brief Fución que incrementa el puntaje.
	* @param s de tipo entero. Son los puntos a sumar.
	*/
	private void updateScore( int s ){
		this.puntaje += s ;
		this.score.setText("Score "+ this.puntaje);
	}

	/**
	* @brief Función que detiene a los fantasmas e indica que se subió de nivel.
	*/
	private void win(){

		this.isAlive = false;

		for( Ghost g : fantasmas )
			g.stopTransition();
		++this.nivel;
	}

	/**
	* @brief Función que detiene a los fantasmas e indica que el PacMan murió.
	*/
	private void die(){

		this.isAlive = false;
		this.isDead = true;
		this.time = System.nanoTime();

		for( Ghost gg : fantasmas ){
			gg.stopTransition();
			gg.setVisible(false);
		}
	}

	/**
	* @brief Fución que reinicia la escena agregando todas las comidas.
	*/
	public void restartScene(){
		for(Vertex v : this.plano.getMap().values()){
			Circle c = v.getCircle();
			if( c.getRadius() > 0 ){
				c.setVisible(true);
			}
		}
		this.comida = 244;
	}

	/**
	* @brief Función que reinicia las vidas y el puntaje.
	*/
	public void restartLives(){
		this.vidas = 3;

		for( ImageView iv: lives )
			iv.setVisible(true);
		
		this.puntaje = 0;
		this.score.setText("Score ");
	}

	/**
	* @brief Función que reicicia al PacMan y a los fantasmas.
	*/
	public void restart(){
		this.isDead = false;
		this.initVertex = this.startVertex;
		this.endVertex = this.startVertex;

		for(Ghost g : fantasmas ){
			g.restart();
		}

		this.imageV.relocate(
			this.initVertex.getX() - 6, this.initVertex.getY() - 6 );

		this.orientacion = "RIGHT";
		this.nextOrientacion = "RIGHT";

		this.imageV.setVisible(true);
		this.level.setText("L"+ this.nivel );
		this.messageIV.setImage( this.sprite.getReadyImage() );
		this.messageIV.setVisible(true);
		this.messageIV.setX(90);
	}

	/**
	* @brief Función que actualiza la posición del PacMan.
	**/
	private void moveTo(){

		if( plano.existNextVertex(
				this.initVertex, this.nextOrientacion ) ){
			this.orientacion = this.nextOrientacion;
		}

		this.endVertex = plano.getNextVertex(
			this.initVertex, 1, this.orientacion, true );
			
		this.imageV.setLayoutX(
			this.endVertex.getX() - 6 - this.imageV.getLayoutBounds().getMinX() );

		this.imageV.setLayoutY(
			this.endVertex.getY() - 6 - this.imageV.getLayoutBounds().getMinY() );

		this.tt.setByX( this.imageV.getLayoutX() );
		this.tt.setByY( this.imageV.getLayoutY() );

		this.tt.setToX( this.imageV.getTranslateX() );
		this.tt.setToY( this.imageV.getTranslateY() );
			
		this.initVertex = this.endVertex;

		this.tt.play();

		Circle eat = this.endVertex.getCircle();

		if( eat.isVisible() ){
			
			eat.setVisible(false);
			--this.comida;
			
			if( eat.getRadius() == 1){
				this.updateScore( 10 );
			}

			else if( eat.getRadius() > 1 ){
				for( Ghost g : fantasmas )
					if( !g.goingToHome() )
						g.setState("FEAR");
				this.updateScore( 10 );
			}
		}
		else if( this.comida == 0){
			win();
		}
	}

	/**
	* @brief Función principal de ejecución.
	*/
	public void handle( long now ){

		if( this.isAlive ){

			this.imageV.setImage( 
				this.sprite.getImage( this.orientacion, now ) );
			
			for( Ghost g : fantasmas ){

				g.setPacManState(this.isAlive);
				g.setPacManVertex( this.initVertex );
				g.setPacManOrientation( this.orientacion );

				if( this.imageV.getBoundsInParent().intersects( g.getBounds() ) ){
					if( g.isFear() ){
						g.setScatter(false);
						g.setState("HOME");
						this.updateScore( 200 );
					}
					else if( !g.goingToHome() ){
						die(); break;
					}
				}
			}
			if( this.tt.getStatus() == Status.STOPPED )
				moveTo();
		}

		//Sube de nivel
		else if( this.vidas >= 0 && this.comida == 0 ){

			try{ Thread.sleep(2000); }
			catch( Exception e ){ }

			this.restartScene();
			this.restart();
		}

		//Perdio todas las vidas
		else if( this.vidas < 0 && this.bandera ){
			this.bandera = false;

			for( Ghost g : fantasmas ){
				g.stopTransition();
			}

			try{ Thread.sleep(2000); }
			catch( Exception e ){ }

			this.root.getManejadorEventos().getApp().getScoreGrid().setScore( this.puntaje );
			this.restartLives();
			this.restartScene();
			this.restart();
			this.root.getManejadorEventos().setScoreScene();
		}

		//Secuencia de explosión
		else if( this.isDead ){
			int num = (int)( now - this.time)/ (1000000000/this.fps);
			
			if( num < this.fps){
				this.imageV.setImage(
					this.sprite.getDieImage( num ));
			}
			else{
				if( --this.vidas >= 0 ){
					this.lives.get( this.vidas ).setVisible(false);
					this.restart();
				}
				else{
					this.messageIV.setImage( this.sprite.getGameOverImage() );
					this.messageIV.setX(77);
					this.messageIV.setVisible(true);
				}
			}
		}
		//Secunecia de espera de inicio
		else{
			this.imageV.setImage( 
				this.sprite.getImage( "RIGHT", now ) );
		}
	}
}