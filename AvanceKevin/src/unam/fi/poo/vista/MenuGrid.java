
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
	private Boton playBtn, highScoreBtn, creditsBtn, controlsBtn;
	private ImageView pacImage;

	/**
	* @brief Constructor del objeto MenuGrid, aquí generamos la pantalla de Menú mostrada al usuario
	*/
	public MenuGrid(){
		super();
		super.setAlignment(Pos.CENTER);
		//super.setHgap(70);
		super.setVgap(5);
		super.setGridLinesVisible(false);
		getStylesheets().add( new File("./src/unam/fi/poo/vista/style.css").toURI().toString());

		this.playBtn = new Boton("Play", ManejadorEventos.getInstance() );

		this.highScoreBtn = new Boton("High Scores", ManejadorEventos.getInstance() );

		this.creditsBtn = new Boton("Credits", ManejadorEventos.getInstance() );
		
		this.controlsBtn = new Boton("Controls", ManejadorEventos.getInstance() );

		this.pacImage = new ImageView(
			new Image(
				new File("./imagenes/pacManTitle1.png").toURI().toString() ));
		this.pacImage.setFitWidth(190);
		this.pacImage.setFitHeight(44);

		super.add(crearCajaH(this.pacImage, Pos.CENTER, 5),0,0);
		super.add(crearCajaH(this.playBtn, Pos.CENTER, 5),0,1);
		super.add(crearCajaH(this.highScoreBtn, Pos.CENTER, 5),0,2);
		super.add(crearCajaH(this.controlsBtn, Pos.CENTER, 5),0,3);
		super.add(crearCajaH(this.creditsBtn, Pos.CENTER, 5),0,4);
	}

	/**
	* @brief Getter del atributo PlayButton
	* @return Un objeto de tipo Botón que contiene a PlayButton
	*/
	public Boton getPlayButton(){
		return this.playBtn;
	}

	/**
	* @brief Getter del atributo HighScoreButton
	* @return Un objeto de tipo Botón que contiene a HighScoreButton
	*/
	public Boton getHighScoreButton(){
		return this.highScoreBtn;
	}

	/**
	* @brief Getter del atributo ControlButton
	* @return Un objeto de tipo Botón que contiene a ControlsButton
	*/
	public Boton getControlsButton(){
		return this.controlsBtn;
	}

	/**
	* @brief Getter del atributo CreditsButton
	* @return Un objeto de tipo Botón que contiene a CreditsButton
	*/
	public Boton getCreditsButton(){
		return this.creditsBtn;
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