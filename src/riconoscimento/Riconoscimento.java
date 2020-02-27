package riconoscimento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Riconoscimento {
	private List <Nucleotide<TipoNucleotide, Integer>> listaValori = new ArrayList<Nucleotide<TipoNucleotide, Integer>>();
	private Map <TipoLoop, Integer> loopTotali;
	private List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> relazioniPresenti = new ArrayList<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>>();
	
	public Riconoscimento(String in) throws InputSequenceException {
		if(this.testInput(in)){
			this.creaMappa(in);
			this.stampaSequenza();
		} else{
			throw new InputSequenceException("Non hai inserito un input valido!");
		}
	}
	
	public Riconoscimento() {
		
	}

	public boolean testInput(String s){
		if(s.matches("[aucg]*") && s.length() > 0){
			return true;
		}else{
			return false;
		}
	}

	
	public void creaMappa(String s){
		int i;
		s = s.toUpperCase();
		for(i=0; i<s.length(); i++){
		listaValori.add(new Nucleotide<TipoNucleotide,Integer>(TipoNucleotide.valueOf(String.valueOf(s.charAt(i))),i));
		}
	}

	public void stampaSequenza(){
		int i;
		for(i=0;i<listaValori.size();i++){
			System.out.print(listaValori.get(i).getTipo().getValue()+ ": " + listaValori.get(i).getIndice()  + " ");
		}
	}
	

	public boolean inserisciRelazioneRossa(Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppiaDaInserire) {
		boolean typeCheck = false;
		boolean esito = false;
		relazioniPresenti = this.ordinaLista(relazioniPresenti);
		if (relazioniPresenti.contains(coppiaDaInserire)) {
			System.out.println("la relazione Ë stata gi‡ inserita");
			return false;
		}
		
		//ci deve essere almeno un nucleotide spaiato all'interno di una relazione rossa
		//|i-j| > 1
		if (Math.abs(coppiaDaInserire.getInf().getIndice() - coppiaDaInserire.getSup().getIndice()) <= 1) {
			System.out.println("relazioni di questo tipo non sono ammesse");
			return false;
		}
		
		if(((listaValori.get(coppiaDaInserire.getInf().getIndice()).getTipo().getValue() == 'a') && (listaValori.get(coppiaDaInserire.getSup().getIndice()).getTipo().getValue() == 'u')) || ((listaValori.get(coppiaDaInserire.getInf().getIndice()).getTipo().getValue() == 'u') && (listaValori.get(coppiaDaInserire.getSup().getIndice()).getTipo().getValue() == 'a'))){
			typeCheck = true;
		}else if(((listaValori.get(coppiaDaInserire.getInf().getIndice()).getTipo().getValue() == 'u') && (listaValori.get(coppiaDaInserire.getSup().getIndice()).getTipo().getValue() == 'g')) || ((listaValori.get(coppiaDaInserire.getInf().getIndice()).getTipo().getValue() == 'g') && (listaValori.get(coppiaDaInserire.getSup().getIndice()).getTipo().getValue() == 'u'))){
			typeCheck = true;
	} else if(((listaValori.get(coppiaDaInserire.getInf().getIndice()).getTipo().getValue() == 'g') && (listaValori.get(coppiaDaInserire.getSup().getIndice()).getTipo().getValue() == 'c')) || ((listaValori.get(coppiaDaInserire.getInf().getIndice()).getTipo().getValue() == 'c') && (listaValori.get(coppiaDaInserire.getSup().getIndice()).getTipo().getValue() == 'g'))){
			typeCheck = true;
		} else{
			if (!typeCheck) {
				System.out.println("La relazione che hai scelto non Ë valida: Errore di typeCheck");
				return false;
			}
		}
		if (relazioniPresenti.isEmpty()) {
			relazioniPresenti.add(coppiaDaInserire);
			return true;
		}
		if (coppiaDaInserire.getSup().getIndice() < this.getMinInf(relazioniPresenti)) {
			relazioniPresenti.add(coppiaDaInserire);
			return true;
		}
		if (coppiaDaInserire.getInf().getIndice() > this.getMaxSup(relazioniPresenti)) {
			relazioniPresenti.add(coppiaDaInserire);
			return true;
		}
		
				
		for(int i=0;i<this.relazioniPresenti.size(); i++){
			if((relazioniPresenti.get(i).getInf().getIndice()<=coppiaDaInserire.getInf().getIndice()) && (relazioniPresenti.get(i).getSup().getIndice()>=coppiaDaInserire.getInf().getIndice()) && (relazioniPresenti.get(i).getSup().getIndice()<=coppiaDaInserire.getSup().getIndice())){
				return false;
			}
			else if((relazioniPresenti.get(i).getSup().getIndice()>=coppiaDaInserire.getSup().getIndice()) && (relazioniPresenti.get(i).getInf().getIndice()>=coppiaDaInserire.getInf().getIndice()) && (relazioniPresenti.get(i).getInf().getIndice()<=coppiaDaInserire.getSup().getIndice())){
				return false;
			}
			else{
				esito = true;
			}
		}
		if(esito){
		 relazioniPresenti.add(coppiaDaInserire);
		}
		return esito;
	}
	
	public void inizializzaMappa() {
		loopTotali = new HashMap<TipoLoop, Integer>();
		loopTotali.put(TipoLoop.Hairpin,0);
		loopTotali.put(TipoLoop.Helix,0);
		loopTotali.put(TipoLoop.Bulge,0);
		loopTotali.put(TipoLoop.InnerLoop,0);
		loopTotali.put(TipoLoop.MultipleLoop,0);
		relazioniPresenti = this.ordinaLista(this.relazioniPresenti);
		this.assegnaLivello(relazioniPresenti,0);
	}
	
	
	public void getLoopTotali() {
			System.out.println("Hairpin : "+this.loopTotali.get(TipoLoop.Hairpin));
			System.out.println("Helix : "+this.loopTotali.get(TipoLoop.Helix));
			System.out.println("Bulge : "+this.loopTotali.get(TipoLoop.Bulge));
			System.out.println("Inner Loop : "+this.loopTotali.get(TipoLoop.InnerLoop));
			System.out.println("Multiple Loop : "+this.loopTotali.get(TipoLoop.MultipleLoop));
	}
	
	private int getMinInf(List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> lista) {
		int minValue = Integer.MAX_VALUE;
		for (int i = 0; i < lista.size(); i++) {
			if (minValue > lista.get(i).getInf().getIndice()) {
				minValue = lista.get(i).getInf().getIndice();
			}
		}
		return minValue;
	}
	
	private int getMaxSup(List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> lista) {
		int maxValue = Integer.MIN_VALUE;
		for (int i = 0; i < lista.size(); i++) {
			if (maxValue < lista.get(i).getSup().getIndice()) {
				maxValue = lista.get(i).getSup().getIndice();
			}
		}
		return maxValue;
	}
	
	public List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> ordinaLista(List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> lista){
		 List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> listaOrdinata = new ArrayList<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>>();
		 int minimoInferiore;
		 while(lista.size()>0){
			 minimoInferiore = this.getMinInf(lista);
			 for (Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> couple : lista) {
				 if(couple.getInf().getIndice() == minimoInferiore){
					 listaOrdinata.add(couple);
					 lista.remove(couple);
					 break;
				 }
			 }
		 }
		 return listaOrdinata;
	}
	
	public String getRelazioniRosse() {
		String out = null;
		for (int i= 0; i < relazioniPresenti.size(); i++) {
			if (out == null) {
				out = relazioniPresenti.get(i).toString();
			}
			else {out = out.concat(relazioniPresenti.get(i).toString());}
		}
		return out;
	}
	

	public void assegnaLivello(List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> lista, int liv){
		if(liv!=0){
			int c0 = liv - 1;
			while(lista.get(liv).getSup().getIndice() > lista.get(c0).getSup().getIndice()){
				c0--;
				if(c0<0 ){
					lista.get(liv).setLivello(0);
					return;
				}
			}
			lista.get(liv).setLivello(this.relazioniPresenti.get(c0).getLivello()+1);
			return;
		}
		
		int i = 1;
		int contatore = 0;
		for(Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppiaGi‡Presente : lista){
			if(i<lista.size()){
				if(contatore !=0 && i!=0 && lista.get(i-1).getLivello()==0){
					contatore = 0;
				}
				if(coppiaGi‡Presente.getInf().getIndice() < lista.get(i).getInf().getIndice() && coppiaGi‡Presente.getSup().getIndice() > lista.get(i).getSup().getIndice()){
					contatore = coppiaGi‡Presente.getLivello()+1;
					lista.get(i).setLivello(contatore);
					i++;
				}
				else if(lista.get(i-2).getSup().getIndice() < lista.get(i).getInf().getIndice()){
					this.assegnaLivello(lista, i);
					i++;
				}
				else{
					if(lista.get(i-2).getLivello()==0)  {
						contatore = 1;
					}
					else if(coppiaGi‡Presente.getSup().getIndice() < lista.get(i).getInf().getIndice()){
						contatore = coppiaGi‡Presente.getLivello();
					}else{
						contatore--;
					}
					lista.get(i).setLivello(contatore);
					i++;
					}
				}
			} 
		}


		public List<Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>>> getRelazioniInLivello(int livello, Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>> coppia) {
			//1. le coppie dentro coppia
			//2. prendi tutte quelle all'interno che hanno il livello superiore a livello
			int indice = this.relazioniPresenti.indexOf(coppia);
			List<Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>>> lista = new ArrayList<Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>>>();
			for(int i = indice; i<this.relazioniPresenti.size();i++){
				if(this.relazioniPresenti.get(i).getLivello() >= livello){
					lista.add(this.relazioniPresenti.get(i));
				}
				if(i+1<this.relazioniPresenti.size()){
					if(this.relazioniPresenti.get(i+1).getLivello() == 0){
						return lista;
					}
				}
			}
			return lista;
		}


		public int getNumeroRelazioniInLivello(int livello, Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>> coppia) {
			int indice = this.relazioniPresenti.indexOf(coppia);
			int contatore = 0;
			for(int i = indice; i<this.relazioniPresenti.size();i++){
				if(this.relazioniPresenti.get(i).getLivello() == livello){
					contatore++;
				}
				if(i+1<this.relazioniPresenti.size()){
					if(this.relazioniPresenti.get(i+1).getLivello() < livello){
						return contatore;
					}
				}
			}
			return contatore;
		}


		public List<Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>>> getRelazionAnnidate(int livello, Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>> coppia) {
			//1. le coppie dentro coppia
			//2. prendi tutte quelle all'interno che hanno il livello superiore a livello
			int indice = this.relazioniPresenti.indexOf(coppia);
			List<Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>>> lista = new ArrayList<Coppia<Nucleotide<TipoNucleotide,Integer>,Nucleotide<TipoNucleotide, Integer>>>();
			for(int i = indice; i<this.relazioniPresenti.size();i++){
				if(this.relazioniPresenti.get(i).getLivello() > livello){
					lista.add(this.relazioniPresenti.get(i));
				}
				if(i+1<this.relazioniPresenti.size()){
					if(this.relazioniPresenti.get(i+1).getLivello() <= livello){
						return lista;
					}
				}
			}
			return lista;
		}


public void riconosciLoop(int cont) {
	Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>();
	if(cont<this.relazioniPresenti.size()){
	coppia = relazioniPresenti.get(cont);
	}else{
		this.getLoopTotali();
		return;
	}
	int differenzaTraInf, differenzaTraSup;
	List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> coppieDiLivelloSuperiore = this.getRelazioniInLivello(coppia.getLivello()+1, coppia);
	if (coppieDiLivelloSuperiore.size() == 0) {
		loopTotali.put(TipoLoop.Hairpin, loopTotali.get(TipoLoop.Hairpin)+1);
		cont++;
		this.riconosciLoop(cont);
		return;
	}
	if (coppieDiLivelloSuperiore.size() >= 2 && (this.getNumeroRelazioniInLivello(coppia.getLivello()+1, coppia) >=2)) {
		loopTotali.put(TipoLoop.MultipleLoop, loopTotali.get(TipoLoop.MultipleLoop)+1);
		cont++;
	}else{
		differenzaTraInf = coppieDiLivelloSuperiore.get(0).getInf().getIndice() - coppia.getInf().getIndice();
		differenzaTraSup = coppia.getSup().getIndice() - coppieDiLivelloSuperiore.get(0).getSup().getIndice();
		if (differenzaTraInf >= 2 && differenzaTraSup >= 2) {
			loopTotali.put(TipoLoop.InnerLoop, loopTotali.get(TipoLoop.InnerLoop)+1);
			}
		else if (differenzaTraInf >= 2 || differenzaTraSup >= 2) {
			loopTotali.put(TipoLoop.Bulge, loopTotali.get(TipoLoop.Bulge)+1);
		}
		else if (differenzaTraInf == 1 && differenzaTraSup == 1) {
			loopTotali.put(TipoLoop.Helix, loopTotali.get(TipoLoop.Helix)+1);
		}
		if(coppieDiLivelloSuperiore.size() == 1){
		loopTotali.put(TipoLoop.Hairpin, loopTotali.get(TipoLoop.Hairpin)+1);
		cont+=2;
		this.riconosciLoop(cont);
		return;
		}
		cont++;
	}
	
	for (int j = 0; j < coppieDiLivelloSuperiore.size(); j++) {
		List<Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>>> coppieAnnidate = this.getRelazionAnnidate(coppieDiLivelloSuperiore.get(j).getLivello(), coppieDiLivelloSuperiore.get(j));
		if(coppieAnnidate.isEmpty()){
			loopTotali.put(TipoLoop.Hairpin, loopTotali.get(TipoLoop.Hairpin)+1);
			cont++;
		}else if(coppieAnnidate.size() >= 2  && ((this.getNumeroRelazioniInLivello(coppieDiLivelloSuperiore.get(j).getLivello()+1, coppieDiLivelloSuperiore.get(j)) >=2))){
			loopTotali.put(TipoLoop.MultipleLoop, loopTotali.get(TipoLoop.MultipleLoop)+1);
			cont++;
		}else{
			differenzaTraInf = coppieAnnidate.get(0).getInf().getIndice() - coppieDiLivelloSuperiore.get(j).getInf().getIndice();
			differenzaTraSup =  coppieDiLivelloSuperiore.get(j).getSup().getIndice() - coppieAnnidate.get(0).getSup().getIndice();
			if (differenzaTraInf >= 2 && differenzaTraSup >= 2) {
				loopTotali.put(TipoLoop.InnerLoop, loopTotali.get(TipoLoop.InnerLoop)+1);
			}
			else if (differenzaTraInf >= 2 || differenzaTraSup >= 2) {
				loopTotali.put(TipoLoop.Bulge, loopTotali.get(TipoLoop.Bulge)+1);
			}
			else if (differenzaTraInf == 1 && differenzaTraSup == 1) {
				loopTotali.put(TipoLoop.Helix, loopTotali.get(TipoLoop.Helix)+1);
			}
			cont++;
		}
		
		}
	    this.riconosciLoop(cont);
		}


	public int getNumeroHairpin(){
		return this.loopTotali.get(TipoLoop.Hairpin);
	}
	
	public int getNumeroHelix(){
		return this.loopTotali.get(TipoLoop.Helix);
	}
	
	public int getNumeroBulge(){
		return this.loopTotali.get(TipoLoop.Bulge);
	}
	
	public int getNumeroInnerLoop(){
		return this.loopTotali.get(TipoLoop.InnerLoop);
	}
	
	public int getNumeroMultipleLoop(){
		return this.loopTotali.get(TipoLoop.MultipleLoop);
	}
}


