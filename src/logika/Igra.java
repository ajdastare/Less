package logika;

import java.util.*;
import com.sun.javafx.collections.MappingChange.Map;

public class Igra {

	
	// Velikost igralne pološče je N*2 x N*2.
	public static final int N = 9;
	public static final int M = 4;

	// Igralno polje
	private Polje[][] plosca;
		
	// Igralec, ki je trenutno na potezi.
	// Vrednost je poljubna, ce je igre konec (se pravi, lahko je napacna).
	public Igralec naPotezi;
	
	public static final Podstavek p1 = new Podstavek("1");
	public static final Podstavek p2 = new Podstavek("2");
	public static final Podstavek p3 = new Podstavek("3");
	public static final Podstavek p4 = new Podstavek("4");
	public static final Podstavek p5 = new Podstavek("5");
	public static final Podstavek p6 = new Podstavek("6");
	public static final Podstavek p7 = new Podstavek("7");
	public static final Podstavek p8 = new Podstavek("8");
	public static final Podstavek p9 = new Podstavek("9");
	public static final Podstavek p10 = new Podstavek("10");
	public static final Podstavek p11 = new Podstavek("11");
	public static final Podstavek p12 = new Podstavek("12");
	
	public static final int[][][] podatki = new int[N][M][4];
	
	public static final int[][][] matrika = new int[6][6][2];
	
	//koti so mesta (0,0)...(2,0)...(6,0)...(8,0)
	//ce ima kaksen element vrednost true, pomeni, da so vsa mesta v kotu zapolnjena; [false, true, false, false] pomeni, da
	//ima en izmed igralcev vse svoje figure na (2,0), (2,1), (2,3), (2,4).
	public static final boolean[] koti = new boolean[4];
	

