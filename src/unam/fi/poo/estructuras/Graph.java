
package unam.fi.poo.estructuras;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Stack;

public class Graph{

	/**>!HashMap que contiene a los vértices.*/
	private HashMap<String, Vertex> vertices;

	/**>!Tiempo de descubrimiento de los nodos.*/
	private static int time;

	/**
	* @brief Constructor del objeto Grafo.
	*/
	public Graph(){
		this.vertices = new HashMap<String, Vertex>();
	}

	/**
	* @brief Funcion que verifica la existencia de un vertice en el grafo.
	* @param name de tipo String. Nombre del vértice a buscar.
	* @return True si el grafo contiene al vértice. False en caso contrario.
	*/
	public boolean contains( String name ){
		return this.vertices.containsKey(name);
	}

	/**
	* @brief Función que agrega un nuevo vértice al grafo.
	* @param v de tipo Vertex. Es el vértice a agregar.
	* @return True si el vértice se agregó correctamente al grafo. False en caso contrario.
	*/
	public boolean addVertex(Vertex v){
		return (this.vertices.put(v.getName(), v) != null);
	}

	/**
	* @brief Función que une dos vértices.
	* @param edge1 de tipo String. Es el nombre del primer vértice.
	* @param edge2 de tipo String. Es el nombre del segundo vértice.
	* @return True si la unión se realizó con exito. Falso en caso contrario.
	*/
	public boolean addEdge(String edge1, String edge2){

		Vertex v1 = this.vertices.get(edge1);
		Vertex v2 = this.vertices.get(edge2);

		if(v1 != null && v2 != null){
			v1.addNeighbor(v2);
			v2.addNeighbor(v1);

			v1.addLine(v2);
			
			return true;
		}

		return false;
	}

	/**
	* @brief Función que une de manera dirigida dos vértices.
	* @param edge1 de tipo String. Es el nombre del primer vértice.
	* @param edge2 de tipo String. Es el nombre del segundo vértice.
	* @return True si la unión se realizó con exito. Falso en caso contrario.
	*/
	public boolean addEdgeDirected(String edge1, String edge2){

		Vertex v1 = this.vertices.get(edge1);
		Vertex v2 = this.vertices.get(edge2);

		if(v1 != null && v2 != null){
			v1.addNeighbor(v2);
			v1.addLine(v2);
			
			return true;
		}

		return false;
	}

	/**
	* @brief Fución que devuelve el número de vértices que contiene el grafo.
	* @return El número de vértices en el grafo.
	*/
	public int size(){
		return this.vertices.size();
	}

	/**
	* @brief Función que busca el vértice especificado y lo devuelve.
	* @param vName de tipo String. Es el nombre del vértice.
	* @return El vértice especificado.
	*/
	public Vertex getVertex(String vName){
		return this.vertices.get(vName);
	}

	/**
	* @brief Función que busca el vértice especificado y lo devuelve.
	* @param vName de tipo entero. Es el nombre del vértice.
	* @return El vértice especificado.
	*/
	public Vertex getVertex(int vName){
		return this.vertices.get(String.valueOf( vName ));
	}

	/**
	* @brief Función que devuelve el Mapa de vértices.
	* @return El mapa de vértices.
	*/
	public HashMap<String, Vertex> getMap(){
		return this.vertices;
	}

	/**
	* @brief Función que imprime los datos de los vértices.
	*/
	public void print(){
		System.out.println();
		for( Vertex v : this.vertices.values()){
			v.print();
			System.out.println();
			//v.printNeighbors();
		}
	}

	/**
	* @brief Algoritmo de Búsqueda Por Anchura.
	* @param start de tipo Vertex. Es el vértice a partir del cual se empieza a buscar.
	* @param end de tipo Vertex. Es el vértice a buscar.
	*/
	public void BSF(Vertex start, Vertex end ){
		for( Vertex v: this.vertices.values()){
			v.setColor( Colors.BLACK);
			v.setDistance(0);
			v.setPredecesor("Nil");
			//v.updateColor("RED");
		}

		start.setColor(Colors.WHITE);

		ArrayDeque<Vertex> q = new ArrayDeque<Vertex>();
		q.addFirst(start);

		while(!q.isEmpty()){
			Vertex v = q.pollFirst();
			//v.updateColor("WHITE");
			if( start != end )
				if( v == end ) return;

			for( Vertex w: v.getNeighbors() ){

				if(w.getColor() == Colors.BLACK){
					w.setColor(Colors.GRAY);
					w.setDistance(v.getDistance()+1);
					w.setPredecesor(v.getName());
					//w.updateColor("RED");
					q.addLast(w);
				}
			}
			v.setColor(Colors.WHITE);
		}
	}


	/**
	* @brief Función recursiva, auxiliar para DFS
	* @param v de tipo Vertex. Es el vértice a evaluar.
	*/
	public void dfsTraverse( Vertex v ){

		v.setDiscoveryTime( ++this.time );
		v.setColor(Colors.GRAY);

		for( Vertex u : v.getNeighbors() ){
			if( u.getColor() == Colors.BLACK ){
				u.setPredecesor( v.getName() );
				dfsTraverse( u );
			}
		}
		v.setColor( Colors.WHITE );
		v.setFinishTime( ++this.time );
	}

