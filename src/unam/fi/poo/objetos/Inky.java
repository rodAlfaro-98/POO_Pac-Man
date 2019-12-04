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

public class Inky extends Ghost{
	
	//Constructor
	public Inky( Plano g, String v0 ){
		super.nombre = "INKY";
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
								case "INKY":
									playOnChasePath(); break;
								case "FEAR":
									playOnFearPath(); break;
								case "HOME":
									playToHomePath(); break;
								case "WAIT":
									playWaitPath(6000); break;
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
		
		if((System.currentTimeMillis() - this.timer) <5 || this.initVertex.getName().equals("754")){
			this.scatterPath = this.plano.getPathTo(this.plano.goToNextVertexInPath(this.initVertex, 
			this.plano.getVertex("665")),this.plano.getVertex("665"));
		}else  if(this.initVertex.getName().equals("665")){
			this.scatterPath = this.plano.getPathTo(this.plano.goToNextVertexInPath(this.initVertex, 
			this.plano.getVertex("754")),this.plano.getVertex("754"));
		}
		
		if( !super.scatterPath.isEmpty() )
			this.endVertex = this.plano.getVertex(this.scatterPath.pop());
		moveGhost();
	}
	
	
	
	public void playOnChasePath(){
		
		Stack<String> movimiento = null;
		String mover = null;
		Vertex nextVertex = this.plano.goToNextVertexInPath(this.initVertex,this.pacVertex);
			
		if(nextVertex != null)
			movimiento = this.plano.getDoublePathTo(nextVertex,this.pacVertex, this.blinkyLenPath);
		
		if(movimiento != null)
			mover = movimiento.peek();
		
		
		if(mover != null){
			this.endVertex = this.plano.getVertex(mover);
			moveGhost();
		}

	}

}