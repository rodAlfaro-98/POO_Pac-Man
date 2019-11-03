
package unam.fi.poo.objetos;

public class Hilo extends Thread{
//public class Hilo implements Runnable{
	long min;

	public Hilo( long _min ){
		this.min = _min;
	}

	public void run(){
		System.out.println(getName());
		for( int i = 0; i < min; ++i )
			System.out.println(i);
	}
}