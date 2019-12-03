package unam.fi.poo.estructuras;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Stack;
import java.util.Random;
import javafx.scene.paint.Color;
import unam.fi.poo.objetos.Administrador;

public class Plano extends Graph{
	private int tamX, tamY;
	private double x0, y0, incr;
	private HashMap<String,Integer> nodes;
	private ArrayList<String> special;
	private ArrayList<String> inicio;
	private Administrador admon;

	/**
	* @brief Constructor vacío del objeto Plano.
	*/
	public Plano(){
		super();
	}

	/**
	* @brief Constructor del objeto Plano.
	* @param x0 de tipo double. Es la coordenada x origen;
	* @param y0 de tipo double. Es la coordenada y origen;
	* @param tamx de tipo entero. Es el numero de columas de la matriz.
	* @param tamy de tipo entero. Es el numero de filas de la matriz.
	* @param incr de tipo double. Es el incremento entre coordenadas de vértices.
	*/
	public Plano( double x0, double y0, int tamX, int tamY, double incr ){
		super();
		this.tamX = tamX;
		this.tamY = tamY;
		this.x0 = x0;
		this.y0 = y0;
		this.incr = incr;
		this.special = new ArrayList<String>();
		this.inicio = new ArrayList<String>();
		this.nodes = new Administrador().getNodos();
		this.admon = new Administrador();
	}

	/**
	* @brief Función Getter del atributo incremento.
	* @return El incremento de coordenadas.
	*/
	public double getIncrement(){
		return this.incr;
	}

	/**
	* @brief Función Getter del atributo tamx.
	* @return El numero de columnas de la matriz.
	*/
	public int getTamX(){
		return this.tamX;
	}

	/**
	* @brief Función Getter del atributo tamy.
	* @return El numero de filas de la matriz.
	*/
	public int getTamY(){
		return this.tamY;
	}

	/**
	* @brief Función que devuelve un vértice al azar.
	* @return Un vértice que pertenezca al grafo.
	*/
	public Vertex getRandomVertex(){
		Random rndm = new Random();
		int r = rndm.nextInt( tamX*tamY );
		
		while( !super.contains( String.valueOf( r ) ) ){

			rndm.setSeed(System.currentTimeMillis());
			r = rndm.nextInt( tamX*tamY );
		}
		return super.getVertex( String.valueOf( r ) );
	}

	/**
	* @brief Función que devuelve el siguiente vértice en la direccion del personaje.
	* @param prev de tipo Vertex. Es el vértice origen.
	* @param num de tipo entero. Es numero de vértices que deseas adelantar.
	* @param orientacion de tipo String. Es la orientaciñon del personaje.
	* @param first de tipo boolean. Es una bandera para elegir el retorno de datos.
	* @return El siguiente vértice n veces delante del origen.
	*			En caso de no encontrar el vértice, verifica first.
	*			Si es True devuelve el origen, Null en el caso contrario.
	*/
	public Vertex getNextVertex( Vertex prev, int num, String orientation, boolean first ){
		String nameNext = new String();
		String nameNext2 = new String();

		switch(orientation){
			case "UP":
				nameNext = String.valueOf( prev.getIntName() - tamX*num );	break;
			case "DOWN":
				nameNext = String.valueOf( prev.getIntName() + tamX*num );	break;
			case "LEFT":
			{
				nameNext = String.valueOf( prev.getIntName() - num );
				nameNext2 = String.valueOf( prev.getIntName() + 25 );
			} break;
			case "RIGHT":
			{
				nameNext = String.valueOf( prev.getIntName() + num );
				nameNext2 = String.valueOf( prev.getIntName() - 25 );
			} break;
		}

		if( prev.getNeighbors().contains( getVertex( nameNext ) ) )
			return getVertex( nameNext );
		else if( prev.getNeighbors().contains( getVertex( nameNext2 ) ) )
			return getVertex( nameNext2 );
		else if( first )
			return prev;
		else
			return null;
	}

	/**
	* @brief Se usan?
	*/
	public void addAllInitNodes( String...args ){
		for( String s: args )
			this.inicio.add(s);
	}
	
