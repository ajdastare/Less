package inteligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import logika.Poteza;
import logika.Stanje;
import logika.Kot;
import logika.Polje;

/**
 * @author AS
 *
 */
public class Minimax {
	
	private static final Random RANDOM = new Random();
	
	private static final int ZMAGA = (1 << 20); // vrednost zmage, veÄ kot vsaka druga ocena pozicije
	private static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	private static final int NEODLOC = 0;  // vrednost neodloÄene igre	
	
//	public static List<OcenjenaPoteza> oceniPoteze(Igra igra, int globina, Igralec jaz) {
//		List<OcenjenaPoteza> ocenjenePoteze = new LinkedList<OcenjenaPoteza> ();
//		List<Poteza> moznePoteze = igra.poteze();
//		for (Poteza p: moznePoteze) {
//			Igra tempIgra = new Igra(igra);
//			tempIgra.odigraj (p);
//			int ocena = minimaxPozicijo (tempIgra, globina-1, jaz);
//			ocenjenePoteze.add(new OcenjenaPoteza(p, ocena));			
//		}
//		return ocenjenePoteze;
//	}
//	
//	public static int minimaxPozicijo(Igra igra, int globina, Igralec jaz) {
//		Stanje stanje = igra.stanje();
//		switch (stanje) {
//		case ZMAGA_O: return (jaz == Igralec.O ? ZMAGA : ZGUBA);
//		case ZMAGA_X: return (jaz == Igralec.X ? ZMAGA : ZGUBA);
//		case NEODLOCENO: return (NEODLOC);
//		default:
//		// Nekdo je na potezi
//		if (globina == 0) {return oceniPozicijo(igra, jaz);}
//		// globina > 0
//	    List<OcenjenaPoteza> ocenjenePoteze = oceniPoteze(igra, globina, jaz);
//		if (igra.naPotezi == jaz) {return maxOcena(ocenjenePoteze);}
//		else {return minOcena(ocenjenePoteze);}		
//		}
//	}
//	
//	public static int maxOcena(List<OcenjenaPoteza> ocenjenePoteze) {
//		int max = ZGUBA;
//		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
//			if (ocenjenaPoteza.vrednost > max) {max = ocenjenaPoteza.vrednost;}
//		}
//		return max;
//	}
//	
//	public static Poteza maxPoteza(List<OcenjenaPoteza> ocenjenePoteze) {
//		int max = ZGUBA;
//		Poteza poteza = null;
//		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
//			if (ocenjenaPoteza.vrednost >= max) {
//				max = ocenjenaPoteza.vrednost;
//				poteza = ocenjenaPoteza.poteza;			
//			}
//		}
//		return poteza;
//	}
//	
//	public static int minOcena(List<OcenjenaPoteza> ocenjenePoteze) {
//		int min = ZMAGA;
//		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
//			if (ocenjenaPoteza.vrednost < min) {min = ocenjenaPoteza.vrednost;}
//		}
//		return min;
//	}
//	
//	// Metoda oceniPozicijo je odvisna od igre
//	
//	public static int oceniPozicijo(Igra igra, Igralec jaz) {
//		int ocena = 0;
//		for (Vrsta v : Igra.VRSTE) {
//			ocena = ocena + oceniVrsto(v, igra, jaz);
//		}
//		return ocena;	
//	}
//	
//	public static int oceniVrsto (Vrsta v, Igra igra, Igralec jaz) {
//		Polje[][] plosca = igra.getPlosca();
//		int count_X = 0;
//		int count_O = 0;
//		for (int k = 0; k < Igra.N && (count_X == 0 || count_O == 0); k++) {
//			switch (plosca[v.x[k]][v.y[k]]) {
//			case O: count_O += 1; break;
//			case X: count_X += 1; break;
//			case PRAZNO: break;
//			}
//		}
//		if (count_O > 0 && count_X > 0) { return 0; }
//		else if (jaz == Igralec.O) { return count_O - count_X; }
//		else { return count_X - count_O; }
//	}
//	
//	// Nakljucna ocena pozicije. Metoda ni uporabljena.
//	public static int oceniPozicijoA(Igra igra, Igralec jaz) {
//		return RANDOM.nextInt(201) - 100;	
//	}
//
}