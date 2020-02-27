package riconoscimento;

public enum TipoNucleotide {
	
	A('a'),
	U('u'),
	G('g'),
	C('c');
	private char t;
    TipoNucleotide(char t) { this.t = t; }
    public char getValue() { return t; }
	
	
	

}
