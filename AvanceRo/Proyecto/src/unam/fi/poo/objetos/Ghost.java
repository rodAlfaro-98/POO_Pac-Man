package unam.fi.poo.objetos;

import unam.fi.poo.estructuras.Vertex;
import javafx.scene.image.ImageView;
import javafx.geometry.Bounds;

public interface Ghost {

	public static final int FPS = 4; 		// Cuadros por segundo
	public static final int FRAMES = 2;	// 2 cuadros de animacion
	public static final int VELOCIDAD = 110; // 110 milisegundos por vertice
	public static final int VELOCIDAD_HOME = 60; // 110 milisegundos por vertice
	public static final int TIME_OF_FEAR = 9;  // 9 segundos de miedo
	
	public void restart();
	public void stopGhost();
	public void stop();
	public void pause();
	public Bounds getBounds();
	public ImageView getSprite();
	public void start();

	public boolean isFear();

	public boolean goingToHome();

	public void setState( String st );

	public void setOrientacion();

	public void setPacManVertex( Vertex vp );

	public void setPacManOrientation( String str );

	public void setPacManState( boolean b );

	//public void playOnFearPath();

	//public void playToHomePath();

	public void scatter();
	
	public void playOnChasePath();

	//public void handle( long now );
}
