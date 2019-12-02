package unam.fi.poo.objetos;

import unam.fi.poo.estructuras.Vertex;
import javafx.scene.image.ImageView;
import javafx.geometry.Bounds;

public interface Ghost {
	
	public void restart();
	public void stop();
	public void pause();
	public Bounds getBounds();
	public ImageView getSprite();
	public void start();

	public boolean isFear();

	public boolean isToHome();

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
