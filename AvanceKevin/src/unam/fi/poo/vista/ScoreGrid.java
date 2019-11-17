
package unam.fi.poo.vista;

import java.util.ArrayList;
import java.util.HashMap;

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

public class ScoreGrid extends GridPane {
	
	private int score = 0;
	private Etiqueta nameLbl, encabezado;
	private CajaDeTexto nameTxt;
	private Boton registrarBtn, regresarBtn;
	private Administrador admon;
	private HashMap<String, Integer> highScores;

	public ScoreGrid( ManejadorEventos me ){
		super.setAlignment(Pos.CENTER);
		super.setHgap(10);
		super.setVgap(20);
		super.setGridLinesVisible(false);
		getStylesheets().add(
			new File("./src/unam/fi/poo/vista/style.css").toURI().toString());
		this.admon = new Administrador();
		this.highScores = admon.getMapScores();

		this.encabezado = new Etiqueta("Your Score: ");		
		//datos del formulario
		this.nameLbl = new Etiqueta("Name: ");
		this.nameLbl.setAlignment(Pos.CENTER_RIGHT);
		this.nameTxt = new CajaDeTexto();
		
		this.registrarBtn = new Boton("Save", me );
		
		this.regresarBtn = new Boton("Cancel", me );

		HBox botones = crearCajaH(this.regresarBtn, Pos.CENTER_RIGHT, 5);
		botones.getChildren().add(registrarBtn);
		
		super.add(crearCajaH(encabezado, Pos.TOP_CENTER, 10),0,1,2,1);
		
		super.add(crearCajaH(this.nameLbl, Pos.CENTER_RIGHT, 5),0,0);
		super.add(this.nameTxt,1,0);
	
		super.add(crearCajaH(botones, Pos.TOP_CENTER,10),0,2,2,1);
	}

	public void saveScore(){

		if( this.nameTxt.getText() != null )
			if( this.highScores.containsKey(this.nameTxt.getText()) )
				this.highScores.replace( this.nameTxt.getText(), this.score );
			else
				this.highScores.put(this.nameTxt.getText(), this.score);

		//this.admon.saveScore( String.valueOf( this.score ) );
		System.out.println( this.highScores);
	}

	public Boton getSaveButton(){
		return this.registrarBtn;
	}

	public Boton getCancelButton(){
		return this.regresarBtn;
	}

	public String getNewUserName(){
		return this.nameTxt.getText();
	}

	public void setScore( int score ){
		this.score = score;
		this.encabezado.setText("Your Score: "+this.score);

	}

	public int getScore(){
		return this.score;
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
}
