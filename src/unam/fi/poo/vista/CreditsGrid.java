
package unam.fi.poo.vista;

import java.io.File;
import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

import unam.fi.poo.controles.Etiqueta;
import unam.fi.poo.controles.Boton;
import unam.fi.poo.eventos.ManejadorEventos;

public class CreditsGrid extends GridPane{

	private Etiqueta creditos;
	private Boton regresarBtn;

	/**
	* @brief Constructor del objeto CreditsGrid, aquí generamos la pantalla de Créditos mostrada al usuario
	*/
	public CreditsGrid(){
		super();
		super.setAlignment(Pos.CENTER);
		super.setVgap(20);

		getStylesheets().add( new File("./src/unam/fi/poo/vista/style.css").toURI().toString());

		this.creditos = new Etiqueta(
			"\nRealizado por:"+
			"\n\nAlfaro Dominguez Rodrigo"+
			"\nLópez González Kevin"+
			"\nPOO 2020-1"
			);
		
		this.regresarBtn = new Boton("Menu", ManejadorEventos.getInstance() );

		super.add(crearCajaH( creditos, Pos.CENTER, 10), 0, 0);
		super.add(crearCajaH( regresarBtn, Pos.CENTER, 10), 0, 1);
	}

	/**
	* @brief Getter del atributo MenuButton
	* @return Un objeto de tipo Botón que contiene a SaveButton
	*/
	public Boton getMenuButton(){
		return this.regresarBtn;
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
}