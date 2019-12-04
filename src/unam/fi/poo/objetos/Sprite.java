
package unam.fi.poo.objetos;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class Sprite{

	private final String RUTA_IMAGEN = "./imagenes/Clasic/";
	private String name;
	private int numFramesA, numFramesD;
	private long lastFrame;
	private int fps;
	private ArrayList<Image> framesUP;
	private ArrayList<Image> framesDOWN;
	private ArrayList<Image> framesLEFT;
	private ArrayList<Image> framesRIGTH;
	private ArrayList<Image> framesFEAR;
	private ArrayList<Image> framesDIE;
	private ArrayList<Image> framesEYES;
	private Image gameOver, ready;

	/**
	* @brief Constructor vacío de la clase.
	*/
	public Sprite(){}
	
	/**
	* @brief Constructor de la clase.
	* @param _name de tipo String. Es el nombre con el que buscará las imagenes.
	* @param _numFramesO de tipo entero. Es el numero de imagenes que ocupará para la animación cuando este vivo.
	* @param _numFramesO de tipo entero. Es el numero de imagenes que ocupará para la animación cuando este muerto.
	* @param _fps de tipo entero. Es el numero de imagenes por segundo.
	*/
	public Sprite( String _name, int _numFramesA, int _numFramesD, int _fps){
		this.name = _name;
		this.numFramesA = _numFramesA;
		this.numFramesD = _numFramesD;

		this.framesUP = new ArrayList<Image>();
		this.framesRIGTH = new ArrayList<Image>();
		this.framesDOWN = new ArrayList<Image>();
		this.framesLEFT = new ArrayList<Image>();
		this.framesFEAR = new ArrayList<Image>();
		this.framesDIE = new ArrayList<Image>();
		this.framesEYES = new ArrayList<Image>();

		this.lastFrame = System.nanoTime();
		this.fps = _fps;

		cargarMensajes();
		cargarImagenes("_UP", numFramesA);
		cargarImagenes("_DOWN", numFramesA);
		cargarImagenes("_LEFT", numFramesA);
		cargarImagenes("_RIGHT", numFramesA);
		cargarGhostFear( numFramesD );
		cargarGhostEyes( numFramesD );
		cargarImagenes("_DIE", numFramesD );
	}

	/**
	* @brief Función que carga los mensajes al programa.
	*/
	private void cargarMensajes(){
		try{
			File f = new File( RUTA_IMAGEN + "ready.png" );
			this.ready = new Image(f.toURI().toString() );
			
			f = new File( RUTA_IMAGEN + "gameover.png" );
			this.gameOver = new Image(f.toURI().toString() );
		}
		catch( Exception e){System.out.println("Error al cargar imagenes."); }
	}

	/**
	* @brief Función que carga las imagenes al programa.
	* @param orientación de tipo String. Es la orientación de la imagen.
	* @param numF de tipo entero. Es el numero de imágenes que buscará.
	*/
	private void cargarImagenes( String orientacion, int numF ){
		File f;
		for( int i = 0; i < numF; ++i ){
			f = new File( RUTA_IMAGEN + name + orientacion + String.valueOf(i) + ".png" );
				if( f.exists() )
					switch(orientacion){
						case "_RIGHT":
							this.framesRIGTH.add( new Image( f.toURI().toString() ) );
							break;
						case "_LEFT":
							this.framesLEFT.add( new Image( f.toURI().toString() ) );
							break;
						case "_UP":
							this.framesUP.add( new Image( f.toURI().toString() ) );
							break;
						case "_DOWN":
							this.framesDOWN.add( new Image( f.toURI().toString() ) );
							break;
						case "_DIE":
							this.framesDIE.add( new Image( f.toURI().toString() ) );
							break;
					}
		}
	}

	/**
	* @brief Función que carga las imágenes especiales del Fantasma. Estado FEAR.
	* @param numF de tipo entero. Es el número de imágenes.
	*/
	private void cargarGhostFear( int numF ){
		File f;
		for( int i = 0; i < numF; ++i ){
			f = new File( RUTA_IMAGEN + "GHOST_FEAR" + String.valueOf(i) + ".png" );
			if( f.exists() )
				this.framesFEAR.add( new Image( f.toURI().toString() ) );
		}
	}

	/**
	* @brief Función que carga las imágenes especiales del Fantasma. Estado HOME.
	* @param numF de tipo entero. Es el número de imágenes.
	*/
	private void cargarGhostEyes( int numF ){
		File f;
		for( int i = 0; i < numF; ++i ){
			f = new File( RUTA_IMAGEN + "EYES_" + String.valueOf(i) + ".png" );
			if( f.exists() )
				this.framesEYES.add( new Image( f.toURI().toString() ) );
		}
	}

	/**
	* @brief Función Getter que devuelve una imagen.
	* @return La imagen del mensaje READY.
	*/
	public Image getReadyImage(){
		return this.ready;
	}

	/**
	* @brief Función Getter que devuelve una imagen.
	* @return La imagen del mensaje GAMEOVER.
	*/
	public Image getGameOverImage(){
		return this.gameOver;
	}

	/**
	* @brief Función Getter que devuelve una imagen correspondiente a la animación de la muerte del personaje.
	* @param index de tipo entero. Es el índice de la imagen.
	* @return La imagen especificada por el índice.
	*/
	public Image getDieImage( int index ){
		return this.framesDIE.get( index );
	}

	/**
	* @brief Función Getter que devuelve una imagen para representar las vidas del PacMan.
	* @return Una imagen del PacMan.
	*/
	public Image getLiveImage(){
		return framesRIGTH.get(1);
	}

	/**
	* @brief Función que devuelve 2 imágenes especiales del Fantasma.
	* @param now de tipo entero. Es el tiempo en segundos, se usa como referencia para iterar las imagenes.
	* @return Una imagen del fantasma cuando tiene miedo. 
	**/
	public Image getImageFearBlue( int now ){
		return framesFEAR.get( ( now % numFramesA ) * 2 );
	}

	/**
	* @brief Función que devuelve una imagen de los ojos del fantasma.
	* @param orientacion de tipo String. Es la orientación del fantasma.
	* @return Una imagen de los ojos del fantasma en la dirección especificada.
	*/
	public Image getImageEyes( String orientacion ){
		switch( orientacion ){
			case "UP":
				return framesEYES.get(0);
			case "DOWN":
				return framesEYES.get(1);
			case "RIGHT":
				return framesEYES.get(2);
			case "LEFT":
				return framesEYES.get(3);
		}
		return null;
	}

	/**
	* @brief Función que devuelve una imagen del personaje según su orientación y/o estado.
	* @param status de tipo String. Es el estado/orientación del personaje.
	* @param now de tipo long. Es el tiempo en nano segundos, utilizado como referencia para iterar las imágenes.
	* @return La imagen del personaje segun su estado.
	*/
	public Image getImage( String status, long now ){
		
		int frameJump = (int) Math.floor(( now - this.lastFrame) / (1000000000 / fps));

		switch( status ){
			case "RIGHT":
				return framesRIGTH.get(frameJump % numFramesA);
			case "LEFT":
				return framesLEFT.get(frameJump % numFramesA);
			case "UP":
				return framesUP.get(frameJump % numFramesA);
			case "DOWN":
				return framesDOWN.get(frameJump % numFramesA);
			case "FEAR":
				return framesFEAR.get(frameJump % numFramesD );
			case "DIE":
				return framesDIE.get(frameJump % numFramesD );
		}
		return null;
	}


}