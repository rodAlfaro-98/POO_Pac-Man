
package unam.fi.poo.objetos;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.ArrayList;

public class Sprite extends AnimationTimer{

	private ImageView imageV;
	private ArrayList<Image> framesUP;
	private ArrayList<Image> framesDOWN;
	private ArrayList<Image> framesLEFT;
	private ArrayList<Image> framesRIGTH;
	private ArrayList<Image> framesDIE;
	private String nameImage;
	private int numFrames;
	private long lastFrame;
	private int fps = 4;
	private String estado;
	private boolean automatico;

	public Sprite(){}
	
	public Sprite( String _nameFile, int _numFrames, boolean atm, String state, double x, double y ){
		this.nameImage = _nameFile;
		this.numFrames = _numFrames;
		this.automatico = atm;
		this.estado = state;
		this.imageV = new ImageView();
		this.imageV.setX(x);
		this.imageV.setY(y);
		this.framesUP = new ArrayList<Image>();
		this.framesRIGTH = new ArrayList<Image>();
		this.framesDOWN = new ArrayList<Image>();
		this.framesLEFT = new ArrayList<Image>();
		this.framesDIE = new ArrayList<Image>();
		this.lastFrame = System.nanoTime();
		cargarImagenes("UP");
		cargarImagenes("DOWN");
		cargarImagenes("LEFT");
		cargarImagenes("RIGHT");
		cargarImagenes("DIE");
	}

	private void cargarImagenes( String orientacion ){
		File f;
		for( int i = 0; i < numFrames; ++i ){
			f = new File( nameImage + orientacion + String.valueOf(i) + ".png" );
				if( f.exists() )
					switch(orientacion){
						case "RIGHT":
							this.framesRIGTH.add( new Image( f.toURI().toString() ) );
							break;
						case "LEFT":
							this.framesLEFT.add( new Image( f.toURI().toString() ) );
							break;
						case "UP":
							this.framesUP.add( new Image( f.toURI().toString() ) );
							break;
						case "DOWN":
							this.framesDOWN.add( new Image( f.toURI().toString() ) );
							break;
						case "DIE":
							this.framesDIE.add( new Image( f.toURI().toString() ) );
							break;
					}
		}
	}

	public void handle( long now ){
		int frameJump = (int) Math.floor((now - this.lastFrame) / (1000000000 / fps));
		
		if(!automatico){
			switch(estado){
				case "RIGHT":
					this.imageV.setImage( framesRIGTH.get(frameJump % numFrames) );
					break;
				case "LEFT":
					this.imageV.setImage( framesLEFT.get(frameJump % numFrames) );
					break;
				case "UP":
					this.imageV.setImage( framesUP.get(frameJump % numFrames) );
					break;
				case "DOWN":
					this.imageV.setImage( framesDOWN.get(frameJump % numFrames) );
					break;
				case "DIE":
					this.imageV.setImage( framesDIE.get(frameJump % numFrames) );
			}
		}
		else{
			this.imageV.setImage( framesRIGTH.get(frameJump % numFrames) );
		}

		//System.out.println(frameJump%numFrames);
	}

	public ImageView getImageV(){
		return this.imageV;
	}

	public String getEstado(){
		return this.estado;
	}

	public void setEstado( String est ){
		this.estado = est;
	}
}