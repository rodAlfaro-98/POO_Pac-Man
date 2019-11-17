
package unam.fi.poo.vista;


import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import unam.fi.poo.aplicacion.Aplicacion;
import java.io.File;

import unam.fi.poo.controles.Etiqueta;
import unam.fi.poo.controles.Boton;
import unam.fi.poo.eventos.ManejadorEventos;

public class MenuGrid extends GridPane{

	private Etiqueta titleLbl, equipoLbl;
	private Boton playBtn, highScoreBtn, creditsBtn;
	private ImageView pacImage;

	public MenuGrid( ManejadorEventos me ){
		super();
		super.setAlignment(Pos.CENTER);
		//super.setHgap(70);
		super.setVgap(10);
		super.setGridLinesVisible(false);
		getStylesheets().add( new File("./src/unam/fi/poo/vista/style.css").toURI().toString());

		this.playBtn = new Boton("Play", me );

		this.highScoreBtn = new Boton("High Scores", me );

		this.creditsBtn = new Boton("Credits", me );

		this.pacImage = new ImageView(
			new Image(
				new File("./imagenes/pacManTitle1.png").toURI().toString() ));
		this.pacImage.setFitWidth(190);
		this.pacImage.setFitHeight(44);

		super.add(crearCajaH(this.pacImage, Pos.CENTER, 10),0,0);
		super.add(crearCajaH(this.playBtn, Pos.CENTER, 10),0,2);
		super.add(crearCajaH(this.highScoreBtn, Pos.CENTER, 10),0,3);
		super.add(crearCajaH(this.creditsBtn, Pos.CENTER, 10),0,4);
	}

	public Boton getPlayButton(){
		return this.playBtn;
	}

	public Boton getHighScoreButton(){
		return this.highScoreBtn;
	}

	public Boton getCreditsButton(){
		return this.creditsBtn;
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