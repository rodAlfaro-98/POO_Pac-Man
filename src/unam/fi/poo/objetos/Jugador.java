package unam.fi.poo.objetos;

public class Jugador{
	
	private String name;
	private int score;
	
	/**
	* @brief Constructor de la clase Jugador.
	* @param name de tipo String. Es el nombre del usuario.
	* @param score de tipo entero. Es el puntaje obtenido por el usuario.
	*/
	public Jugador(String name, int score){
		this.name = name;
		this.score = score;
	}
	
	/**
	* @brief Setters y Getters de los atributos.
	*/
	public void setName(String name){
		this.name = name;
	}
	public void setScore(int score){
		this.score = score;
	}
	
	public String getName(){
		return this.name;
	}
	public int getScore(){
		return this.score;
	}
	
	public String getDatos(){
		return ""+this.name+":"+this.score+"";
	}	

}