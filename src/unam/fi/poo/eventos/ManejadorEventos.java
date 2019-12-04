
package unam.fi.poo.eventos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import unam.fi.poo.aplicacion.Aplicacion;

public class ManejadorEventos implements EventHandler<ActionEvent>{

	private Aplicacion app;

	//Parámetro de tipo del mismo objeto, estatico, que permite realizar el patrón de diseño Singleton
	public static ManejadorEventos me;

	/**
	* @brief Constructor de la clase ManejadorEventos
	* @param Un objeto de tipo aplicación que será la aplicación de la cual manejaremos los eventos
	*/
	public ManejadorEventos( Aplicacion app ){
		this.app = app;
	}

	/**
	* @brief Función que permite que ManejadroEventos sea Singleton, para esto se hace que sea static
	* @param Un objeto de tipo aplicación que será la aplicación de la cual manejaremos los eventos
	* @return Un objeto de tipo Singleton
	*/
	public static ManejadorEventos getInstance( Aplicacion app ){
		if(me == null){
			me = new ManejadorEventos(app);
		}
		return me;
	}

	/**
	* @brief Función que permite que ManejadroEventos sea Singleton, para esto se hace que sea static
	* @return Un objeto de tipo Singleton
	*/
	public static ManejadorEventos getInstance(){
		return me;
	}

	/**
	* @brief Función que maneja los eventos de la aplicación dependiendo de cual sea el origen de evento ActionEvent que recibe
	* @param Un objeto de tipo ActionEvent que generará un evento
	*/
	public void handle( ActionEvent e ){

		if( app.getMenuGrid().getPlayButton() == e.getSource() ){
			app.setScene( app.getGameScene() );
			app.getGameScene().startObjects();
		}

		else if( app.getMenuGrid().getCreditsButton() == e.getSource() )
			app.setScene( app.getCreditsScene() );

		else if( app.getMenuGrid().getControlsButton() == e.getSource() )
			app.setScene( app.getControlsScene() );

		else if( app.getMenuGrid().getHighScoreButton() == e.getSource() ){
			app.getHighScoreGrid().showScores();
			app.setScene( app.getHighScoreScene() );
		}

		else if( app.getCreditsGrid().getMenuButton() == e.getSource() ||
			app.getScoreGrid().getCancelButton() == e.getSource()  ||
			app.getControlsGrid().getMenuButton() == e.getSource() ){
			app.setScene( app.getMenuScene() );
		}

		else if( app.getScoreGrid().getSaveButton() == e.getSource() ){
			app.getScoreGrid().saveScore();
			app.getGameScene().stopObjects();
			app.setScene( app.getMenuScene() );
		}

		else if( app.getHighScoreGrid().getCancelButton() == e.getSource() ){
			app.setScene( app.getMenuScene() );
		}
	}

	/**
	* @brief Getter del atributo Aplicación
	* @return Un objeto de tipo Aplicación
	*/
	public Aplicacion getApp(){
		return this.app;
	}

	/**
	* @brief Setter del objeto ScoreScene
	* @param Un objeto de tipo ScoreScene
	*/
	public void setScoreScene(){
		app.setScene( app.getScoreScene() );
	}

	/**
	* @brief Setter del objeto MenuScene
	* @param Un objeto de tipo MenuScene
	*/
	public void setMenuScene(){
		app.getGameScene().stopObjects();
		app.setScene( app.getMenuScene() );
	}
}