package riconoscimento;
import java.util.Scanner;
public class Main {
	public static void main (String[] args){
	Scanner input = new Scanner(System.in);
	Riconoscimento riconosci = null;
	while(riconosci == null)
		try {
			System.out.println("Scrivi una sequenza secondary structure");
			String str = input.nextLine();
			riconosci = new Riconoscimento(str);
		} catch (InputSequenceException e) {
			System.out.println("Non hai inserito una sequenza valida");
			continue;
		}
	String inputRosso = "", out = null;
	while(inputRosso != "stop"){
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		System.out.println("\nInserisci la relazione rossa o inserisci stop per terminare" + "\n" + "Primo valore");
		inputRosso = input.nextLine();
		int inf = Integer.MIN_VALUE;
		try {
			inf = Integer.parseInt(inputRosso);
		} catch (NumberFormatException exc) {
			if (inputRosso.equals("stop")) {
				break;
			} else {
				System.out.println("Non hai inserito un numero");
				continue;
			}
		}
		if (inf != Integer.MIN_VALUE) {
			System.out.println("Inserisci il secondo valore");
			inputRosso = input.nextLine();
			int sup = Integer.MAX_VALUE;
			try {
				sup = Integer.parseInt(inputRosso);
			} catch (NumberFormatException exc) {
				if (inputRosso.equals("stop")) {
					break;
				} else {
					System.out.println("Non hai inserito un numero");
					continue;
				}
			}
			if (sup != Integer.MAX_VALUE) {
				try{
					coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(Math.min(inf, sup)), new Nucleotide(Math.max(inf, sup)));
					boolean esito = riconosci.inserisciRelazioneRossa(coppia);
					System.out.println(esito);
				}catch(IndexOutOfBoundsException ex){
					System.out.println("Non puoi inserire un valore fuori range!");
					continue;
				}
				riconosci.stampaSequenza();
				System.out.println("\n");
				if ((out = riconosci.getRelazioniRosse()) != null)
					System.out.println(out);
			} else continue;
		}
	}
	input.close();
	if(riconosci.getRelazioniRosse() == null){
		System.out.println("La sequenza inserita è vuota");
		riconosci.inizializzaMappa();
		riconosci.getLoopTotali();
		return;
	}
	//System.out.println(riconosci.getRelazioniRosse());
	riconosci.inizializzaMappa();
	//System.out.println(riconosci.getRelazioniRosse());
	riconosci.riconosciLoop(0);
	}
	
}