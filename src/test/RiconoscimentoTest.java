package test;
import riconoscimento.*;

import static org.junit.Assert.*;

import org.junit.Test;


public class RiconoscimentoTest {
	

	@Test
	public void testInserimentoCoppiaAUCorretto() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("acu");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(2));
	    assertTrue("L'inserimento è corretto", r.inserisciRelazioneRossa(coppia));
	}
	
	@Test
	public void testInserimentoCoppiaGCCorretto() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("ccg");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(2));
		assertTrue("L'inserimento è corretto", r.inserisciRelazioneRossa(coppia));
	}
	
	@Test
	public void testInserimentoCoppiaUGCorretto() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("ucg");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(2));
		assertTrue("L'inserimento è corretto", r.inserisciRelazioneRossa(coppia));
	}
	
	@Test
	public void testInserimentoErratoSenzaNuclueotidiSpaiati() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("cgg");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(1));
		assertFalse("L'inserimento è corretto", r.inserisciRelazioneRossa(coppia));
	}
	
	@Test
	public void testInserimentoErratoRelazioniCheSiAccavallano() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("ccgg");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(1), new Nucleotide(3));
		r.inserisciRelazioneRossa(coppia);
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia2;
		coppia2 = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(2));
		assertFalse("L'inserimento è corretto", r.inserisciRelazioneRossa(coppia2));
	}
	
	@Test
	public void testInserimentoHairpinCorretto() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("ccgg");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(1), new Nucleotide(3));
		r.inserisciRelazioneRossa(coppia);
		r.inizializzaMappa();
		r.riconosciLoop(0);
		assertEquals(1, r.getNumeroHairpin());
	}
	
	@Test
	public void testInserimentoHelixCorretto() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("ccugg");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(1), new Nucleotide(3));
		r.inserisciRelazioneRossa(coppia);
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia2;
		coppia2 = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(4));
		r.inserisciRelazioneRossa(coppia2);
		r.inizializzaMappa();
		r.riconosciLoop(0);
		assertEquals(1, r.getNumeroHelix());
	}
	
	@Test
	public void testInserimentoBulgeCorretto() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("ccugug");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(1), new Nucleotide(3));
		r.inserisciRelazioneRossa(coppia);
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia2;
		coppia2 = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(5));
		r.inserisciRelazioneRossa(coppia2);
		r.inizializzaMappa();
		r.riconosciLoop(0);
		assertEquals(1, r.getNumeroBulge());
	}
	
	@Test
	public void testInserimentoInternalLoopCorretto() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("cucugug");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(2), new Nucleotide(4));
		r.inserisciRelazioneRossa(coppia);
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia2;
		coppia2 = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(6));
		r.inserisciRelazioneRossa(coppia2);
		r.inizializzaMappa();
		r.riconosciLoop(0);
		assertEquals(1, r.getNumeroInnerLoop());
	}
	
	@Test
	public void testInserimentoMultipleLoopCorretto() {
		Riconoscimento r = null;
		try{
		r = new Riconoscimento("cucugucugg");
		}catch(InputSequenceException e){
		}
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia;
		coppia = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(0), new Nucleotide(9));
		r.inserisciRelazioneRossa(coppia);
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia2;
		coppia2 = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(2), new Nucleotide(4));
		r.inserisciRelazioneRossa(coppia2);
		Coppia<Nucleotide<TipoNucleotide, Integer>,Nucleotide<TipoNucleotide, Integer>> coppia3;
		coppia3 = new Coppia<Nucleotide<TipoNucleotide, Integer>, Nucleotide<TipoNucleotide, Integer>>(new Nucleotide(6), new Nucleotide(8));
		r.inserisciRelazioneRossa(coppia3);
		r.inizializzaMappa();
		r.riconosciLoop(0);
		assertEquals(1, r.getNumeroMultipleLoop());
	}

}
