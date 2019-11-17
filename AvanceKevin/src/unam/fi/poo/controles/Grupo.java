
package unam.fi.poo.controles;

import javafx.scene.Group;
import unam.fi.poo.eventos.ManejadorEventos;

public class Grupo extends Group{

	private ManejadorEventos me;

	public Grupo( ManejadorEventos me ){
		super();
		this.me = me;
	}

	public ManejadorEventos getManejadorEventos(){
		return this.me;
	}

}
