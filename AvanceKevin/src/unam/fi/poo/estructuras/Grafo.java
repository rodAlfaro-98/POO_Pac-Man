package unam.fi.poo.estructuras;

import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import unam.fi.poo.objetos.Administrador;

public class Grafo extends Graph{

	private int tamX, tamY;
	private double x0, y0, incr;
	private Administrador admon;
	private HashMap<String,Integer> nodes;
	private ArrayList<String> special;

	public Grafo(){
		super();
	}

	public Grafo( int tamX, int tamY , double x0, double y0, double incr){
		super();
		this.tamX = tamX;
		this.tamY = tamY;
		this.x0 = x0;
		this.y0 = y0;
		this.incr = incr;
		this.special = new ArrayList<String>();
		this.admon = new Administrador();
		this.nodes = admon.getNodos();
	}

	public void addSpecialNode( String nameV ){
		this.special.add( nameV );
	}

	public void setSpecialNodes( ArrayList<String> sp ){
		this.special = sp;
	}

	public HashMap<String, Vertex> getMap(){
		return super.getMap();
	}

	public void createGraph(){

		int i, j, n = 1;
		String [][]vertices = new String[tamY][tamX];

		//Crea los nombres de los vertices
		for( i = 0; i < tamY; ++i ){
			for( j = 0; j < tamX; ++j ){
				vertices[i][j] = String.valueOf(n);
				++n;
			}
		}
		
		//Crea los vertices
		double xx = x0, yy = y0;

		for( i = 0; i < tamY; ++i, yy += incr){
			for( j = 0; j < tamX; ++j, xx += incr){
				if( this.nodes.containsKey( vertices[i][j] ))
					switch( this.nodes.get(vertices[i][j]) ){
						case 0:
						{
							addVertex( new Vertex( vertices[i][j], xx, yy, 0, Color.RED) );
						} break;
						case 1:
						{
							addVertex( new Vertex( vertices[i][j], xx, yy, 1, Color.RED) );
						} break;
						case 2:
						{
							addVertex( new Vertex( vertices[i][j], xx, yy, 3, Color.RED) );
						} break;
					}
			}
			xx = x0;
		}
		
		//Une los vertices horizontalmente
		for( i = 0; i < tamY; ++i ){
			for( j = 1; j < tamX; ++j ){
				if(contains(vertices[i][j-1]) && contains(vertices[i][j]))
					addEdge( vertices[i][j-1], vertices[i][j] );
			}
		}
		
		//Une los vertices verticalmente
		for( i = 1; i < tamY; ++i ){
			for( j = 0; j < tamX; ++j ){
				if(contains(vertices[i-1][j]) && contains(vertices[i][j]))
					if( special.contains( vertices[i][j] ) )
						addEdgeDirected(vertices[i][j], vertices[i-1][j]);
					else
						addEdge( vertices[i-1][j], vertices[i][j] );
			}
		}		
	}

}