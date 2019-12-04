package unam.fi.poo.estructuras;

import unam.fi.poo.estructuras.Colors;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util. ArrayList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.File;

public class Vertex{

	private static double dif = 0;
	private String name;
	private ArrayList<Vertex> neighbors;
	private int distance = 0;
	private Colors color = Colors.BLACK;
	private String predecesor = "";
	private int weigth = 0;

	private int discovery_time;
	private int finish_time;

	/**>!Coordenadas x, y del círculo (comida).*/
	private double x, y, r;
	private Circle c;
	private Text nombre;
	private ArrayList<Line> edges;

	/**
	* @brief Constructor vacío del objeto Vertex.
	*/
	public Vertex(){ }

	/**
	* @brief Constructor del objeto Vertex.
	* @param _name de tipo String. Es el nombre del vértice.
	* @param x de tipo double. Es la coordenada de posición en x.
	* @param y de tipo double. Es la coordenada de posición en y.
	* @param r de tipo double. Es el radio del círculo.
	*/
	public Vertex(String _name, double x, double y, double r){
		this.name = _name;
		this.x = x;
		this.y = y;
		this.r = r;
		this.c = new Circle(x,y,r);
		this.neighbors = new ArrayList<Vertex>();
		this.edges = new ArrayList<Line>();
		this.nombre = new Text(x,y,_name);

	}

	/**
	* @brief Constructor del objeto Vertex.
	* @param _name de tipo String. Es el nombre del vértice.
	* @param x de tipo double. Es la coordenada de posición en x.
	* @param y de tipo double. Es la coordenada de posición en y.
	* @param r de tipo double. Es el radio del círculo.
	* @param c de tipo Color. Es el color del círculo.
	*/
	public Vertex(String _name, double x, double y, double r, Color c){
		this.name = _name;
		this.x = x;
		this.y = y;
		this.r = r;
		this.c = new Circle(x,y,r,c);
		this.neighbors = new ArrayList<Vertex>();
		this.edges = new ArrayList<Line>();
		this.nombre = new Text(x,y,_name);

	}

	/**
	* @brief Setters y Getters de todos los atributos.
	*/
	public Text getText(){
		return this.nombre;
	}

	public ArrayList<Line> getLines(){
		return this.edges;
	}

	public void addLine(Vertex v2){
		this.edges.add( new Line(x,y,v2.getX(), v2.getY() ) );
	}

	public Circle getCircle(){
		return this.c;
	}

	public void setX(int x){ this.x = x; }

	public void setY(int y){ this.y = y; }

	public double getX(){ return this.x-dif; }

	public double getY(){ return this.y-dif; }

	public String getName(){
		return this.name;
	}

	public int getIntName(){
		return Integer.parseInt(this.name);
	}

	public int getDistance(){
		return this.distance;
	}

	public void setDistance( int _distance){
		this.distance = _distance;
	}

	public Colors getColor(){
		return this.color;
	}

	public void setColor( Colors _color){
		this.color = _color;
	}

	public String getPredecesor(){
		return this.predecesor;
	}

	public void setPredecesor(String _predec){
		this.predecesor = _predec;
	}

	public int getWeight(){
		return this.weigth;
	}

	public ArrayList<Vertex> getNeighbors(){
		return this.neighbors;
	}

	public void setFinishTime( int _ftime){
		this.finish_time = _ftime;
	}

	public int getFinishTime(){
		return this.finish_time;
	}

	public void setDiscoveryTime( int _dtime){
		this.discovery_time = _dtime;
	}

	public int getDiscoveryTime(){
		return this.discovery_time;
	}

	public void addNeighbor(Vertex v){
		this.neighbors.add(v);
	}

	public void print(){
		System.out.println( this.name + "[ " + this.x + " , " + this.y + " ]" + this.distance );
	}

}