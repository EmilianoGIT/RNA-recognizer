package riconoscimento;

public class Coppia<T1,T2> {

	private T1 inf;
	private T2 sup;
	private int livello;
	
	public Coppia(T1 i, T2 s) {
		this.inf = i;
		this.sup = s;
		this.livello = 0;
	}
	
	public Coppia() {
		
	}

	public T1 getInf() {
		return this.inf;
	}
	
	public T2 getSup() {
		return this.sup;
	}
	
	public int getLivello(){
		return this.livello;
	}

	/*@ normal_behaviour
	  @ requires livello >=0;
	*/
	public void setLivello(int livello) {
		this.livello = livello;
	}
	
	@Override
	public String toString() {
		return "< "+this.inf+", " + this.sup + " > ["+this.livello+"] ";
	}
}
