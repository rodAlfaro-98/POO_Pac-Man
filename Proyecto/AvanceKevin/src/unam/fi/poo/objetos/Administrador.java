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

	//private final static Sring SEPARADOR_INFORMACION=":";
	File nodos, nodosInfo;
	
	public Administrador() {
		this.nodos= new File(RUTA_DATOS + NOMBRE_ARCHIVO_NODOS);
		this.nodosInfo= new File(RUTA_DATOS + NOMBRE_ARCHIVO_NODOS_INFO);
		//setNodos(getNodos());
	}
	
	public void setNodo( String nombre ){				
		try {
			BufferedWriter bw= new BufferedWriter(new FileWriter(nodos,true));			
			bw.write( nombre );
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

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
