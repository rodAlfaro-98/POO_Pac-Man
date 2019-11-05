package unam.fi.poo.vista;

import unam.fi.poo.objetos.Sprite;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.estructuras.Graph;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayDeque;
import java.util.Random;

public class Ghost implements Runnable{
	
	private Sprite me;
	private String name;
	private Graph tablero;
	private Vertex origen;
	private ArrayDeque<String> movimientos;
	private PacMan pacMan;
	private int num;
	private int randomPast;
	
	public Ghost(String ghost, String name, Vertex origen, Graph tablero, PacMan pacMan){
		this.me =  new Sprite(ghost,2, false, "RIGHT",origen.getX(), origen.getY());
		this.name = name;
		this.tablero = tablero;
		this.origen = origen;
		this.movimientos = tablero.goToVertex(origen.getName(), pacMan.getOrigen().getName());
		this.randomPast = 0;
		this.movimientos.push("351");
		this.pacMan = pacMan;
		this.num = 0;
	}
	
	public Sprite getMe(){
		return this.me;
	}
	
	public ImageView getImageV(){
		return this.me.getImageV();
	}
	
	public void setEstado(String estado){
		this.me.setEstado(estado);
	}	
	
	public void start(){
		this.me.start();
	}
	
	public void obtenerMovimineto(Vertex pac){
		
		this.movimientos = tablero.goToVertex(origen.getName(), pac.getName());
	}
	
	public long mover(long start){
		
		if(origen == this.pacMan.getOrigen()){
			pacMan.setStatus(false);
		}
		
		long finish = System.currentTimeMillis();

		if((finish - start) >= 150){
		
			if(this.name.equals("blinky")){
				moverBlinky();
				return System.currentTimeMillis();
			}else if(this.name.equals("pinky") && (finish - start) >= 250 ){
				moverPinky();
				return System.currentTimeMillis();
			}else if(this.name.equals("clyde") && (finish - start) >= 250 ){
				moverClyde();
				return System.currentTimeMillis();
			}
			
		}
		
		return start;
	}
	
	public void run(){
		long start = System.currentTimeMillis();
		try{
			while(this.pacMan.getStatus() == true){
				start = mover(start);
			}
		}catch(Exception e){
			this.pacMan.setStatus(false);
		}
	}
	
	public void moverBlinky(){
		Vertex pac = pacMan.getOrigen();
		
		Vertex destino = new Vertex();
		
		if(this.num == 1){
			obtenerMovimineto(pac);
			this.num = -1;
		}
		String lugar = movimientos.pollFirst();
		destino = tablero.getVertex(lugar);
		
		if(destino == this.pacMan.getOrigen()){
			pacMan.setStatus(false);
		}
		
		double origX = this.origen.getX();
		double origY = this.origen.getY();
		
		double destX = destino.getX();
		double destY = destino.getY();
		
		if(origY == destY){
			if( (origX-destX) > 0 ){
				setEstado("LEFT");
			}else{
				setEstado("RIGHT");
			}
		}else if( (origY-destY) < 0){
			setEstado("DOWN");
		}else{
			setEstado("UP");
		}
		
		this.me.getImageV().setX(destino.getX()-6);
		this.me.getImageV().setY(destino.getY()-6);
		this.origen = destino;
		
		this.num++;
	}
	
	public void moverPinky(){
		Vertex pac = pacMan.getOrigen();
		
		Vertex destino = new Vertex();
		if(this.movimientos.size() == 0){
			obtenerMovimineto(pac);
		}
		String lugar = movimientos.pollFirst();
		destino = tablero.getVertex(lugar);
	
		double origX = this.origen.getX();
		double origY = this.origen.getY();
	
		double destX = destino.getX();
		double destY = destino.getY();
	
		if(origY == destY){
			if( (origX-destX) > 0 ){
				setEstado("LEFT");
			}else{
				setEstado("RIGHT");
			}
		}else if( (origY-destY) < 0){
			setEstado("DOWN");
		}else{
			setEstado("UP");
		}
		
		this.me.getImageV().setX(destino.getX()-6);
		this.me.getImageV().setY(destino.getY()-6);
		this.origen = destino;
			
	}
	
	public void moverClyde(){
		Vertex pac = pacMan.getOrigen();
		Random rand = new Random();
		Vertex destino = new Vertex();
		boolean done = false;
		
		if(this.movimientos.size() > 15){
			moverPinky();
		}
		else{
			int random = rand.nextInt(4);
		
			if(random == 0 && randomPast != 1){
				destino = this.tablero.getVertex( this.origen.getIntName() - 1 );
				if( destino != null && this.origen.getNeighbors().contains(destino)){
					this.setEstado("LEFT");
					done = true;
				}
			}else if(random == 1 && randomPast != 0){
				destino = this.tablero.getVertex( this.origen.getIntName() + 1 );
				if( destino != null && this.origen.getNeighbors().contains(destino)){
					this.setEstado("RIGHT");
					done = true;
				}
			}else if(random == 2 && randomPast != 3){
				destino = this.tablero.getVertex( this.origen.getIntName() - 26 );
				if( destino != null && this.origen.getNeighbors().contains(destino)){
					this.setEstado("UP");
					done = true;
				}
			}else if(random == 3 && randomPast != 2){
				destino = this.tablero.getVertex( this.origen.getIntName() + 26 );
				if( destino != null && this.origen.getNeighbors().contains(destino)){
					this.setEstado("DOWN");
					done = true;
				}	
			}
			
			if(done){
						
				this.getImageV().setX(destino.getX()-6);
				this.getImageV().setY(destino.getY()-6);
				this.origen = destino;
			}	
			
			obtenerMovimineto(pac);

			this.randomPast = random;
		}
		
	}
	
}