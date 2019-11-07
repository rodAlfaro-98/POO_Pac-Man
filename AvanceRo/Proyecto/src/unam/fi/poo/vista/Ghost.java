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
	private int movement;
	private long scatter;
	private long nonScatter;
	private Ghost Blinky;
	
	public Ghost(String ghost, String name, Vertex origen, Graph tablero, PacMan pacMan){
		this.me =  new Sprite(ghost,2, false, "RIGHT",origen.getX(), origen.getY());
		this.name = name;
		this.tablero = tablero;
		this.origen = origen;
		this.movimientos = tablero.goToVertex(origen.getName(), pacMan.getOrigen().getName());
		this.movimientos.push("351");
		this.pacMan = pacMan;
		this.movement = 0;
		this.scatter = 0;
		this.nonScatter = 0;
	}
	
	public Ghost(String ghost, String name, Vertex origen, Graph tablero, PacMan pacMan, Ghost Blinky){
		this.me =  new Sprite(ghost,2, false, "RIGHT",origen.getX(), origen.getY());
		this.name = name;
		this.tablero = tablero;
		this.origen = origen;
		this.movimientos = tablero.goToVertex(origen.getName(), pacMan.getOrigen().getName());
		this.movimientos.push("351");
		this.pacMan = pacMan;
		this.movement = 0;
		this.scatter = 0;
		this.nonScatter = 0;
		this.Blinky = Blinky;
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
	
	public ArrayDeque<String> getMovimientos(){	
		return this.movimientos;
	}
	
	public void obtenerMovimineto(Vertex pac){
		
		if(this.name.equals("inky")){
			ArrayDeque<String> mover = tablero.goToDouble(origen.getName(), pac.getName(), this.Blinky.getMovimientos().size());
			if(mover != null){
				this.movimientos = mover;
			}
		}
		else{
			this.movimientos = tablero.goToVertex(origen.getName(), pac.getName());
		}
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
			}else if(this.name.equals("pinky")){
				//moverPinky();
				return System.currentTimeMillis();
			}else if(this.name.equals("clyde") && (finish - start) >= 250 ){
				//moverClyde();
				return System.currentTimeMillis();
			}else if(this.name.equals("inky") && (finish - start) >= 255 ){
				moverInky();
				return System.currentTimeMillis();
			}
			
		}
		
		return start;
	}
	
	public void run(){
		long start = System.currentTimeMillis();
		//try{
			while(this.pacMan.getStatus() == true){
				start = mover(start);
			}
		/*}catch(Exception e){
			this.pacMan.setStatus(false);
		}*/
	}
	
	public void moverBlinky(){
		Vertex pac = pacMan.getOrigen();
		
		Vertex destino = new Vertex();
		
		if(this.movement == 4){
			obtenerMovimineto(pac);
			this.movement = 0;
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
		this.movement++;
	}
	
	public void moverPinky(){
		Vertex pac = pacMan.getOrigen();
		
		Vertex destino = new Vertex();
		if(this.movement == 4){
			
			Vertex destinoMasUno = pac;
			Vertex destinoMasDos = pac;
			
			if(this.pacMan.getDirection() == 0){
				destinoMasUno = destino = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() - 78 );
				destinoMasDos = destino = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() - 104 );
			}else if(this.pacMan.getDirection() == 1){
				destinoMasUno = destino = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() + 78 );
				destinoMasDos = destino = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() + 104 );
			}else if(this.pacMan.getDirection() == 2){
				destinoMasUno = destino = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() - 3 );
				destinoMasDos = destino = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() - 4 );
			}else{
				destinoMasUno = destino = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() + 3 );
				destinoMasDos = destino = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() + 4 );
			}
			
			if(destinoMasDos != null && this.tablero.contains(destinoMasDos.getName())){
				obtenerMovimineto(destinoMasDos);
			}else if(destinoMasUno != null && this.tablero.contains(destinoMasUno.getName()) && destinoMasDos == null){
				obtenerMovimineto(destinoMasUno);
			}else{
				obtenerMovimineto(pac);
			}
			
			this.movement = 0;
			
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
		
		this.movement++;
			
	}
	
	public void moverClyde(){
		Vertex pac = pacMan.getOrigen();
		Random rand = new Random();
		Vertex destino = new Vertex();
		boolean done = false;
		
		if(this.movimientos.size() > 8){
			moverBlinky();
		}
		else{
			
			done = true;
			
			obtenerMovimineto(tablero.getVertex("729"));
			
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
			
			if(done){
						
				this.getImageV().setX(destino.getX()-6);
				this.getImageV().setY(destino.getY()-6);
				this.origen = destino;
			}	

			this.movement = 0;
		}
		
	}
	
	public void moverInky(){
		
		Vertex pac = pacMan.getOrigen();
		
		Vertex destino = new Vertex();
		
		if(this.movement == 4){
			obtenerMovimineto(pac);
			this.movement = 0;
			//System.out.println("\n\n\n");
		}
		//System.out.println(this.movimientos.size()+"; move: "+this.movement);
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
		this.movement++;
		
	}
	
}
