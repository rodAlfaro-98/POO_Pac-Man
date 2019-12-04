
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


public class Pinky extends Ghost{

	//Constructor
	public Pinky( Plano g, String v0 ){
		super.nombre = "PINKY";
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
		super.state = "WAIT";
		
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
								case "PINKY":
									playOnChasePath(); break;
								case "FEAR":
									playOnFearPath(); break;
								case "HOME":
									playToHomePath(); break;
								case "WAIT":
									playWaitPath(3000); break;
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
		
		if((System.currentTimeMillis() - super.timer) <5 || super.initVertex.getName().equals("26")){
			super.scatterPath = super.plano.getPathTo(super.plano.goToNextVertexInPath(super.initVertex, 
			super.plano.getVertex("119")),super.plano.getVertex("119"));
		}else if(super.initVertex.getName().equals("119")){
			super.scatterPath = super.plano.getPathTo(super.plano.goToNextVertexInPath(super.initVertex, 
			super.plano.getVertex("26")),super.plano.getVertex("26"));
		}
		if( !super.scatterPath.isEmpty() )
			super.endVertex = super.plano.getVertex(super.scatterPath.pop());
		super.moveGhost();
		
	}
	
	public void playOnChasePath(){
		
		Vertex tile = plano.getNextVertex(
			super.pacVertex, 4, super.pacOrientacion, false );

		if( tile != null ){
			super.endVertex = tile;
		}
		else{
			super.endVertex = plano.getNextVertex(
				super.pacVertex, 3, super.pacOrientacion, true );
		}

		super.endVertex = super.plano.goToNextVertexInPath(
			super.initVertex, super.pacVertex );
		
		super.moveGhost();
		
	}

}