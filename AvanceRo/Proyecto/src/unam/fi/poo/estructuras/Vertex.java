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

	private String name;
	private ArrayList<Vertex> neighbors;
	private int distance = 0;
	private Colors color = Colors.BLACK;
	private String predecesor = "";
	private int weigth = 0;

	private int discovery_time;
	private int finish_time;

	//private double radio = 10;
	private double x, y;
	private Circle c;
	private Text nombre;
	private ArrayList<Line> edges;

	//private File file;
	//private Image image;
	//private ImageView imageV;

	public Vertex(){
		
	}

	public Vertex(String _name, double x, double y, double r){
		this.name = _name;
		this.x = x;
		this.y = y;
		this.c = new Circle(x,y,r);
		this.neighbors = new ArrayList<Vertex>();
		this.edges = new ArrayList<Line>();
		this.nombre = new Text(x,y,_name);
		//this.file = new File("./imagenes/Comida.png");
		//this.image = new Image( file.toURI().toString() );
		//this.imageV = new ImageView(image);
		//this.imageV.relocate(x-2,y-2);

	}

	public Vertex(String _name, double x, double y, double r, Color c){
		this.name = _name;
		this.x = x;
		this.y = y;
		this.c = new Circle(x,y,r,c);
		this.neighbors = new ArrayList<Vertex>();
		this.edges = new ArrayList<Line>();
		this.nombre = new Text(x,y,_name);

	}

	public Text getText(){
		return this.nombre;
	}

	public ArrayList<Line> getLines(){
		return this.edges;
	}

	public void addLine(Vertex v2){
		this.edges.add( new Line(x,y,v2.getX(), v2.getY() ) );
	}

	public void updateColor(){
		this.c.setFill( Color.RED );
	}

	public Circle getCircle(){
		return this.c;
	}

	//public ImageView getImage(){
	//	return this.imageV;
	//}

	public void setX(int x){ this.x = x; }

	public void setY(int y){ this.y = y; }

	public double getX(){ return this.x; }

	public double getY(){ return this.y; }

	public String getName(){
		return this.name;
	}

	public int getIntName(){
		return Integer.valueOf(this.name);
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

	public void printNeighbors(){
		System.out.print("\tN-> ");
		for(Vertex v : this.neighbors){
			System.out.print(v.getName()+", ");
		}
		System.out.println();
	}

	public void print(){
		String color = "";
		String s = "  ";
		switch(this.color){
			case BLACK: color = "BLACK"; break;
			case GRAY: color = "GRAY"; break;
			case WHITE: color = "WHITE"; break;
		}
		System.out.print(this.name+" -> "+this.predecesor+s+
			this.distance+s+this.discovery_time+"/"+this.finish_time+s+"\t"+
			color+s+this.predecesor);
	}

}