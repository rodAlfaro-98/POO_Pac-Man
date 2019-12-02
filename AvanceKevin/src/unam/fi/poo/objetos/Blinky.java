package unam.fi.poo.objetos;

import java.util.Stack;
import javafx.util.Duration;

import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.animation.Animation.Status;
import javafx.animation.TranslateTransition;

import unam.fi.poo.estructuras.Plano;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.interfaces.Ghost;

public class Blinky extends Ghost{

	//Constructor
	public Blinky( Plano g, String v0 ){
		super.nombre = "BLINKY";
		super.sprite = new Sprite( super.nombre, FRAMES, 4, FPS );
		super.plano = g;
		super.startVertex = g.getVertex(v0);
		super.initVertex = g.getVertex(v0);
		super.endVertex = g.getVertex(v0);
		super.stackPath = new Stack<String>();

		super.imageV = new ImageView();
		super.imageV.setX( initVertex.getX()-6 );
		super.imageV.setY( initVertex.getY()-6 );

		super.tTransition = new TranslateTransition();
		super.tTransition.setDuration( new Duration( VELOCIDAD ) );
		super.tTransition.setNode( super.imageV );
		super.tTransition.setCycleCount(1);
		super.state = super.nombre;
		
		super.movimientos = new Stack<String>();
		super.timer = System.currentTimeMillis();
		super.scatter = false;
		super.scatterPath = new Stack<String>();

		this.initAnimationTimer();
		
	}

	private void initAnimationTimer(){
		super.aTimer = new AnimationTimer(){
			//Método manejador de todos los eventos
			@Override
			public void handle( long now ){
				
				//System.out.println("Blinky state: "+ getState() );

				if( isPacManAlive ){

					if( tTransition.getStatus() == Status.STOPPED ){
						setOrigen();
						
						if(!state.equals("FEAR") && !state.equals("HOME"));
							seeIfScatter();
						
						if(scatter){
							scatter();
						}
						else{
							switch( state ){
								case "BLINKY":
									playOnChasePath(); break;
								case "FEAR":
									playOnFearPath(); break;
								case "HOME":
									playToHomePath(); break;
								case "WAIT":
									playWaitPath(1000); break;
							}
						}
						setOrientacion();
						setImageOrientation( now );
					}
				}
				setImageOrientation( now );
			}
		};
	}
	
	public void scatter(){
		
		if((System.currentTimeMillis() - this.timer) <5 || this.initVertex.getName().equals("1")){
			this.scatterPath = this.plano.getPathTo(this.plano.goToNextVertexInPath(this.initVertex, 
			this.plano.getVertex("116")),this.plano.getVertex("116"));
		}else if(this.initVertex.getName().equals("116")){
			this.scatterPath = this.plano.getPathTo(this.plano.goToNextVertexInPath(this.initVertex,
			this.plano.getVertex("1")),this.plano.getVertex("1"));
		}

		if( !super.scatterPath.isEmpty() )
			this.endVertex = this.plano.getVertex(this.scatterPath.pop());
		moveGhost();
		
	}
	
	public void playOnChasePath(){
		
		this.endVertex = this.plano.goToNextVertexInPath(
			this.initVertex, this.pacVertex );

		this.blinkyLenPath = this.pacVertex.getDistance();
		this.blinkyVertex = this.initVertex.getName();
		
		moveGhost();
		
	}

}