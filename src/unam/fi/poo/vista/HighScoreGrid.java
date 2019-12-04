
package unam.fi.poo.vista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

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
	
	private Etiqueta nameLbl, encabezado, puntajesLbl, jugadoresLbl;
	private Boton regresarBtn;
	private Administrador admon;
	private HashMap< Integer, String > highScores;

	/**
	* @brief Constructor del objeto HighScoreGrid, aquí generamos la pantalla de Puntajes más altos mostrada al usuario
	*/
	public HighScoreGrid(){
		super.setAlignment(Pos.CENTER);
		super.setHgap(20);
		super.setVgap(5);
		super.setGridLinesVisible(false);
		
		getStylesheets().add(
			new File("./src/unam/fi/poo/vista/style.css").toURI().toString());
		this.admon = new Administrador();

		this.encabezado = new Etiqueta("The Best Five High Scores");		

		this.nameLbl = new Etiqueta("Player:  \t   Score:");
		this.nameLbl.setAlignment(Pos.TOP_CENTER);

		this.puntajesLbl = new Etiqueta("");
		this.puntajesLbl.setAlignment(Pos.CENTER_RIGHT);
		this.puntajesLbl.setMinWidth(92);

		this.jugadoresLbl = new Etiqueta("");
		this.jugadoresLbl.setAlignment(Pos.CENTER_RIGHT);
		this.jugadoresLbl.setMinWidth(92);
		
		this.regresarBtn = new Boton("Regresar", ManejadorEventos.getInstance() );

		this.highScores = new HashMap< Integer, String >();

		super.add(crearCajaH(encabezado, Pos.TOP_CENTER, 10),0,0,2,1);
		
		super.add(crearCajaH(this.nameLbl, Pos.TOP_CENTER, 5),0,1,2,1);
		
		super.add(crearCajaH(this.jugadoresLbl, Pos.TOP_CENTER, 5),0,2);
		super.add( this.puntajesLbl,1,2);

		super.add(crearCajaH(this.regresarBtn, Pos.TOP_CENTER,10),0,3,2,1);
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
		
		for(Jugador x : this.admon.getScore() ){
			this.highScores.put( x.getScore(), x.getName() );
		}	
	}
	
	public void showScores(){
	
		loadScore();

		String scores = "";
		String names = "";
		
		ArrayList<Integer> toSort = new ArrayList<Integer>();
		
		for( Integer score : this.highScores.keySet() ){
			toSort.add( score );
		}
		
		Collections.sort(toSort, Comparator.reverseOrder() );
		
		for( int i = 0; i < 5; i++ ){
			Integer score = toSort.get( i );
			names += this.highScores.get( score )+"\n";
			scores += String.valueOf( score )+"\n";
		}
	
		this.jugadoresLbl.setText( names );
		this.puntajesLbl.setText( scores );
		
	}
	
}