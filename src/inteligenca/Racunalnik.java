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


public class Racunalnik {
private static final Random RANDOM = new Random();
	
	private static final int ZMAGA = 50; // vrednost zmage, veÄ kot vsaka druga ocena pozicije
//	private static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
//	private static final int NEODLOC = 0;  // vrednost neodloÄene igre	
	
	public static List<OcenjenaPoteza> oceniPoteze(Igra igra, Igralec jaz) {
		List<OcenjenaPoteza> ocenjenePoteze = new LinkedList<OcenjenaPoteza> ();
		List<OcenjenaPoteza> moznePoteze = igra.poteze();
	
		for (OcenjenaPoteza p: moznePoteze) {
			Igra tempIgra = new Igra(igra);
			Polje[][] tempPlosca = tempIgra.getPlosca();
			if(tempIgra.odigraj(p.poteza)) {
				int zacX0 = p.poteza.getX0();
				int zacY0 = p.poteza.getY0();
				int koncX = p.poteza.getX();
				int koncY = p.poteza.getY();
				tempPlosca[zacX0][zacY0] = Polje.PRAZNO;
				if( jaz == Igralec.C) {
					tempPlosca[koncX][koncY] = Polje.C;
				}else {
					tempPlosca[koncX][koncY] = Polje.B;
				}	
				int vrednost_poteze = oceniPozicijo(tempIgra,jaz);
				// tu bi lahko dodala oceniPozicijo + oceniPoteze(igra igra...)
				ocenjenePoteze.add(new OcenjenaPoteza(p.poteza,p.strosek, vrednost_poteze));
	
	}else {
		continue;}
	}
		return ocenjenePoteze;
	}
	// na vsakem koraku zgoraj dobimo ocenjene poteze 
	
//	public static Poteza globina(Igra igra, Poteza poteza, int globina,Igralec jaz) {
//		List<OcenjenaPoteza> ocenjenePoteze = oceniPoteze(igra, jaz);
//		// na ta nacin dobimo poteze in ocene
//		Poteza najboljsa = maxPoteza(ocenjenePoteze);
//		
//	}
	
	
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
//						if(stevec == 1) {
							int x = i;
							int y = j;
							while(x >= 1){
							// štejemo stroške po vrstici
							// gremo v levo in gledamo desne ograje 
								int strosek = matrika[x-1][j][1];
								vrstice += strosek + 1;
								x = x-1;
								}
							if(i == 0) {
								vrstice = vrstice;
							}
							if(x == 0) {
								vrstice = vrstice + matrika[x][j][1]+ 1;
//							}
							
							while(y>=1){
								// do (0,0)
								int strosek = matrika[0][y][0];
								stolpci += strosek + 1;
								y = y-1;
								}
							if(y == 0) {
								stolpci = stolpci ;
							}
							}
//						if(stevec == 2){
//							int x= i;
//							int y = j;
//							while(x >= 2){// gremo do (1,0)
//							// štejemo stroške po vrstici
//							// gremo v levo in gledamo desne ograje 
//								int strosek = matrika[x-1][j][1];
//								vrstice += strosek + 1;
//								x = x - 1;
//								}
//							if(x == 1) {
//								vrstice = vrstice + matrika[x][j][1]+1;
//							}
//							if(i == 1) {
//								vrstice = vrstice;
//							}
//							while(y>=1){
//								// do (1,0)
//								int strosek = matrika[1][y][0];
//								stolpci += strosek + 1;
//								y = y-1;
//								}
//							if(y == 0) {
//								stolpci = stolpci;
//							}
//							
//						}
//						if(stevec == 3) {
//							int x= i;
//							int y = j;
//							while(x >= 1){
//							// štejemo stroške po vrstici
//							// gremo v levo in gledamo desne ograje 
//								int strosek = matrika[x-1][j][1];
//								vrstice += strosek + 1;
//								x = x - 1;
//								}
//							if(x == 0) {
//								vrstice = vrstice+ matrika[0][j][1] +1 ;
//							}
//							if(i == 0) {
//								vrstice = vrstice;
//							}
//							while(y >=2){
//								// gremo do (0,1)
//								int strosek = matrika[0][y][0];
//								stolpci += strosek + 1;
//								y = y-1;
//								}
//							if(y == 1) {
//								stolpci = stolpci;
//							}
//							
//						}
//						if(stevec == 4) {
//							int x= i;
//							int y = j;
//							while(x >= 2){
//							// štejemo stroške po vrstici
//							// gremo v levo in gledamo desne ograje 
//								int strosek = matrika[x-1][j][1];
//								vrstice += strosek + 1;
//								x = x - 1;
//								}
//							if(x == 1) {
//								vrstice= vrstice + matrika[1][j][1]+1;
//								}
//							if(i == 1) {
//								vrstice = vrstice;
//							}
//							while(y >=2){
//								// gremo do (1,1)
//								int strosek = matrika[1][y][0];
//								stolpci += strosek + 1;
//								y = y-1;
//								}
//							if(y ==1) {
//								stolpci = stolpci;
//							}
//							
//						
//						}
						
					
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
	
	
	
//	public static OcenjenaPoteza maxPoteza(List<OcenjenaPoteza> ocenjenePoteze) {
//		int max = ZMAGA;
//		OcenjenaPoteza poteza = null;
//		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
//			if (ocenjenaPoteza.vrednost <= max) {
//				max = ocenjenaPoteza.vrednost;
//				poteza = ocenjenaPoteza;			
//			}
//		}
//		return poteza;
//	}

}
