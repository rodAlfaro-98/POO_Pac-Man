package unam.fi.poo.objetos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Administrador {
	
	private final static String RUTA_DATOS="./datos/";
	private final static String NOMBRE_ARCHIVO_NODOS_INFO="NodosInfo.txt";
	private final static String NOMBRE_ARCHIVO_NODOS="Nodos.txt";
	private final static String NOMBRE_ARCHIVO_PLANO="Plano.txt";
	private final static String NOMBRE_ARCHIVO_EDGES="Edges.txt";
	private final static String NOMBRE_ARCHIVO_SCORE="HighScores.txt";

	//private final static Sring SEPARADOR_INFORMACION=":";
	File nodos, nodosInfo, plano, edges, hScore;
	
	/**
	* @brief Constructor del objeto administrador.
	*/
	public Administrador() {
		this.nodos = new File(RUTA_DATOS + NOMBRE_ARCHIVO_NODOS);
		this.plano = new File(RUTA_DATOS + NOMBRE_ARCHIVO_PLANO);
		this.edges = new File(RUTA_DATOS + NOMBRE_ARCHIVO_EDGES);
		this.nodosInfo= new File(RUTA_DATOS + NOMBRE_ARCHIVO_NODOS_INFO);
		this.hScore = new File( RUTA_DATOS + NOMBRE_ARCHIVO_SCORE );
		//setNodos(getNodos());
	}

	/**
	* @brief Función que guarda los puntajes en un archivo de texto.
	* @param scores de tipo ArrayList<Jugador>. Es la lista a guardar.
	*/
	public void saveScore( ArrayList<Jugador> scores ){
		try {
			BufferedWriter bw= new BufferedWriter(new FileWriter( this.hScore ,false));
			for(Jugador x : scores){
				bw.write( x.getDatos() );
				bw.newLine();
				bw.flush();
				//bw.close();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	* @brief Función que obtiene la lista de puntajes de un archivo.
	* @return Lista de puntajes.
	*/
	public ArrayList<Jugador> getScore(){
		
		ArrayList<Jugador> scores = new ArrayList<Jugador>();
		
		try{
			String data = "";
			BufferedReader br;
			File file;

			file = new File(RUTA_DATOS + NOMBRE_ARCHIVO_SCORE );
			br = new BufferedReader( new FileReader(file));
			while((data = br.readLine()) != null && (!data.equals(""))){
				String []info = data.split(":");
				Jugador x = new Jugador(info[0], Integer.valueOf(info[1]));
				//mapScores.put(info[0], Integer.valueOf(info[1]));
				scores.add(x);
			}
			br.close();
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		
		return scores;
	}
	
	/**
	* @brief Función que guarda un nodo en un archivo.
	* @param linea de tipo String. Es la informacion del nodo.
	*/
	public void saveNode( String linea ){				
		try {
			BufferedWriter bw= new BufferedWriter(new FileWriter(plano,true));			
			bw.write( linea );
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	* @brief Función que guarda un arista en un archivo.
	* @param linea de tipo String. Es la informacion del arista.
	*/
	public void saveEdge( String linea ){				
		try {
			BufferedWriter bw= new BufferedWriter(new FileWriter(edges,true));			
			bw.write( linea );
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	* @brief Función que guarda una lista de nodos en un archivo de texto.
	* @param nodeList de tipo ArrayList<String>. Es la lista de nodos.
	*/
	public void setNodos(ArrayList<String> nodeList ){
		try {
			BufferedWriter bw= new BufferedWriter(new FileWriter(nodosInfo,true));			
			for( String s : nodeList ){
				bw.write( s );
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	* @brief Función que obtiene la lista de nodos de un archivo de texto.
	* @return La lista de nodos.
	*/
	public ArrayList<String> getNodosList(){
		ArrayList<String> listNodos = new ArrayList<String>();
		ArrayList<Integer> listNodosI = new ArrayList<Integer>();

		try{
			String data = "";
			BufferedReader br;
			File file;

			file = new File(RUTA_DATOS + NOMBRE_ARCHIVO_NODOS );
			br = new BufferedReader( new FileReader(file));
			while((data = br.readLine()) != null ){
				listNodos.add(data);
			}
			br.close();
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		for(String s: listNodos)
			listNodosI.add(Integer.valueOf(s));

		Collections.sort(listNodos);
		listNodos.clear();

		for(Integer i: listNodosI)
			listNodos.add(String.valueOf(i));
		
		return listNodos;
	}

	public ArrayList<String> getNodes(){
		ArrayList<String> listNodos = new ArrayList<String>();

		try{
			String data = "";
			BufferedReader br;
			File file;

			file = new File(RUTA_DATOS + NOMBRE_ARCHIVO_PLANO );
			br = new BufferedReader( new FileReader(file));
			while((data = br.readLine()) != null ){
				listNodos.add(data);
			}
			br.close();
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		return listNodos;
	}

	public ArrayList<String> getEdges(){
		ArrayList<String> listNodos = new ArrayList<String>();

		try{
			String data = "";
			BufferedReader br;
			File file;

			file = new File(RUTA_DATOS + NOMBRE_ARCHIVO_EDGES );
			br = new BufferedReader( new FileReader(file));
			while((data = br.readLine()) != null ){
				listNodos.add(data);
			}
			br.close();
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		return listNodos;
	}

	public HashMap<String, Integer> getNodos(){
		HashMap<String, Integer> mapNodos = new HashMap<String, Integer>();

		try{
			String data = "";
			BufferedReader br;
			File file;

			file = new File(RUTA_DATOS + NOMBRE_ARCHIVO_NODOS_INFO );
			br = new BufferedReader( new FileReader(file));
			while((data = br.readLine()) != null ){
				String []info = data.split(":");
				mapNodos.put(info[0], Integer.valueOf(info[1]));
			}
			br.close();
		}
		catch( IOException e ){
			e.printStackTrace();
		}

		return mapNodos;
	}
}