	/**
	* @brief Se usa??
	*/
	public void addSpecialNode( String nameV ){
		this.special.add( nameV );
	}

	/**
	* @brief Se usa??
	*/
	public void addAllSpecialNodes( String... args ){
		for(String s: args)
			this.special.add(s);
	}


	/**
	* @brief Función que verifica la existencia del siguiente vértice en la dirección del personaje.
	* @param v de tipo Vertex. Es el vértice origen.
	* @param orient de tipo String. Es la orientación del personaje.
	* @return True si existe. False en caso contrario.
	*/
	public boolean existNextVertex( Vertex v, String orient ){
		
		String nameNext = new String();
		String nameNext2 = new String();

		switch( orient ){
			case "UP":
				nameNext = String.valueOf( v.getIntName() - tamX );	break;
			case "DOWN":
				nameNext = String.valueOf( v.getIntName() + tamX );	break;
			case "LEFT":
			{
				nameNext = String.valueOf( v.getIntName() - 1 );
				nameNext2 = String.valueOf( v.getIntName() + 25 );
			} break;
			case "RIGHT":
			{
				nameNext = String.valueOf( v.getIntName() + 1 );
				nameNext2 = String.valueOf( v.getIntName() - 25 );
			} break;
		}
		//if( nameNext == "300" || nameNext == "299" )
		//	return false;
		if( v.getNeighbors().contains( getVertex( nameNext2 ) ) )
			return true;

		return v.getNeighbors().contains( getVertex( nameNext ) );
	}


	/**
	* @brief Función que guarda en un archivo txt el plano creado.
	*/
	private void guardarPlano(){
		for( Vertex v : super.getMap().values() ){
			this.admon.saveNode( v.toString() );
		}
	}

	/**
	* @brief FUnción que obtiene el plano de un archivo txt previamente creado.
	*/
	public void cargarPlano(){
		//System.out.println("Cargando plano...");
		for( String data : this.admon.getNodes() ){
			String []info = data.split(":");
			super.addVertex( 
				new Vertex( info[0],
					Double.valueOf( info[1] ),
					Double.valueOf( info[2] ),
					Double.valueOf( info[3] ),
					Color.RED) );
		}
		for( String data : this.admon.getEdges() ){
			String []info = data.split(":");
			super.addEdge(info[0], info[1]);
		}
	}

	/**
	* @brief Función que crea un Grafo a partir de una matriz. Sólo se ejcuta una vez.
	*/
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
							addVertex( 
								new Vertex( vertices[i][j], xx, yy, 0, Color.RED) );
						} break;
						case 1:
						{
							addVertex( 
								new Vertex( vertices[i][j], xx, yy, 1, Color.RED) );
						} break;
						case 2:
						{
							addVertex( 
								new Vertex( vertices[i][j], xx, yy, 4, Color.RED) );
						} break;
					}
			}
			xx = x0;
		}
		
		//Une los vertices horizontalmente
		for( i = 0; i < tamY; ++i ){
			for( j = 1; j < tamX; ++j ){
				if(contains(vertices[i][j-1]) && contains(vertices[i][j])){
					super.addEdge( vertices[i][j-1], vertices[i][j] );
					this.admon.saveEdge(vertices[i][j-1] +":"+ vertices[i][j]);
				}
			}
		}
				
		//Une los vertices verticalmente
		for( i = 1; i < tamY; ++i ){
			for( j = 0; j < tamX; ++j ){
				if(contains(vertices[i-1][j]) && contains(vertices[i][j]))
					if( special.contains( vertices[i][j] ) ){
						super.addEdgeDirected(vertices[i][j], vertices[i-1][j]);
						this.admon.saveEdge(vertices[i][j] +":"+ vertices[i-1][j]);
					}
					else{
						super.addEdge( vertices[i-1][j], vertices[i][j] );
						this.admon.saveEdge(vertices[i-1][j] +":"+ vertices[i][j]);
					}
			}
		}

		guardarPlano();		
	}

}