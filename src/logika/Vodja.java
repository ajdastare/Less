
package logika;

import java.util.Random;

import inteligenca.OcenjenaPoteza;
import inteligenca.Racunalnik;
import gui.IgralnoPolje;
import logika.Igra;

import java.util.List;
import gui.GlavnoOkno;

/**
 * @author AS
 * Hrani trenutno stanje igre in nadzoruje potek igre.
 */

public class Vodja {
	private static final int ZMAGA = 50;
	private IgralnoPolje polje;
	private Random random;
	
	// Glavno okno
	private GlavnoOkno okno;
	
	// Igra, ki jo trenutno igramo.
	public Igra igra;
	int st_potezR = 3;
// Ali je clovek igralecB ali C? 
	public static Vrsta_igralca igralecB;
	public static Vrsta_igralca igralecC;
	public boolean clovekNaVrsti; 
	
	
	public Igralec beli;
	public Igralec crni;
	public static boolean naVrstiB;
	public boolean naVrstiC;
	public boolean naVrstiR;
	//prej tu clovek na vrsti 
		
	public Vodja(GlavnoOkno okno) {
		random = new Random();
		this.okno = okno;
		clovekNaVrsti = false;
//		naVrstiB = false;
	}
		public void novaIgra(Vrsta_igralca igralecB, Vrsta_igralca igralecC) {
//		// Ustvarimo novo igro
		this.igra = new Igra();
		Vodja.igralecB = igralecB;
		Vodja.igralecC = igralecC;
				
//		this.beli = beli;
//		this.crni = beli.nasprotnik();
		igramo();
	}
//	
	public void igramo () {
//		okno.osveziGUI();
		okno.repaint();
		// tole zamenjaj z repaint() ? 
		switch (igra.stanje()) {
		case ZMAGA_B: 
		case ZMAGA_C: 
				break;
		case NA_POTEZI_B:
			if (igralecB ==Vrsta_igralca.CLOVEK) clovekNaVrsti = true;
			else {
				racunalnikovaPoteza(Igralec.B);
			}
			break;
		case NA_POTEZI_C: 
			if(igralecC == Vrsta_igralca.CLOVEK) clovekNaVrsti = true;
			else{
				clovekNaVrsti = false;
				racunalnikovaPoteza(Igralec.C);
			}
			
		}
		okno.osveziGUI();
	}
	public static OcenjenaPoteza maxPoteza(List<OcenjenaPoteza> ocenjenePoteze, int st_potez) {
		
		int max = ZMAGA ;
		OcenjenaPoteza poteza = null;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost <= max) {
				if(st_potez - ocenjenaPoteza.strosek >= 0) {
					if(ocenjenaPoteza.poteza.x == 0 && ocenjenaPoteza.poteza.x0==0) {
						if(ocenjenaPoteza.poteza.y< ocenjenaPoteza.poteza.y0) {
							return ocenjenaPoteza;
						
					}
					}
					if(ocenjenaPoteza.poteza.y0 == 0 && ocenjenaPoteza.poteza.y == 0) {
						if(ocenjenaPoteza.poteza.x < ocenjenaPoteza.poteza.x0) {
						return ocenjenaPoteza;
						}
						}else{
					max = ocenjenaPoteza.vrednost;
					poteza = ocenjenaPoteza;	
					}
				}else {continue;}
			}
			else continue;
				
			}
		
		return poteza;
	}
	
	public void racunalnikovaPoteza(Igralec igralec) {
//		List<OcenjenaPoteza> ocenjenePoteze = Minimax.oceniPoteze (igra, 3, beli.nasprotnik());
		List<OcenjenaPoteza> ocenjenePoteze = Racunalnik.oceniPoteze(igra,igralec);

		OcenjenaPoteza  poteza1 = maxPoteza(ocenjenePoteze, st_potezR);
//		Poteza poteza = Minimax.maxPoteza(ocenjenePoteze);
		
	
		
		int zac_X= poteza1.poteza.getX0();
		int zac_Y = poteza1.poteza.getY0();
		int konc_X = poteza1.poteza.getX();
		int konc_Y = poteza1.poteza.getY();

		int strosek = poteza1.strosek;
		
		boolean horizontalno = false;
		boolean vertikalno = false;
		boolean na_desno = false;
		boolean na_levo = false;
		boolean dol = false;
		boolean gor = false;
//
//	
		st_potezR = st_potezR - strosek;
		
		if(st_potezR >0) {
			Polje[][] plosca = igra.getPlosca();
			plosca[zac_X][zac_Y]= Polje.PRAZNO;
			if(igralec == Igralec.C) {
				plosca[konc_X][konc_Y]=Polje.C;
				
			}
			if(igralec == Igralec.B) {
				plosca[konc_X][konc_Y]=Polje.B;
				
				
			}	
		}
		if(st_potezR == 0) {
			Polje[][] plosca = igra.getPlosca();
			plosca[zac_X][zac_Y]= Polje.PRAZNO;
			if(igralec == Igralec.C) {
				plosca[konc_X][konc_Y]=Polje.C;
				
			}
			if(igralec == Igralec.B) {
				plosca[konc_X][konc_Y]=Polje.B;
				
				
			}	
			igra.naPotezi = igralec.nasprotnik();
			st_potezR =3;
		}
		if(st_potezR < 0 ) {
			System.out.println("Nekej je narobe");
			
			// na vrsti beli bi lahko tu sli v globino
			
			
		}
	igramo();
	}
//	
	public void clovekovaPoteza(Poteza koncna) {
		Igralec naPotezi = igra.naPotezi;
		if (igra.odigraj(koncna)) {
			

			

			int zacX= koncna.getX0();
			int zacY = koncna.getY0();
			int koncX = koncna.getX();
			int koncY = koncna.getY();
//			
			Polje[][] plosca = igra.getPlosca();
			plosca[zacX][zacY]= Polje.PRAZNO;

			if(naPotezi == Igralec.C) {
				plosca[koncX][koncY] = Polje.C;
				
			}
			if(naPotezi == Igralec.B) {
			plosca[koncX][koncY]= Polje.B;
			}
			
//			naVrstiB = false;
			okno.repaint();
			igramo();
			
			
		}
//		
	}
//	
//	
//
}