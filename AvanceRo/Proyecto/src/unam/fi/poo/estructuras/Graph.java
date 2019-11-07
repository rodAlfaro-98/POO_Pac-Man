
package unam.fi.poo.estructuras;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class Graph{

	private HashMap<String, Vertex> vertices;
	private static int time;

	private ArrayList<Integer> tiempos = new ArrayList<Integer>();
	private ArrayList<ArrayDeque<String>> movimientos = new ArrayList<>();

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
		}
	}

	public void BSF(Vertex start){
		for( Vertex v: this.vertices.values()){
			v.setColor( Colors.BLACK);
			v.setDistance(0);
			v.setPredecesor("Nil");
		}

		start.setColor(Colors.WHITE);

		ArrayDeque<Vertex> q = new ArrayDeque<Vertex>();
		q.addFirst(start);

		while(!q.isEmpty()){
			Vertex v = q.pollFirst();
			
			for( Vertex w: v.getNeighbors() ){

				if(w.getColor() == Colors.BLACK){
					w.setColor(Colors.GRAY);
					w.setDistance(v.getDistance()+1);
					w.setPredecesor(v.getName());
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

	public void updateColor( ArrayDeque<String> pila){
		
		if( pila != null){

			while(!pila.isEmpty()){

				Vertex v1 = this.vertices.get( pila.pollFirst() );
				
				if(v1 != null ){
					v1.updateColor();
				}
			}
		}
	}

	private void gotoVertex(Vertex v1, String _v2, ArrayDeque<String> pila){

		Vertex v2 = this.vertices.get(_v2);

		if(v1 != null && v2 != null){

			if( !v2.getPredecesor().equals( v1.getName() )){
				pila.push(v2.getPredecesor() );
				gotoVertex( v1, v2.getPredecesor(), pila);
			}
		}
	}

	public ArrayDeque<String> goToVertex( String _v1, String _v2){

		Vertex v1 = this.vertices.get(_v1);
		Vertex v2 = this.vertices.get(_v2);

		BSF( v1 );
		
		ArrayDeque<String> pila = new ArrayDeque<String>();

		if(v1 != null && v2 != null){
		
			pila.push( v2.getName() );

			if( !v2.getPredecesor().equals( v1.getName() )){
				pila.push(v2.getPredecesor() );
				gotoVertex( v1, v2.getPredecesor(), pila);
			}

			pila.push( v1.getName() );
		}

		return pila;

	}
	
	public ArrayDeque<String> goToDouble(String v1, String v2, int tam){
		
		initializeVisit();
		
		goToDoubleVertex(v1,v2);
		
		for(int i = 0; i < this.movimientos.size(); i++){
			
			ArrayDeque<String> actual = this.movimientos.get(i);
			
			if( actual.size()>=(2*tam) && (actual.getFirst() == v1)){
				
				//System.out.println("tam: "+tam+"; tam lista: "+actual.size());
				
				return actual;
			}
			
		}
		
		return null;
		
		
		
	}
	
	public void goToDoubleVertex( String _v1, String _v2){

		Vertex v1 = this.vertices.get(_v1);
		Vertex v2 = this.vertices.get(_v2);

		BSF( v1 );
		
		ArrayDeque<String> pila = new ArrayDeque<String>();

		if(v1 != null && v2 != null){
		
			pila.push( v2.getName() );

			for( Vertex neighbor : v2.getNeighbors() ){

				if( !neighbor.getName().equals( v1.getName() )){
					pila.push(v2.getName() );
					gotoVertex( v1, neighbor.getName(), pila);
				}
				this.movimientos.add( pila );
				this.tiempos.add(pila.size());
			}
			
			pila.push( v1.getName() );

		}

	}
	
	public void initializeVisit(){
		for(Vertex value : this.vertices.values()){
			value.setVisited(false);
		}	
	}
	
}
