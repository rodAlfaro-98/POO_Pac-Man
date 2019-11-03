package unam.fi.poo.vista;

import unam.fi.poo.objetos.Sprite;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.estructuras.Graph;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayDeque;

public class Ghost implements Runnable{
	
	private Sprite me;
	private String name;
	private Graph tablero;
	private Vertex origen;
	private ArrayDeque<String> movimientos;
	private PacMan pacMan;
	private boolean exit;
	
	public Ghost(String ghost, String name, Vertex origen, Graph tablero, PacMan pacMan){
		this.me =  new Sprite(ghost,2, false, "RIGHT",origen.getX(), origen.getY());
		this.name = name;
		this.tablero = tablero;
		this.origen = origen;
		this.movimientos = new ArrayDeque<String>();
		this.movimientos.push("351");
		this.pacMan = pacMan;
		this.exit = false;
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
		
		long finish = System.currentTimeMillis();

		System.out.println(finish - start);

		if((finish - start) >= 140){
		
			Vertex pac = pacMan.getOrigen();
		
			Vertex destino = new Vertex();
			if (this.movimientos.size() == 0){
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
			
			return System.currentTimeMillis();
			
		}
		
		return start;
	}
	
	public void run(){
		System.out.println("Inicia hilo de "+this.name);
		long start = System.currentTimeMillis();
		try{
			while(this.exit != true){
				start = mover(start);
			}
		}catch(Exception e){
			this.pacMan.setStatus(false);
		}
	}
	
	public void done(){
		this.exit = true;
	}
	
}