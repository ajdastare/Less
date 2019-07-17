
package logika;

import java.util.Random;

import inteligenca.OcenjenaPoteza;
import inteligenca.Minimax;
import gui.IgralnoPolje;

import java.util.List;
import gui.GlavnoOkno;


/**
 * @author AS
 * Hrani trenutno stanje igre in nadzoruje potek igre.
 */

public class Vodja {
	private IgralnoPolje polje;
	private Random random;
	
	// Glavno okno
	private GlavnoOkno okno;
	
	// Igra, ki jo trenutno igramo.
	public Igra igra;
	

	public Igralec beli;
	public boolean naVrstiB;
	public boolean naVrstiC;
	//prej tu clovek na vrsti 
		
	public Vodja(GlavnoOkno okno) {
		random = new Random();
		this.okno = okno;
		naVrstiB = true;
	}
		public void novaIgra(Igralec beli) {
//		// Ustvarimo novo igro
		this.igra = new Igra();
		
		this.beli = beli;
//		igramo();
	}
//	
	public void igramo () {
		okno.osveziGUI();
		// tole zamenjaj z repaint() ? 
		switch (igra.stanje()) {
		case ZMAGA_B: 
		case ZMAGA_C: 
			case NEODLOCENO: 
				break;
		case NA_POTEZI_B:
		case NA_POTEZI_C: 
			if (igra.naPotezi == beli) {
				naVrstiB = true;
			} else {
				racunalnikovaPoteza();
			}			
		}
	}
	
	public void racunalnikovaPoteza() {
		List<OcenjenaPoteza> ocenjenePoteze = Minimax.oceniPoteze (igra, 2, clovek.nasprotnik());
		Poteza poteza = Minimax.maxPoteza(ocenjenePoteze);
		igra.odigraj(poteza);
		igramo();
	}
//	
	public void clovekovaPoteza(Poteza koncna) {
		if (igra.odigraj(koncna)) {

			System.out.println("smo v clovekova Poteza");

			int zacX= koncna.getX0();
			int zacY = koncna.getY0();
			int koncX = koncna.getX();
			int koncY = koncna.getY();
//			
			Polje[][] plosca = igra.getPlosca();
			plosca[zacX][zacY]= Polje.PRAZNO;
			plosca[koncX][koncY]= Polje.B;
			
//			naVrstiB = false;
//			igramo();
			
			
		}
//		
	}
//	
//	
//
}