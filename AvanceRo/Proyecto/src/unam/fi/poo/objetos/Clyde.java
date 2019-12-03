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

public class Clyde extends Ghost{
	
	//Constructor
	public Clyde( Plano g, String v0 ){
		super.nombre = "CLYDE";
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
								case "CLYDE":
									playOnChasePath(); break;
								case "FEAR":
									playOnFearPath(); break;
								case "HOME":
									playToHomePath(); break;
								case "WAIT":
									playWaitPath(7000); break;
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
		
		if((System.currentTimeMillis() - this.timer) <5 || this.initVertex.getName().equals("729")){
			this.scatterPath = this.plano.getPathTo(this.plano.goToNextVertexInPath(this.initVertex,	
			this.plano.getVertex("662")),this.plano.getVertex("662"));
		}else if(this.initVertex.getName().equals("662")){
			this.scatterPath = this.plano.getPathTo(this.plano.goToNextVertexInPath(this.initVertex, 
			this.plano.getVertex("729")),this.plano.getVertex("729"));
		}
		
		if( !super.scatterPath.isEmpty() )
			this.endVertex = this.plano.getVertex(this.scatterPath.pop());
		
		if(this.endVertex != null)
			moveGhost();
		
	}
	
	public void playOnChasePath(){
		
		this.endVertex = this.plano.goToNextVertexInPath(
			this.initVertex, this.pacVertex );

		if( this.pacVertex.getDistance() <= 8){
			if(this.initVertex.getName().equals("729"))
				this.endVertex = this.plano.goToNextVertexInPath(
					this.initVertex, this.plano.getVertex("662") );
			else
				this.endVertex = this.plano.goToNextVertexInPath(
					this.initVertex, this.plano.getVertex("729") );
		}

		if(this.endVertex != null)
			moveGhost();
		
	}
	
}