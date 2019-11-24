
package unam.fi.poo.eventos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import unam.fi.poo.aplicacion.Aplicacion;

public class ManejadorEventos implements EventHandler<ActionEvent>{

	private Aplicacion app;

	public ManejadorEventos( Aplicacion app ){
		this.app = app;
	}

	public Aplicacion getApp(){
		return this.app;
	}

	public void handle( ActionEvent e ){

		if( app.getMenuGrid().getPlayButton() == e.getSource() ){
			app.setScene( app.getGameScene() );
			app.getGameScene().startObjects();
		}

		else if( app.getMenuGrid().getCreditsButton() == e.getSource() )
			app.setScene( app.getCreditsScene() );

		else if( app.getMenuGrid().getHighScoreButton() == e.getSource() )
			app.setScene( app.getHighScoreScene() );

		else if( app.getCreditsGrid().getMenuButton() == e.getSource() ||
			app.getScoreGrid().getCancelButton() == e.getSource() ){
			app.setScene( app.getMenuScene() );
		}

		else if( app.getScoreGrid().getSaveButton() == e.getSource() ){
			app.getScoreGrid().saveScore();
			app.setScene( app.getMenuScene() );
		}
		
		else if( app.getHighScoreGrid().getCancelButton() == e.getSource() ){
			app.setScene( app.getMenuScene() );
		}
	}

	public void setSceneScore(){
		app.setScene( app.getScoreScene() );
	}
}