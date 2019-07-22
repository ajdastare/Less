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
	static int kolikokrat = 3;
	
	private static final Random RANDOM = new Random();
	
	private static final int ZMAGA = 50; // vrednost zmage, veÄ kot vsaka druga ocena pozicije
	private static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	private static final int NEODLOC = 0;  // vrednost neodloÄene igre	
	
	public static List<OcenjenaPoteza> oceniPoteze(Igra igra, int globina, Igralec jaz) {
		List<OcenjenaPoteza> ocenjenePoteze = new LinkedList<OcenjenaPoteza> ();
		List<Poteza> moznePoteze = igra.poteze();
	
		for (Poteza p: moznePoteze) {
	
			
			Igra tempIgra = new Igra(igra);
			Polje[][] tempPlosca = tempIgra.getPlosca();
			
			

			if(tempIgra.odigraj (p)) {

				
				int zacX0 = p.getX0();
				int zacY0 = p.getY0();
				int koncX = p.getX();
				int koncY = p.getY();
//				kolikokrat -= (3 - tempIgra.st_potezB);

			
				tempPlosca[zacX0][zacY0] = Polje.PRAZNO;
				if( jaz == Igralec.C) {
					tempPlosca[koncX][koncY] = Polje.C;
				}else {
					tempPlosca[koncX][koncY] = Polje.B;
				}	
			// tu bi moral upoštevati ali je na vrsti jaz/ nasprotnik? 
			// z vsako potezo odigra igro 
				
//				int ocena = minimaxPozicijo (tempIgra, globina-1, tempIgra.naPotezi);
			
			// gre v globino 
//				ocenjenePoteze.add(new OcenjenaPoteza(p, ocena));
//			}
//			if(kolikokrat > 0) {
//				int ocena = oceniPozicijo(tempIgra,jaz);
				int ocena = minimaxPozicijo(tempIgra, globina-1, jaz);
				ocenjenePoteze.add(new OcenjenaPoteza(p,ocena));
			}	
//				
//			}
//			if(kolikokrat == 0) {
//				// da smo prisli do 0 
//				int ocena = minimaxPozicijo (tempIgra, globina-1, jaz.nasprotnik());
//				ocenjenePoteze.add(new OcenjenaPoteza(p, ocena));
//			}

		}
		
		return ocenjenePoteze;
	}
	
//	
	public static int minimaxPozicijo(Igra igra, int globina, Igralec jaz) {
		Stanje stanje = igra.stanje();
		switch (stanje) {
		case ZMAGA_B: return (jaz == Igralec.B ? ZMAGA : ZGUBA);
		case ZMAGA_C: return (jaz == Igralec.C ? ZMAGA : ZGUBA);
		default:
		// Nekdo je na potezi
			// tukej bi mogl seštevat stroške!
			
		if (globina == 0) {return oceniPozicijo(igra,jaz);}
		//definiraj kako bom ocenjevala trenutno pozicijo na plošči 
		// minimalno število korakov da vsi pridejo v kot
	
//		if (globina > 0) {
	    List<OcenjenaPoteza> ocenjenePoteze = oceniPoteze(igra, globina, jaz);
		if (igra.naPotezi == jaz) {return maxOcena(ocenjenePoteze);}
		
		else {return minOcena(ocenjenePoteze);
		}
		}

		
		
		

	}

	public static int maxOcena(List<OcenjenaPoteza> ocenjenePoteze) {
		int max = ZMAGA;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost < max) {max = ocenjenaPoteza.vrednost;}
			// cim manjsi je strosek boljse bo
		}
		return max;
	}
//	
	public static Poteza maxPoteza(List<OcenjenaPoteza> ocenjenePoteze) {
		int max = ZMAGA;
		Poteza poteza = null;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost <= max) {
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
		Polje[][] plosca = igra.getPlosca();
		// plosca od kopije igra
		int[][][] matrika = Igra.matrika;
		// ta matrika ni kopija
		int vrstice = 0;
		int stolpci = 0; 
		int skupaj = 0; 
		int stevec = 0;
		if (jaz == Igralec.C) {
			for (int i=0; i < 6; i++) {
				for (int j = 0; j<6; j++) {
					if (plosca[i][j] == Polje.C) {
						stevec += 1;
						if(stevec == 1) {
							int x= i-1;
							int y = j;
							while(x >= 0){
							// štejemo stroške po vrstici
							// gremo v levo in gledamo desne ograje 
								int strosek = matrika[x][j][1];
								vrstice += strosek + 1;
								x = x - 1;
								}
							while(y>=0){
								// do (0,0)
								int strosek = matrika[0][y][0];
								stolpci += strosek + 1;
								y = y-1;
								}	
							}
						if(stevec == 2){
							int x= i-1;
							int y = j;
							while(x > 0){// gremo do (1,0)
							// štejemo stroške po vrstici
							// gremo v levo in gledamo desne ograje 
								int strosek = matrika[x][j][1];
								vrstice += strosek + 1;
								x = x - 1;
								}
							while(y>=0){
								// do (1,0)
								int strosek = matrika[1][y][0];
								stolpci += strosek + 1;
								y = y-1;
								}	
							
						}
						if(stevec == 3) {
							int x= i-1;
							int y = j;
							while(x >= 0){
							// štejemo stroške po vrstici
							// gremo v levo in gledamo desne ograje 
								int strosek = matrika[x][j][1];
								vrstice += strosek + 1;
								x = x - 1;
								}
							while(y >0){
								// gremo do (0,1)
								int strosek = matrika[0][y][0];
								stolpci += strosek + 1;
								y = y-1;
								}	
							
						}
						if(stevec == 4) {
							int x= i-1;
							int y = j;
							while(x > 0){
							// štejemo stroške po vrstici
							// gremo v levo in gledamo desne ograje 
								int strosek = matrika[x][j][1];
								vrstice += strosek + 1;
								x = x - 1;
								}
							while(y > 0){
								// gremo do (1,1)
								int strosek = matrika[1][y][0];
								stolpci += strosek + 1;
								y = y-1;
								}	
							
						}
						
					
					}
					
				
			}
			
		}
		}
		if (jaz == Igralec.B ) {
			for (int i=0; i < 6; i++) {
				for (int j = 0; j<6; j++) {
					if (plosca[i][j] == Polje.B) {
						// štejemo stroške po vrstici
						// gremo v desno zato nas zanimajo desne ograje
						for(int x = i; x <= 4 ; x++) {
								vrstice = vrstice + matrika[x][j][1];	
						}
						for(int y =j+1; j<6; j++) {
								stolpci= stolpci + matrika[5][y][0];						
								}
						}
					}
					}
					}
		skupaj = vrstice + stolpci;
		vrstice = 0;
		stolpci = 0;
		return skupaj;
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