
package unam.fi.poo.controles;

import javafx.scene.Group;
import unam.fi.poo.eventos.ManejadorEventos;

public class Grupo extends Group{

	private ManejadorEventos me;

	public Grupo( ){
		super();
		this.me = ManejadorEventos.getInstance();
	}

	public ManejadorEventos getManejadorEventos(){
		return this.me;
	}

}
