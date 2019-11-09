package unam.fi.poo.vista;

import unam.fi.poo.objetos.Sprite;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.estructuras.Graph;
import java.util.Random;

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
	private int direction;
	private boolean fightened;
	
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
		this.fightened = false;
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
		this.fightened = false;
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
	
	public ArrayDeque<String> obtenerMovimineto(Vertex pac){
		
		if(pac.getIntName() == 729){
			return tablero.goToVertex(origen.getName(), pac.getName());
		}else{
			synchronized(this.pacMan){
				if(this.name.equals("inky") && this.fightened != true){
					ArrayDeque<String> mover = tablero.goToDouble(origen.getName(), this.pacMan.getOrigen().getName(), this.Blinky.getMovimientos().size());
					if(mover != null){
						return mover;
					}else{
						return this.movimientos;
					}	
				}else{
					return tablero.goToVertex(origen.getName(), this.pacMan.getOrigen().getName());
				}
			}
		}
	}
	
	public long mover(long start){
		
		if(origen == this.pacMan.getOrigen()){
			pacMan.setStatus(false);
		}
		
		long finish = System.currentTimeMillis();

		if((finish - start) >= 250){
		
			if(this.name.equals("blinky")){
				moverBlinky();
				return System.currentTimeMillis();
			}else if(this.name.equals("pinky")){
				moverPinky();
				return System.currentTimeMillis();
			}else if(this.name.equals("clyde")){
				moverClyde();
				return System.currentTimeMillis();
			}else if(this.name.equals("inky")){
				moverInky();
				return System.currentTimeMillis();
			}
			
		}
		
		return start;
	}
	
	public void run(){
		long start = System.currentTimeMillis();
		while(this.pacMan.getStatus() == true){
			if(this.fightened){
				long currentTime = System.currentTimeMillis();
				this.pacMan.endChase(currentTime);
				
				if(this.pacMan.getChase() != true){
					setFrightened(false);
				}
				
				if(currentTime - start >= 300 && this.fightened){
					start = escape(start);
				}
				
			}else{
				start = mover(start);
			}
		}
	}
	
	public void moverBlinky(){
		
		Vertex pac = pacMan.getOrigen();
		
		if(this.movement >= 2){
			this.movimientos=obtenerMovimineto(pac);
			this.movement = 0;
		}
		actualizar();
	}
	
	public void moverPinky(){
		
		Vertex pac = pacMan.getOrigen();
		
		if(this.movement >= 2){
			
			Vertex destinoMasUno = pac;
			Vertex destinoMasDos = pac;
			
			if(this.pacMan.getDirection() == 0){
				destinoMasUno = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() - 78 );
				destinoMasDos = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() - 104 );
			}else if(this.pacMan.getDirection() == 1){
				destinoMasUno = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() + 78 );
				destinoMasDos = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() + 104 );
			}else if(this.pacMan.getDirection() == 2){
				destinoMasUno = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() - 3 );
				destinoMasDos = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() - 4 );
			}else{
				destinoMasUno = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() + 3 );
				destinoMasDos = this.tablero.getVertex( this.pacMan.getOrigen().getIntName() + 4 );
			}
			
			if(destinoMasDos != null && this.tablero.contains(destinoMasDos.getName())){
				this.movimientos=obtenerMovimineto(destinoMasDos);
			}else if(destinoMasUno != null && this.tablero.contains(destinoMasUno.getName()) && destinoMasDos == null){
				this.movimientos=obtenerMovimineto(destinoMasUno);
			}else{
				obtenerMovimineto(pac);
			}
			
			this.movement = 0;
			
		}
		actualizar();
		
	}
	
	public void moverClyde(){
		
		Vertex pac = pacMan.getOrigen();
		boolean done = true;
		
		if(obtenerMovimineto(pac).size() > 8 && this.movement >= 2){
			this.movimientos = obtenerMovimineto(pac);
			this.movement = 0;
		}else if(obtenerMovimineto(pac).size() <= 8  && this.movement >= 2){
			this.movimientos = obtenerMovimineto(tablero.getVertex("729"));
			this.movement = 0;
		}
		actualizar();
		
	}
	
	public void moverInky(){
		
		Vertex pac = pacMan.getOrigen();
		
		if(this.movement >= 2){
			this.movimientos=obtenerMovimineto(pac);
			this.movement = 0;
		}
		actualizar();
		
		
	}
	
	public void scatter(){
		
	}
	
	public long escape(long start){
		if(this.movement >= 2 && this.movimientos.size() > 2){
			this.movimientos = obtenerMovimineto(this.pacMan.getOrigen());
		}
		Random rand = new Random();
		int direction = rand.nextInt(4);
		int towardPacMan;
		String next = this.movimientos.pollFirst();
		if(next != null){
			towardPacMan = Integer.parseInt(next);
		}else{
			this.origen = tablero.getVertex("351");
			this.me.getImageV().setX(this.origen.getX()-6);
			this.me.getImageV().setY(this.origen.getY()-6);
			setFrightened(false);
			return start;
		}
		
		Vertex escape = new Vertex();
		boolean escaping = false;
		
		if(direction == 0 && this.direction != 1){
			if((this.origen.getIntName() + 1) != towardPacMan && tablero.getVertex(this.origen.getIntName() + 1) != null){
				escape = tablero.getVertex(this.origen.getIntName() + 1);
				escaping = true;
			}
		}else if(direction == 1 && this.direction != 0){
			if((this.origen.getIntName() - 1) != towardPacMan && tablero.getVertex(this.origen.getIntName() - 1) != null){
				escape = tablero.getVertex(this.origen.getIntName() - 1);
				escaping = true;
			}
		}else if(direction == 2 && this.direction != 3){
			if((this.origen.getIntName() + 26) != towardPacMan && tablero.getVertex(this.origen.getIntName() + 26) != null){
				escape = tablero.getVertex(this.origen.getIntName() + 26);
				escaping = true;
			}
		}else if(this.direction != 2){
			if((this.origen.getIntName() - 26) != towardPacMan && tablero.getVertex(this.origen.getIntName() - 26) != null){
				escape = tablero.getVertex(this.origen.getIntName() - 26);
				escaping = true;
			}
		}
		
		if(escaping && this.movement >= 2 && escape != null && this.origen.getNeighbors().contains(escape)){
			double origX = this.origen.getX();
			double origY = this.origen.getY();
			
			double destX = escape.getX();
			double destY = escape.getY();
	
			setEstado("DIE");
		
			this.me.getImageV().setX(destX-6);
			this.me.getImageV().setY(destY-6);
			if(escape.getIntName() == this.pacMan.getOrigen().getIntName()){
				this.origen = tablero.getVertex("351");
				this.me.getImageV().setX(this.origen.getX()-6);
				this.me.getImageV().setY(this.origen.getY()-6);
				this.setFrightened(false);
				return start;
			}
			this.origen = escape;
			this.movement = 0;
			this.direction = direction;
			
			return System.currentTimeMillis();
		}
		
		this.movement++;
		
		return start;
		
	}
	
	public void actualizar(){
		
		Vertex destino = new Vertex();
		String lugar = movimientos.pollFirst();
		destino = tablero.getVertex(lugar);
		
		double origX = this.origen.getX();
		double origY = this.origen.getY();
		
		if(destino != null){
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
		
			this.me.getImageV().setX(destX-6);
			this.me.getImageV().setY(destY-6);
			this.origen = destino;
		
			this.movement++;
		}
		else{
			this.movement = 2;
		}
		
	}
	
	public void setFrightened(boolean frightened){
		this.fightened = frightened;
	}
}
