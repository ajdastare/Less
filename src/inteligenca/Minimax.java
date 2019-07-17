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
	
	private static final int ZMAGA = (1 << 36); // vrednost zmage, veÄ kot vsaka druga ocena pozicije
	private static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	private static final int NEODLOC = 0;  // vrednost neodloÄene igre	
	
	public static List<OcenjenaPoteza> oceniPoteze(Igra igra, int globina, Igralec jaz) {
		List<OcenjenaPoteza> ocenjenePoteze = new LinkedList<OcenjenaPoteza> ();
		List<Poteza> moznePoteze = igra.poteze();
		for (Poteza p: moznePoteze) {
			Igra tempIgra = new Igra(igra);
			// kopiramo igro
			tempIgra.odigraj (p);
			// z vsako potezo odigra igro 
			int ocena = minimaxPozicijo (tempIgra, globina-1, jaz);
			// tu da oceno 
			ocenjenePoteze.add(new OcenjenaPoteza(p, ocena));			
		}
		return ocenjenePoteze;
	}
//	
	public static int minimaxPozicijo(Igra igra, int globina, Igralec jaz) {
		Stanje stanje = igra.stanje();
		switch (stanje) {
		case ZMAGA_B: return (jaz == Igralec.B ? ZMAGA : ZGUBA);
		case ZMAGA_C: return (jaz == Igralec.C ? ZMAGA : ZGUBA);
		case NEODLOCENO: return (NEODLOC);
		default:
		// Nekdo je na potezi
		if (globina == 0) {return oceniPozicijo(igra, jaz);}
		//definiraj kako bom ocenjevala trenutno pozicijo na plošči 
		// minimalno število korakov da vsi pridejo v kot
		
		if (globina > 0) {
	    List<OcenjenaPoteza> ocenjenePoteze = oceniPoteze(igra, globina, jaz);
		if (igra.naPotezi == jaz) {return maxOcena(ocenjenePoteze);}
		
		else {return minOcena(ocenjenePoteze);
		}		
		}
		}
	}
//	
	public static int maxOcena(List<OcenjenaPoteza> ocenjenePoteze) {
		int max = ZGUBA;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost > max) {max = ocenjenaPoteza.vrednost;}
		}
		return max;
	}
//	
	public static Poteza maxPoteza(List<OcenjenaPoteza> ocenjenePoteze) {
		int max = ZGUBA;
		Poteza poteza = null;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost >= max) {
				max = ocenjenaPoteza.vrednost;
				poteza = ocenjenaPoteza.poteza;			
			}
		}
		return poteza;
	}
//	
	public static int minOcena(List<OcenjenaPoteza> ocenjenePoteze) {
		int min = ZMAGA;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost < min) {min = ocenjenaPoteza.vrednost;}
		}
		return min;
	}
//	
	// Metoda oceniPozicijo je odvisna od igre
	
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		
		int ocena = 0;
		// igralec je jaz torej z tistimi ki so tako oznaceni zacnemo
		for(int i = 0; i <6 ; i ++) {
			for(int j = 0; j < 6; j++) {
				if(polje[i][j]== Polje.jaz) {
					int zac_x = i;
					int zac_y = j;
					
					int razlika1_x = zac_x - 0;
					int razlika1_y = zac_y - 0; 
					// najvecji razliki 
					
					for (int x = 0; x <= razlika1_x; x++){
						for (int y = 0; y < razlika1_y; y++) {
							if(polje[i-x-1][j] == Polje.PRAZNO) {
								
							}
							
						}
					}							
					
					
					
					// poglejmo kam sploh more pridt
					if (jaz ==  Igralec.C) {
						LinkedList<Polje> konecC = new LinkedList<Polje>();
						Polje[][] plosca;
						konecC.add(plosca[1][1]);
						konecC.add(plosca[0][1]);
						konecC.add(plosca[0][0]);
						konecC.add(plosca[1][0]);
						// nekak bi rabla od tu naprej neko rekurzivno funkcijo ki bi nas prpelala v ta kot
						// in stela stroske
						// koncamo ko pride C najhitreje v :
						//plosca[1][1], plosca[1][0], plosca[0][1], plosca[0][0]
						// ali pa da bi samo preračunale za kok se more premaknit po x osi in za kok po y osi pa potem pogledaš stroške? 
						
					}
					if(jaz == Igralec.B) {
						// plosca[4][4], plosca[4][5], plosca[5][5], plosca[5][4]
						
					}
					
					
				}
				
			}
		}
		return ocena;
		}
		
		
		
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
	
//	// Nakljucna ocena pozicije. Metoda ni uporabljena.
//	public static int oceniPozicijoA(Igra igra, Igralec jaz) {
//		return RANDOM.nextInt(201) - 100;	
//	}
//
}