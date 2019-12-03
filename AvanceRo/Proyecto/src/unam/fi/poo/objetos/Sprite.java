
package unam.fi.poo.objetos;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class Sprite{

	private final String RUTA_IMAGEN = "./imagenes/Clasic/";
	private String name;
	private int numFramesO, numFramesD;
	private long lastFrame;
	private int fps;
	private ArrayList<Image> framesUP;
	private ArrayList<Image> framesDOWN;
	private ArrayList<Image> framesLEFT;
	private ArrayList<Image> framesRIGTH;
	private ArrayList<Image> framesFEAR;
	private ArrayList<Image> framesDIE;
	private ArrayList<Image> framesEYES;

	public Sprite(){}
	
	public Sprite( String _name, int _numFramesO, int _numFramesD, int _fps){
		this.name = _name;
		this.numFramesO = _numFramesO;
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


		cargarImagenes("_UP", numFramesO);
		cargarImagenes("_DOWN", numFramesO);
		cargarImagenes("_LEFT", numFramesO);
		cargarImagenes("_RIGHT", numFramesO);
		cargarGhostFear( numFramesD );
		cargarGhostEyes( numFramesD );
		cargarImagenes("_DIE", numFramesD );

	}

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

	private void cargarGhostFear( int numF ){
		File f;
		for( int i = 0; i < numF; ++i ){
			f = new File( RUTA_IMAGEN + "GHOST_FEAR" + String.valueOf(i) + ".png" );
			if( f.exists() )
				this.framesFEAR.add( new Image( f.toURI().toString() ) );
		}
	}

	private void cargarGhostEyes( int numF ){
		File f;
		for( int i = 0; i < numF; ++i ){
			f = new File( RUTA_IMAGEN + "EYES_" + String.valueOf(i) + ".png" );
			if( f.exists() )
				this.framesEYES.add( new Image( f.toURI().toString() ) );
		}
	}

	public Image getDieImage( int index ){
		return this.framesDIE.get( index );
	}

	public Image getLiveImage(){
		return framesLEFT.get(1);
	}

	public Image getImageFearBlue( int now ){
		return framesFEAR.get( ( now % numFramesO ) * 2 );
	}

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

	public Image getImage( String status, long now ){
		
		int frameJump = (int) Math.floor(( now - this.lastFrame) / (1000000000 / fps));

		switch( status ){
			case "RIGHT":
				return framesRIGTH.get(frameJump % numFramesO);
			case "LEFT":
				return framesLEFT.get(frameJump % numFramesO);
			case "UP":
				return framesUP.get(frameJump % numFramesO);
			case "DOWN":
				return framesDOWN.get(frameJump % numFramesO);
			case "FEAR":
				return framesFEAR.get(frameJump % numFramesD );
			case "DIE":
				return framesDIE.get(frameJump % numFramesD );
		}
		return null;
	}


}