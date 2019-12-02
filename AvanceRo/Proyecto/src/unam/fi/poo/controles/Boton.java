
package unam.fi.poo.controles;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


public class Boton extends Button {
	public Boton(String nombre, EventHandler<ActionEvent> me){
		super();
		super.setText(nombre);
		super.setOnAction(me);
	}
}
