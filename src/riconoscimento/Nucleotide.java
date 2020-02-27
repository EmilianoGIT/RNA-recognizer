package riconoscimento;

public class Nucleotide<T1, T2> {
	private T1 tipo;
	private T2 indice;
	
	public Nucleotide(T1 tipo, T2 indice) {
		this.tipo = tipo;
		this.indice = indice;
	}
	
	public Nucleotide() {
	}
	
	public Nucleotide(T2 i){
		this.indice = i;
	}
	
	
	public T1 getTipo() {
		return this.tipo;
	}
	
	public T2 getIndice() {
		return this.indice;
	}
	
	@Override
	public String toString() {
		return ""+this.indice;
	}
	
}