	static {
		//konstrukcija vseh 12 možnih podstavkov 
		Podstavek[] ab = {p1, p2, p3, p4, p7, p8};
		for (Podstavek i : ab) i.ograje[3][3] = 1;
		Podstavek[] ba = {p3, p4, p5, p6, p9, p10};
		for (Podstavek i : ba) i.ograje[0][3] = 1;
		Podstavek[] ac = {p9, p10};
		for (Podstavek i : ac) {
			i.ograje[2][3] = 1;
			i.ograje[3][1] = 1;
		}
		Podstavek[] ad = {p11, p12};
		for (Podstavek i : ad) {
			i.ograje[0][1] = 1;
			i.ograje[1][3] = 1;
		}
		Podstavek[] ae = {p2, p4, p5, p6, p7, p8, p9, p10, p11, p12};
		for (Podstavek i : ae) {
			i.ograje[0][2] = 1;
			i.ograje[3][0] = 1;
		}
		
		Podstavek[] vsiPodstavki = {p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12};
		
		//z ukazom podatki[i][j] bomo lahko dostopali do seznama stroškov prehoda iz polja (i,j) na sosedna polja
		for (int i=0; i<N; i++) {
			Random rand = new Random(); 
			int pl = rand.nextInt(11);
			Podstavek ploscica = vsiPodstavki[pl];
			Random rand2 = new Random(); 
			int rot = rand2.nextInt(3);
			ploscica.rotiraj(rot);
			
			
			for (int j=0; j<M; j++) {
				podatki[i][j] = ploscica.ograje[j];
			}
			if (i == 3 || i == 6) {
				//ko dodajamo podstavke, se morajo stroski prehodov spremeniti, ce so kaksne od ograj na robu podstavka
				// sosednje moznosti Z,D,S,L
				int vr3 = podatki[i][0][3] + podatki[i-3][1][1];
				podatki[i][0][3] = vr3;  
				podatki[i-3][1][1] = vr3;
				
				int vr4 = podatki[i][3][3] + podatki[i-3][2][1];
				podatki[i][3][3] = vr4;  
				podatki[i-3][2][1] = vr4;
			}
			else if (i == 1 || i == 2) {
				int vr1 = podatki[i][0][0] + podatki[i-1][3][2];
				podatki[i][0][0] = vr1;  
				podatki[i-1][3][2] = vr1;
				
				int vr2 = podatki[i][1][0] + podatki[i-1][2][2];
				podatki[i][1][0] = vr2;  
				podatki[i-1][2][2] = vr2;
			} 
			else if (i == 0) continue;
			else {
				int vr1 = podatki[i][0][0] + podatki[i-1][3][2];
				podatki[i][0][0] = vr1;  
				podatki[i-1][3][2] = vr1;
				
				int vr2 = podatki[i][1][0] + podatki[i-1][2][2];
				podatki[i][1][0] = vr2;  
				podatki[i-1][2][2] = vr2;
				
				int vr3 = podatki[i][0][3] + podatki[i-3][1][1];
				podatki[i][0][3] = vr3;  
				podatki[i-3][1][1] = vr3;
				
				int vr4 = podatki[i][3][3] + podatki[i-3][2][1];
				podatki[i][3][3] = vr4;  
				podatki[i-3][2][1] = vr4;
		}
		}
		for (int i=0; i<4; ++i) {
			koti[i] = false;
		}
		
		//pretvarjanje podatkov v matriko x, y koordinat, ki vsebuje par števil za ograje zgoraj in levo
		for (int i=0; i<N; i++) {
			for (int j=0; j<M; j++) {
				int[] ograje = new int[4];
				ograje = podatki[i][j];
				int[] zg_l = new int[2];
				zg_l[0] = ograje[0];
				zg_l[1] = ograje[3];
				int t = 0;
				if (j != 0) t = ((j + 3) % 3) / j;
				matrika[(i - ((i + 3) % 3))* 2/3 + t][5 - 2 * ((i + 3) % 3) - (j -(j + 2) % 2)/2] = zg_l;
			}
		}
		
	}
	
	
	/**
	 * Nova igra, v zacetni poziciji je prazna in na potezi je B.
	 */
	public Igra() {
		//igralec B se odloci, v katerem kotu bo zacel. Ta kot se potem nastavi na B in nasprotni na C.
		//Na zacetku so vsa mesta prazna.
		plosca = new Polje[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
		plosca[0][0]= Polje.B;
		// še ostale od 0- 4 in še za C nastavit ostale
		naPotezi = Igralec.B;
	}
	
	
//	/**
//	 * Nova kopija dane igre
//	 * 
//	 * @param igra
//	 */
//	public Igra(Igra igra) {
//		plosca = new Polje[N][M];
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < M; j++) {
//				plosca[i][j] = igra.plosca[i][j];
//			}
//		}
//		this.naPotezi = igra.naPotezi;
//	}
//	
	/**
	 * @return plosca polj (ne spreminjaj!)
	 */
	public Polje[][] getPlosca() {
		return plosca;
	}

	/**
	 * @return seznam moznih potez
	 */
	public List<Poteza> poteze() {
		LinkedList<Poteza> ps = new LinkedList<Poteza>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.PRAZNO) {
					ps.add(new Poteza(i, j));
				}
			}
		}
		return ps;
	}

	/**
	 * @param t
	 * @return igralec, ki ima zapolnjeno vrsto @{t}, ali {@null}, Äe nihÄe
	 */
	private Igralec cigavKot(Kot t) {
		int count_C = 0;
		int count_B = 0;
		for (int k = 0; k < N && (count_C == 0 || count_B == 0); k++) {
			switch (plosca[t.x[k]][t.y[k]]) {
			case B: count_B += 1; break;
			case C: count_C += 1; break;
			case PRAZNO: break;
			}
		}
		if (count_B == N) { return Igralec.B; }
		else if (count_C == N) { return Igralec.C; }
		else { return null; }
	}

	/**
	 * @return zmagovalni kot, ali {@null}, ce ga ni
	 */
//	public Vrsta zmagovalnaPoteza() {
//		for (Kot t : koti) {
//			Igralec lastnik = cigavaVrsta(t);
//			if (lastnik != null) { return t; }
//		}
//		return null;
//	}
//	
//	/**
//	 * @return trenutno stanje igre
//	 */
//	public Stanje stanje() {
//		// Ali imamo zmagovalca?
//		Vrsta t = zmagovalnaVrsta();
//		if (t != null) {
//			switch (plosca[t.x[0]][t.y[0]]) {
//			case O: return Stanje.ZMAGA_O; 
//			case X: return Stanje.ZMAGA_X;
//			case PRAZNO: assert false;
//			}
//		}
//		// Ali imamo kakĹĄno prazno polje?
//		// Äe ga imamo, igre ni konec in je nekdo na potezi
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				if (plosca[i][j] == Polje.PRAZNO) {
//					if (naPotezi == Igralec.O) {
//						return Stanje.NA_POTEZI_O;
//					}
//					else {
//						return Stanje.NA_POTEZI_X;
//					}
//				}
//			}
//		}
//		// Polje je polno, rezultat je neodloÄen
//		return Stanje.NEODLOCENO;
//	}
//
//	/**
//	 * Odigraj potezo p.
//	 * 
//	 * @param p
//	 * @return true, Äe je bila poteza uspeĹĄno odigrana
//	 */
//	public boolean odigraj(Poteza p) {
//		if (plosca[p.getX()][p.getY()] == Polje.PRAZNO) {
//			plosca[p.getX()][p.getY()] = naPotezi.getPolje();
//			naPotezi = naPotezi.nasprotnik();
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
}