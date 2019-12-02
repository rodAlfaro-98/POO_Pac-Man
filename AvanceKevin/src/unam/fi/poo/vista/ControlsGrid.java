
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

public class ControlsGrid extends GridPane{

	private Etiqueta accionLbl, teclaLbl;
	private Boton regresarBtn;

	/**
	* @brief Constructor del objeto ControlsGrid, aquí generamos la pantalla de Créditos mostrada al usuario
	*/
	public ControlsGrid(){
		super();
		super.setAlignment(Pos.CENTER);
		super.setVgap(10);

		getStylesheets().add( new File("./src/unam/fi/poo/vista/style.css").toURI().toString());

		this.accionLbl = new Etiqueta(
			"COMMAND"+
			"\nUP:"+
			"\nDOWN:"+
			"\nRIGTH:"+
			"\nLEFT:"+
			"\nSTART:"+
			"\nPAUSE:"+
			"\nQUIT:"
			);
		this.accionLbl.setAlignment(Pos.CENTER_RIGHT);

		this.teclaLbl = new Etiqueta(
			"KEY"+
			"\nUP ARROW"+
			"\nDOWN ARROW"+
			"\nRIGTH ARROW"+
			"\nLEFT ARROW"+
			"\nENTER"+
			"\nP"+
			"\nQ"
			);
		this.teclaLbl.setAlignment(Pos.CENTER_RIGHT);
		
		this.regresarBtn = new Boton("Menu", ManejadorEventos.getInstance() );

		super.add(crearCajaH( this.accionLbl, Pos.CENTER, 10), 0, 0);
		super.add( this.teclaLbl, 1,0 );
		super.add(crearCajaH( regresarBtn, Pos.CENTER, 10), 0, 1, 2, 1);
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