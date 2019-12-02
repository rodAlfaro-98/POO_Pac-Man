package unam.fi.poo.objetos;

public class Jugador{
	
	private String name;
	private int score;
	
	/**
	* @brief Constructor del objeto Jugador, su fin es únicamente portar datos
	*/
	public Jugador(String name, int score){
		this.name = name;
		this.score = score;
	}
	
	/**
	* @brief Setter del atributo name
	* @param Objeto de tipo String que será el nuevo valor del atributo name
	*/
	public void setName(String name){
		this.name = name;
	}
	
	/**
	* @brief Setter del atributo score
	* @param Objeto de tipo Integer que será el nuevo valor del atributo score
	*/
	public void setScore(int score){
		this.score = score;
	}
	
	/**
	* @brief Getter del atributo name
	* @return Objeto de tipo String que es el valor de name
	*/
	public String getName(){
		return this.name;
	}
	
	/**
	* @brief Getter del atributo score
	* @return Objeto de tipo Integer que es el valor de score
	*/
	public int getScore(){
		return this.score;
	}
	
	/**
	* @brief Función que retorna en una cadena los valres de cada uno de sus atributos
	* @return Objeto de tipo String que es el valor de name
	*/
	public String getDatos(){
		return ""+this.name+":"+this.score+"";
	}	

}