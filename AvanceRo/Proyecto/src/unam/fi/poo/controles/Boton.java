
package unam.fi.poo.controles;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


public class Boton extends Button {
	/**
	* @brief Constructor del objeto Boton.
	* @param nombre de tipo String. Es el nombre del boton.
	* @param me de tipo EventHandler.
	*/
	public Boton(String nombre, EventHandler<ActionEvent> me){
		super();
		super.setText(nombre);
		super.setOnAction(me);
	}
}
