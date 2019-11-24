
package unam.fi.poo.vista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;

import unam.fi.poo.controles.Etiqueta;
import unam.fi.poo.controles.CajaDeTexto;
import unam.fi.poo.controles.Boton;
import unam.fi.poo.eventos.ManejadorEventos;
import unam.fi.poo.objetos.Administrador;
import unam.fi.poo.objetos.Jugador;

public class HighScoreGrid extends GridPane{
	
	private Etiqueta nameLbl, encabezado;
	private Boton regresarBtn;
	private Administrador admon;
	private HashMap<String, Integer> highScores;
	//private ArrayList<Jugador> loadScore;

	public HighScoreGrid( ManejadorEventos me ){
		this.highScores = new HashMap<String, Integer>();
		super.setAlignment(Pos.CENTER);
		super.setHgap(10);
		super.setVgap(20);
		super.setGridLinesVisible(false);
		getStylesheets().add(
			new File("./src/unam/fi/poo/vista/style.css").toURI().toString());
		this.admon = new Administrador();
		loadScore();
		//this.highScores = admon.getMapScores();

		this.encabezado = new Etiqueta("High Scores ");		
		//datos del formulario
		this.nameLbl = new Etiqueta("The Best Five: ");
		this.nameLbl.setAlignment(Pos.CENTER_RIGHT);
		
		this.regresarBtn = new Boton("Regresar", me );

		HBox botones = crearCajaH(this.regresarBtn, Pos.CENTER_RIGHT, 5);
		//botones.getChildren().add(registrarBtn);
		
		super.add(crearCajaH(encabezado, Pos.TOP_CENTER, 10),0,0,2,1);
		
		super.add(crearCajaH(this.nameLbl, Pos.CENTER_RIGHT, 5),0,1);
	
		super.add(crearCajaH(botones, Pos.TOP_CENTER,10),0,3,3,1);
		
		showScores();
	}
	

	public Boton getCancelButton(){
		return this.regresarBtn;
	}
	
	private HBox crearCajaH(Node nodo, Pos posicion, double espacio){
		HBox hbox = null;
		if(nodo !=null && posicion !=null){
			hbox=new HBox(espacio);
			hbox.setAlignment(posicion);
			hbox.getChildren().add(nodo);
		}
		
		return hbox;
	}
	
	public void loadScore(){
		
		ArrayList<Jugador> loadScore = new ArrayList<Jugador>();
		
		loadScore = this.admon.getScore();
		for(Jugador x : loadScore){
			this.highScores.put(x.getName(), x.getScore());
		}	
	}
	
	public void showScores(){
	
		loadScore();
	
		String scores = "";
	
		for(String name : this.highScores.keySet()){
			scores += "Player: "+name+" score: "+this.highScores.get(name)+"\n";
		}
		
	
		super.add(new Etiqueta(scores), 0,2,2,1);
		
	}
	
}