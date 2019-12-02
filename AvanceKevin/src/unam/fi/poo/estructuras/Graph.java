
package unam.fi.poo.estructuras;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Stack;

public class Graph{

	private HashMap<String, Vertex> vertices;
	private static int time;

	public Graph(){
		this.vertices = new HashMap<String, Vertex>();
	}

	public boolean contains( String name ){
		return this.vertices.containsKey(name);
	}

	public boolean addVertex(Vertex v){
		return (this.vertices.put(v.getName(), v) != null);
	}

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

	public int size(){
		return this.vertices.size();
	}

	public Vertex getVertex(String vName){
		return this.vertices.get(vName);
	}

	public Vertex getVertex(int vName){
		return this.vertices.get(String.valueOf( vName ));
	}

	public HashMap<String, Vertex> getMap(){
		return this.vertices;
	}

	public void print(){
		System.out.println();
		for( Vertex v : this.vertices.values()){
			v.print();
			System.out.println();
			//v.printNeighbors();
		}
	}

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

	public Stack<String> goToVertex( String _v1, String _v2 ){
		Stack<String> pila = new Stack<String>();


		Vertex v1 = this.vertices.get(_v1);
		Vertex v2 = this.vertices.get(_v2);

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
	
	private void gotoVertex(Vertex v1, String _v2, Stack<String> pila){

		Vertex v2 = this.vertices.get(_v2);

		if(v1 != null && v2 != null){

			if( !v2.getPredecesor().equals( v1.getName() )){
				pila.push(v2.getPredecesor() );
				gotoVertex( v1, v2.getPredecesor(), pila);
			}
		}
	}

	public Stack<String> goToDoubleVertex( String _v1, String _v2, int tam ){

		Stack<String> pila = new Stack<String>();
		ArrayList<Stack<String>> lista = new ArrayList<>();

		if( _v1.equals( _v2 ) ) return pila;

		Vertex v1 = this.vertices.get(_v1);
		Vertex v2 = this.vertices.get(_v2);

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
