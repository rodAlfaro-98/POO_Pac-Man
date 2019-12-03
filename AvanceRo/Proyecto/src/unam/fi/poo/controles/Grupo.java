
package unam.fi.poo.controles;

import javafx.scene.Group;
import unam.fi.poo.eventos.ManejadorEventos;

public class Grupo extends Group{

	/**!> Manejador de eventos */
	private ManejadorEventos me;

	/**
	* @brief Constructor del objeto Grupo.
	*/
	public Grupo(){
		super();
		this.me = ManejadorEventos.getInstance();
	}

	/**
	* @brief Getter del atributo me
	* @return Un objeto de tipo ManejadorEventos que contiene a me
	*/
	public ManejadorEventos getManejadorEventos(){
		return this.me;
	}

}
