package unam.fi.poo.objetos;

public class Jugador{
	
	private String name;
	private int score;
	
	public Jugador(String name, int score){
		this.name = name;
		this.score = score;
	}
	
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