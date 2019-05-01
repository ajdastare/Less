
package logika;

import java.util.Random;

import inteligenca.OcenjenaPoteza;
import inteligenca.Minimax;

import java.util.List;
import gui.GlavnoOkno;


/**
 * @author AS
 * Hrani trenutno stanje igre in nadzoruje potek igre.
 */

public class Vodja {
	
	private Random random;
	
	// Glavno okno
	private GlavnoOkno okno;
	
	// Igra, ki jo trenutno igramo.
	public Igra igra;
	
	// Ali je Älovek O ali X?
	private Igralec clovek;	
	
	public boolean clovekNaVrsti;
		
	public Vodja(GlavnoOkno okno) {
		random = new Random();
		this.okno = okno;
		clovekNaVrsti = false;
	}
	
//	public void novaIgra(Igralec clovek) {
//		// Ustvarimo novo igro
//		this.igra = new Igra();
//		this.clovek = clovek;
//		igramo();
//	}
//	
//	public void igramo () {
//		okno.osveziGUI();
//		switch (igra.stanje()) {
//		case ZMAGA_B: 
//		case ZMAGA_C: 
//		case NEODLOCENO: 
//			break;
//		case NA_POTEZI_B:
//		case NA_POTEZI_C: 
//			if (igra.naPotezi == clovek) {
//				clovekNaVrsti = true;
//			} else {
//				racunalnikovaPoteza();
//			}			
//		}
//	}
//	
//	public void racunalnikovaPoteza() {
//		List<OcenjenaPoteza> ocenjenePoteze = Minimax.oceniPoteze (igra, 2, clovek.nasprotnik());
//		Poteza poteza = Minimax.maxPoteza(ocenjenePoteze);
//		igra.odigraj(poteza);
//		igramo();
//	}
//	
//	public void clovekovaPoteza(Poteza poteza) {
//		if (igra.odigraj(poteza)) {
//			clovekNaVrsti = false;	
//			igramo();
//		}
//		
//	}
//	
//	
//
}