
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
	private ImageView imageV;
	private Sprite sprite;
	private Plano plano;
	private ArrayList<Ghost> fantasmas;
	private ArrayList<ImageView> lives;
	private boolean isAlive = false, bandera = true, isDead = false;
	//private Group root;
	private Grupo root;
	private Text score, message;
	private TranslateTransition tt;
	//private ManejadorEventos me;

	public PacMan( String name, Plano g, String v0 ){
			
		this.sprite = new Sprite( name, numFrames, 11, fps );
		this.plano = g;
		this.startVertex = g.getVertex(v0);
		this.initVertex = g.getVertex(v0);
		this.endVertex = g.getVertex(v0);
		this.imageV = new ImageView();
		this.fantasmas = new ArrayList<Ghost>();
		this.lives = new ArrayList<ImageView>();

		this.tt = new TranslateTransition();
		this.tt.setDuration( new Duration( velocidad ) );
		this.tt.setNode( this.imageV );
		this.tt.setCycleCount(1);
		
		this.imageV.setX( this.initVertex.getX() - 6 );
		this.imageV.setY( this.initVertex.getY() - 6 );

		this.message = new Text();

		setComplemets();

	}

	private void setComplemets(){
		double x = 207, y = 252;

		this.score = new Text("Score ");
		this.score.setFont( new Font("Purisa",16) );
		this.score.setX( 0 ); this.score.setY( 266 );
		this.score.setFill( Color.YELLOW );

		setMessage("Ready?", 75, Color.YELLOW, true );

		for( int i = 0; i < vidas; ++i ){
			ImageView imv = new ImageView();
			imv.setImage( this.sprite.getLiveImage() );
			imv.setX(x); imv.setY(y);
			this.lives.add( imv );
			x -= 16;
		}
	}

	public void setOrientacion( String orient ){
		this.nextOrientacion = orient;
	}

	private void setMessage( String msm, double x, Color c, boolean v ){
		this.message.setText( msm );
		this.message.setFont( Font.font("Purisa", FontWeight.BLACK, 20) );
		this.message.setX( x ); this.message.setY( 145 );
		this.message.setFill( c );
		this.message.setVisible( v );
		this.message.toFront();
	}

	public void setRoot( Grupo root ){
		
		this.root = root;
		
		for( Ghost g : this.fantasmas ){
			this.root.getChildren().add( g.getSprite() );
		}
		
		for( ImageView iv : this.lives ){
			this.root.getChildren().add( iv );
		}

		this.root.getChildren().add( this.score );
		this.root.getChildren().add( this.message );
		this.root.getChildren().add( this.imageV );
	}

	public void pause(){
		for( Ghost g : fantasmas )
			g.pauseTransition();
		this.tt.pause();
	}

	public void play(){
		this.tt.play();
		for( Ghost g : fantasmas )
			g.playTransition();
	}

	public void addGhost( Ghost gh){
		this.fantasmas.add( gh );
	}

	public ImageView getSprite(){
		return this.imageV;
	}

	public Text getScore(){
		return this.score;
	}

	public void restartScene(){
		for(Vertex v : this.plano.getMap().values()){
			Circle c = v.getCircle();
			if( c.getRadius() > 0 ){
				c.setVisible(true);
				//this.root.getChildren().add( c );
			}
		}
		this.comida = 244;
	}

	public void restartLives(){
		this.vidas = 3;

		for( ImageView iv: lives )
			iv.setVisible(true);
		
		this.puntaje = 0;
		this.score.setText("Score ");
	}

	public void setIsAlive(){
		if( this.vidas >= 0 && !this.isDead && !this.isAlive ){
			setMessage("Ready?", 75, Color.YELLOW , false );
			this.isAlive = true;
			this.isDead = false;
			this.bandera = true;
		}
	}

	public boolean isAlive(){
		return this.vidas >= 0;
	}

	private void updateScore( int s ){
		this.puntaje += s ;
		this.score.setText("Score "+ this.puntaje);
	}

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

		setMessage("Ready?", 75, Color.YELLOW , true );
	}

	private void win(){

		this.isAlive = false;

		for( Ghost g : fantasmas ){
			g.stopTransition();
		}

		++this.nivel;
		setMessage("LEVEL "+this.nivel , 60, Color.YELLOW , true );

		//this.restartScene();
	}

	private void die( long now ){

		this.isAlive = false;
		this.isDead = true;
		this.time = System.nanoTime();
		//this.imageV.toFront();

		for( Ghost gg : fantasmas ){
				gg.stopTransition();
				gg.setVisible(false);
		}
		/*
		if( --this.vidas >= 0 ){
			this.lives.get( this.vidas ).setVisible(false);
			this.restart();
		}
		else{
			setMessage("GAME OVER", 55, Color.RED , true );
		}
		*/
		
	}

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

		//this.imageV.toFront();

		this.tt.setByX( this.imageV.getLayoutX() );
		this.tt.setByY( this.imageV.getLayoutY() );

		this.tt.setToX( this.imageV.getTranslateX() );
		this.tt.setToY( this.imageV.getTranslateY() );
			
		this.initVertex = this.endVertex;

		this.tt.play();

		Circle eat = this.endVertex.getCircle();

		//if( this.root.getChildren().contains( eat ) ){
		if( eat.isVisible() ){
			if( eat.getRadius() > 1 ){
				for( Ghost g : fantasmas ){
					if( !g.goingToHome() && !g.inHome() )
						g.setState("FEAR");
				}
			}
			else if( eat.getRadius() == 1){
				this.updateScore( 10 );
			}
			//this.root.getChildren().remove( eat );
			eat.setVisible(false);
			--this.comida;
		}
		else if( this.comida == 0){
			win();
		}
	}


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
						die( now );
						break;
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

		//Ponemos la secuencia de explosión
		else if( this.isDead ){
			int num = (int)( now -this.time)/ (1000000000/this.fps);
			
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
					setMessage("GAME OVER", 55, Color.RED , true );
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