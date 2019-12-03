
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


public class ScoreGrid extends GridPane {
	
	private int score = 0;
	private Etiqueta nameLbl, encabezado;
	private CajaDeTexto nameTxt;
	private Boton registrarBtn, regresarBtn;
	private Administrador admon;
	private HashMap<String, Integer> highScores;

	/**
	* @brief Constructor del objeto ScoreGrid, aquí generamos la pantalla de guardar puntajes que se le mostrará al usuario
	*/
	public ScoreGrid(){
		super.setAlignment(Pos.CENTER);
		super.setHgap(10);
		super.setVgap(20);
		super.setGridLinesVisible(false);
		getStylesheets().add(
			new File("./src/unam/fi/poo/vista/style.css").toURI().toString());
		this.admon = new Administrador();

		this.encabezado = new Etiqueta("Your Score: ");		
		//datos del formulario
		this.nameLbl = new Etiqueta("Name: ");
		this.nameLbl.setAlignment(Pos.CENTER_RIGHT);
		this.nameTxt = new CajaDeTexto();
		this.nameTxt.setMaxWidth(120);
		
		this.registrarBtn = new Boton("Save", ManejadorEventos.getInstance() );
		
		this.regresarBtn = new Boton("Cancel", ManejadorEventos.getInstance() );

		HBox botones = crearCajaH(this.regresarBtn, Pos.CENTER_RIGHT, 5);
		botones.getChildren().add(registrarBtn);
		
		super.add(crearCajaH(encabezado, Pos.TOP_CENTER, 10),0,1,2,1);
		
		super.add(crearCajaH(this.nameLbl, Pos.CENTER_RIGHT, 5),0,0);
		super.add(this.nameTxt,1,0);
	
		super.add(crearCajaH(botones, Pos.TOP_CENTER,10),0,2,2,1);

		this.highScores = new HashMap<String, Integer>();

		loadScore();

	}

	/**
	* @brief Función interna de la aplicación, se activa al dar el botón de save y su finalidad es la de generar las acciones para guardar los puntajes en diferentes estructuras de datos
	*/
	public void saveScore(){

		if( this.nameTxt.getText() != null ){
			if( this.highScores.containsKey(this.nameTxt.getText()) &&
			this.score > this.highScores.get(this.nameTxt.getText()) ){
				this.highScores.replace( this.nameTxt.getText(), this.score );
			}
			else{
				this.highScores.put(this.nameTxt.getText(), this.score);
			}
		}

		getBestFive();
		
		sendScore();

		this.nameTxt.clear();		
	}

	/**
	* @brief Getter del atributo SaveButton
	* @return Un objeto de tipo Botón que contiene a SaveButton
	*/
	public Boton getSaveButton(){
		return this.registrarBtn;
	}

	/**
	* @brief Getter del atributo CancelButton
	* @return Un objeto de tipo Botón que contiene a CancelButton
	*/
	public Boton getCancelButton(){
		return this.regresarBtn;
	}

	/**
	* @brief Función que nos regresa el usuario ingresado por el jugador
	* @return Un objeto de tipo String con el nombre del jugador
	*/
	public String getNewUserName(){
		return this.nameTxt.getText();
	}

	/**
	* @brief Función que nos permite mostrarle al usuario su puntuación
	* @param Un objeto de tipo Integer que contiene la puntuación
	*/
	public void setScore( int score ){
		this.score = score;
		this.encabezado.setText("Your Score: "+this.score);

	}

	/**
	* @brief Función que nos regresa el puntaje del usuario
	* @return Un objeto de tipo Integer con el puntaje del usuario
	*/
	public int getScore(){
		return this.score;
	}
		
	/**
	* @brief Función que ingresa el puntaje del nuevo usuario y nos retorna el puntaje de los cinco mejores usuarios, para esto los ingresa en un HashMap
	*/
	public void getBestFive(){
	
		HashMap<Integer, String> allScores = new HashMap<Integer, String>();
		ArrayList<Integer> toSort = new ArrayList<Integer>();
		
		for(String name : this.highScores.keySet()){
			allScores.put(this.highScores.get(name), name);
			toSort.add(this.highScores.get(name));
		}
		
		Collections.sort(toSort, Comparator.reverseOrder() );
		
		this.highScores.clear();

		for( int i = 0; i < 5; i++ ){
			Integer score = toSort.get( i );
			this.highScores.put( allScores.get( score ), score );
		}
	
	}


	/**
	* @brief Función que nos permite generar un HBox para poder manejar cajas dentro de la ventana
	* @return Un objeto de tipo HBox
	*/
	private HBox crearCajaH(Node nodo, Pos posicion, double espacio){
		HBox hbox = null;
		if(nodo !=null && posicion !=null){
			hbox=new HBox(espacio);
			hbox.setAlignment(posicion);
			hbox.getChildren().add(nodo);
		}
		
		return hbox;
	}
	
	/**
	* @brief Función que hace que nuestro objeto ScoreGrid guarde los puntajes obtenidos por Administrador del archivo HighScore.txt
	*/
	public void loadScore(){
				
		for(Jugador x : this.admon.getScore()){
			this.highScores.put(x.getName(), x.getScore());
		}	
	}
	
	/**
	* @brief Función que envía una arrayList a Administrador para poder guardar los nuevos puntajes en HighScoretxt
	*/
	public void sendScore(){
		
		ArrayList<Jugador> loadScore = new ArrayList<Jugador>();
		
		for(String name : this.highScores.keySet()){
			Jugador x = new Jugador(name, this.highScores.get(name));
			loadScore.add(x);
		}
		
		this.admon.saveScore(loadScore);
		
	}

}