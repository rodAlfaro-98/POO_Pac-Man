
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
			//v.updateColor("WHITE");
		}

		start.setColor(Colors.WHITE);

		ArrayDeque<Vertex> q = new ArrayDeque<Vertex>();
		q.addFirst(start);

		while(!q.isEmpty()){
			Vertex v = q.pollFirst();
			//v.updateColor("RED");
			
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
				pred = temp.getPredecesor();
			}
		}

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

		BSF( v1 , v2 );

		if(v1 != null && v2 != null){
		
			pila.push( v2.getName() );

			String v2str = v2.getPredecesor();

			while( !v2str.equals( v1.getName() )){
				pila.push( v2str );
				v2str = getVertex( v2str ).getPredecesor();
				//System.out.println("Pila" + pila);
			}

			pila.push( v1.getName() );
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

	public Stack<String> goToDouble(String v1, String v2, int tam){
		
		//initializeVisit();
		
		ArrayList<Stack<String>> movimientos = new ArrayList<Stack<String>>();
		
		goToDoubleVertex(v1,v2,movimientos);
		
		for(int i = 0; i < movimientos.size(); i++){
			
			Stack<String> actual = movimientos.get(i);
			
			if( actual.size()>=(1.2*tam) && actual.peek().equals(v1)){
				
				System.out.println("tam: "+tam+"; tam lista: "+actual.size());
				
				return actual;
			}
			
		}
		
		return null;
		
		
		
	}
	
	public void goToDoubleVertex( String _v1, String _v2, ArrayList<Stack <String>> movimientos){

		Vertex v1 = this.vertices.get(_v1);
		Vertex v2 = this.vertices.get(_v2);

		BSF( v1 , v2);
		
		Stack<String> pila = new Stack<String>();

		if(v1 != null && v2 != null){
		
			pila.push( v2.getName() );

			for( Vertex neighbor : v2.getNeighbors() ){

				if( !neighbor.getName().equals( v1.getName() )){
					pila.push(v2.getName() );
					if(neighbor.getDistance()!= 0)
						gotoVertex( v1, neighbor.getName(), pila);
				}
				movimientos.add( pila );
				//this.tiempos.add(pila.size());
			}
			
			pila.push( v1.getName() );

		}

	}
	
	public void initializeVisit(){
		for(Vertex value : this.vertices.values()){
			//value.setVisited(false);
		}	
	}
	
}