	/**
	* @brief Algoritmo de Búsqueda en Profundidad.
	* @param start de tipo Vertex. Es el vértice a partir del cuél se empieza la búsqueda.
	*/
	public void DFS( Vertex start ){

		this.time = 0;

		for( Vertex v : this.vertices.values() ){
			v.setColor( Colors.BLACK );
			//v.setDistance( 0 );
			v.setPredecesor( "Nil" );
			v.setFinishTime( 0 );
			v.setDiscoveryTime( 0 );
		}

		dfsTraverse( start );	
	}

	/**
	* @brief Función que busca el siguiente vértice del camino entre dos vértices.
	* @param v1 de tipo Vertex. Es el vértice origen.
	* @param v2 de tipo Vertex. Es el vértice destino
	* @return El siguiente vértice de origen.
	*/
	public Vertex goToNextVertexInPath( Vertex v1, Vertex v2 ){

		Vertex temp = new Vertex();

		BSF( v1 , v2 );
		
		if( v1 != null && v2 != null ){

			temp = v2;
			String pred = temp.getPredecesor();

			while( !pred.equals( v1.getName() ) ){
				temp = getVertex( pred );
				if( temp != null)
					pred = temp.getPredecesor();
				else
					break;
			}
		}
		//if( temp !=)
		return temp;
	}

	/**
	* @brief Función que busca el vértice que tenga el doble de la distancia especificada.
	* @param v1 de tipo Vertex. Es el vértice a partir del cual se empieza a buscar.
	* @param blinkyLen de tipo entero. Es la distancia a comparar.
	* @return El vértice con el doble de distancia desde v1.
	*/
	public Vertex goToDoubleVertexInPath( Vertex v1, int blinkyLen ){

		//BSF( v1 );

		if( v1 != null ){
			for( Vertex vN : this.vertices.values() ){
				if( vN.getDistance() == ( 2 * blinkyLen ) ){
					return vN;
				}
			}
		}
		return v1;
	}

	/**
	* @brief Función que devuelve el camino más corto entre dos vértices.
	* @param _v1 de tipo Vertex. Es el vértice origen.
	* @param _v2 de tipo Vertex. Es el vértice destino.
	* @return Una pila con los nombres de los vértices que conforman el camino.
	*/
	public Stack<String> getPathTo( Vertex v1, Vertex v2 ){
		Stack<String> pila = new Stack<String>();

		//Vertex v1 = this.vertices.get(_v1);
		//Vertex v2 = this.vertices.get(_v2);

		if(v1 != null && v2 != null){

			BSF( v1 , v2 );
		
			pila.push( v2.getName() );

			String v2str = v2.getPredecesor();

			while( !v2str.equals( v1.getName() )){
				pila.push( v2str );
				if( getVertex( v2str ) != null )
					v2str = getVertex( v2str ).getPredecesor();
				else
					break;
				//System.out.println("Pila" + pila);
			}
			//pila.push( v1.getName() );
		}

		return pila;

	}
	
	/**
	* @brief Función recursiva auxiliar.
	* @param v1 de tipo Vertex. Es el vértice destino.
	* @param _v2 de tipo String. Es el nombre del vértice a evaluar.
	* @param pila de tipo Stack. Es la pila donde se guardan los nombres de los vértices.
	*/
	private void gotoVertex(Vertex v1, String _v2, Stack<String> pila){

		Vertex v2 = this.vertices.get(_v2);

		if(v1 != null && v2 != null){

			if( !v2.getPredecesor().equals( v1.getName() )){
				pila.push(v2.getPredecesor() );
				gotoVertex( v1, v2.getPredecesor(), pila);
			}
		}
	}

	/**
	* @brief La vamos a usar??
	*
	*/
	public Stack<String> getDoublePathTo( Vertex v1, Vertex v2, int tam ){

		Stack<String> pila = new Stack<String>();
		ArrayList<Stack<String>> lista = new ArrayList<>();

		if( v1 == v2 ) return pila;

		//Vertex v1 = this.vertices.get(_v1);
		//Vertex v2 = this.vertices.get(_v2);

		BSF( v1, v2 );
		
		if(v1 != null && v2 != null){
		
			pila.push( v2.getName() );

			for( Vertex neighbor : v2.getNeighbors() ){
				
				Stack<String> path = new Stack<String>();

				String v2str = v2.getPredecesor();

				while( !v2str.equals( v1.getName() )){
					path.push( v2str );
					pila.push( v2str );
					if( getVertex( v2str ) != null )
						v2str = getVertex( v2str ).getPredecesor();
					else
						break;
				}
				path.push( v2str );
				pila.push( v2str );

				lista.add(path);
			}
		}
		return pila;

	}
	
}
