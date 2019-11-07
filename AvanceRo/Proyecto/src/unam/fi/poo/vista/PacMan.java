package unam.fi.poo.vista;

import unam.fi.poo.objetos.Sprite;
import unam.fi.poo.estructuras.Vertex;
import unam.fi.poo.estructuras.Graph;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayDeque;

public class PacMan{

	private Sprite me;
	private Graph tablero;
	private Vertex origen;
	private boolean alive;
	private int direction;
	
	public PacMan(Vertex origen, Graph tablero){
		this.me = new Sprite("./imagenes/PM_",4, false, "RIGHT", origen.getX(), origen.getY());	
		this.tablero = tablero;
		this.origen = origen;
		this.alive = true;
		this.direction = 0;
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

	public void setOrigen(Vertex origen){
		this.origen = origen;
	}
	
	public Vertex getOrigen(){
		return this.origen;
	}
	
	public void setStatus(boolean status){
		setEstado("DIE");
		this.alive = false;
	}
	
	public boolean getStatus(){
		return this.alive;
	}
	
	public void setDirection(int dir){
		this.direction = dir;
	}
	
	public int getDirection(){
		return this.direction;
	}
}