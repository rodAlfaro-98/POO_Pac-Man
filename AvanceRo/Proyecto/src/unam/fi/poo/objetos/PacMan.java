
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
import unam.fi.poo.interfaces.GhostClass;

public class PacMan extends AnimationTimer{
	
	private static final int fps = 11,
		numFrames = 4, velocidad = 90;
	private int puntaje = 0, comida = 244, vidas = 3, nivel = 1;
	private String orientacion = "RIGHT",
					nextOrientacion = "RIGHT";
	private Vertex startVertex, initVertex, endVertex;
	private ImageView imageV;
	private Sprite sprite;
	private Plano plano;
	private ArrayList<GhostClass> fantasmas;
	private ArrayList<ImageView> lives;
	private boolean alive = false, saveScore = true;
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
		this.fantasmas = new ArrayList<GhostClass>();
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
		this.root.getChildren().add( this.imageV );
		this.root.getChildren().add( this.score );
		this.root.getChildren().add( this.message );
		
		for( GhostClass g : this.fantasmas ){
			this.root.getChildren().add( g.getSprite() );
		}
		
		for( ImageView iv : this.lives ){
			this.root.getChildren().add( iv );
		}
	}

	public void pause(){
		for( GhostClass g : fantasmas )
			g.pause();
		this.tt.pause();
	}

	public void play(){
		this.tt.play();
		for( GhostClass g : fantasmas )
			g.play();
	}

	public void addGhostClass( GhostClass gh){
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
		restartScene();
		restart();
	}

	public void setIsAlive(){
		if( this.vidas >= 0){
			setMessage("Ready?", 75, Color.YELLOW , false );
			this.alive = true;
			this.saveScore = true;
			//start();
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
		this.initVertex = this.startVertex;
		this.endVertex = this.startVertex;

		for(GhostClass g : fantasmas ){
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

		this.alive = false;

		for( GhostClass g : fantasmas ){
			g.stopGhost();
		}

		++this.nivel;
		setMessage("LEVEL "+this.nivel , 60, Color.YELLOW , true );

		//this.restartScene();
	}

	private void die( long now ){

		this.alive = false;
		//this.imageV.toFront();

		for( GhostClass gg : fantasmas ){
				gg.stopGhost();
				gg.setVisible(false);
		}
		
		if( --this.vidas >= 0 ){
			this.lives.get( this.vidas ).setVisible(false);
			this.restart();
		}
		else{
			setMessage("GAME OVER", 55, Color.RED , true );
		}
		
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

		this.imageV.toFront();

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
				for( GhostClass g : fantasmas ){
					if( !g.goingToHome() )
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

		if( this.alive ){

			this.imageV.setImage( 
				this.sprite.getImage( this.orientacion, now ) );
			
			for( GhostClass g : fantasmas ){

				g.setPacManState(this.alive);
				g.setPacManVertex( this.initVertex );
				g.setPacManOrientation( this.orientacion );

				if( this.imageV.getBoundsInParent().intersects( g.getBounds() ) ){
					if( g.isFear() ){
						if( g.isScatter() )
							g.setScatter(false);

						g.setState("HOME");
						this.updateScore( 200 );
					}

					else if( !g.goingToHome() ){
						g.setVisible(false);
						die( now );
						break;
					}
				}
			}
			if( this.tt.getStatus() == Status.STOPPED )
				moveTo();
		}

		else if( this.vidas >= 0 && this.comida == 0 ){

			try{ Thread.sleep(2000); }
			catch( Exception e ){
				System.out.println( "Error en sleep! ");
			}

			this.restartScene();
			this.restart();
		}

		else if( this.vidas < 0 && this.saveScore ){
			this.saveScore = false;

			for( GhostClass g : fantasmas ){
				g.stopGhost();
			}

			try{ Thread.sleep(2000); }
			catch( Exception e ){
				System.out.println( "Error en sleep! ");
			}

			this.root.getManejadorEventos().getApp().getScoreGrid().setScore( this.puntaje );
			this.restartLives();

			this.root.getManejadorEventos().setScoreScene();
			//this.stop();

		}

		else{
			this.imageV.setImage( 
				this.sprite.getImage( "RIGHT", now ) );
		}
	}
}