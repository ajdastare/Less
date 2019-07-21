package logika;

import java.util.*;

public class Igra {


	// Velikost igralne pološče je N*2 x N*2.
	public static final int N = 9;
	public static final int M = 4;
	public int st_potezB = 3;

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

		//pretvarjanje podatkov v matriko x, y koordinat, ki vsebuje par števil za ograje zgoraj in desno
		// kličeš matrika [0][0][0] za zgornjo ograjo na (0,0)
		for (int i=0; i<N; i++) {
			for (int j=0; j<M; j++) {
				int[] ograje = new int[4];
				ograje = podatki[i][j];
				int[] zg_l = new int[2];
				zg_l[0] = ograje[0];
				zg_l[1] = ograje[1];
				int t = 0;
				if (j != 0) t = ((j + 3) % 3) / j;

				matrika[(i - ((i + 3) % 3))* 2/3 + t][5 - (5 - 2 * ((i + 3) % 3) - (j -(j + 2) % 2)/2)] = zg_l;

			}

		}

	}


	/**
	 * Nova igra, v zacetni poziciji je prazna in na potezi je B.
	 */
	public Igra() {
		//igralec B se odloci, v katerem kotu bo zacel. Ta kot se potem nastavi na B in nasprotni na C.
		//Na zacetku so vsa mesta prazna.
		plosca = new Polje[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}

		}

//
		plosca[0][0]= Polje.B;
		plosca[1][1]= Polje.B;
		plosca[1][0]= Polje.B;
		plosca[0][1] = Polje.B;

		plosca[4][4] = Polje.C;
		plosca[4][5] = Polje.C;
		plosca[5][4] = Polje.C;
		plosca[5][5] = Polje.C;
//
		// še ostale od 0- 4 in še za C nastavit ostale
		naPotezi = Igralec.B;


	}


//	/**
//	 * Nova kopija dane igre
//	 *
//	 * @param igra
//	 */
	public Igra(Igra igra) {
		plosca = new Polje[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				plosca[i][j] = igra.plosca[i][j];
			}
		}
//
		this.naPotezi = igra.naPotezi;
	}

	// tu dobimo kdo je na potezi

	/**
	 * @return plosca polj (ne spreminjaj!)
	 */
	public Polje[][] getPlosca() {
		return plosca;
	}

	/**
	 * @return seznam moznih potez
	 */
//	to so te poteze ki so še na voljo torej ni nobenega gor
// tiste poteze ki jih je mozno opravit na trenutni plosci

	public List<Poteza> poteze() {
		LinkedList<Poteza> ps = new LinkedList<Poteza>();
		if (naPotezi == Igralec.C ) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {


				// mislm da morm locit na vodoravno navpicno
					if (plosca[i][j] == Polje.C) {


					// na desno
					boolean na_desno = false;
					boolean na_levo = false;
					boolean gor = false;
					boolean dol = false;
					boolean preskakujemo = false;

					if(i+1 < 6) {
					if(plosca[i+1][j] == Polje.PRAZNO){
						na_desno = true;

					}
					}
					if(i-1 >=0) {
					if(plosca[i-1][j]== Polje.PRAZNO) {
						na_levo = true;

					}
					}
					if(j+1 <6) {
					if(plosca[i][j+1]== Polje.PRAZNO) {
						dol = true;
					}
					}
					if(j-1>=0) {
					if(plosca[i][j-1]== Polje.PRAZNO) {
						gor = true;
					}
					}


					if(na_levo) {
						ps.add(new Poteza(i,j,i-1,j));
					}else if (i-2 >= 0) {
							if(plosca[i-2][j]== Polje.PRAZNO) {

								if(matrika[i-1][j][1] == 0 && matrika[i-2][j][1]== 0) {
									preskakujemo = true;
									ps.add(new Poteza(i,j,i-2,j));
								}else {
									preskakujemo = false;
								}
							}else {
								preskakujemo = false;
							}
						}
					if(na_desno) {
						ps.add(new Poteza(i,j,i+1,j));

					}else if(i+2 < 6) {
							 if(plosca[i+2][j]== Polje.PRAZNO) {
								if(matrika[i+1][j][1] == 0 && matrika[i][j][1]==0) {
									ps.add(new Poteza(i,j,i+2,j));
								}else {
									preskakujemo = false;}
								}else {
									preskakujemo = false; 
								}

						}
					else if(gor){
						ps.add(new Poteza(i,j,i,j-1));
					}else if(j-2 >= 0) {
						if(plosca[i][j-2]== Polje.PRAZNO) {
							if(matrika[i][j][0]== 0 && matrika[i][j-1][0]== 0) {
								ps.add(new Poteza(i,j,i,j-2));
								preskakujemo = true;
							}else {
								preskakujemo = false;
							}

						}
						else {
							continue;
						}
						// torej zgoraj je nek krogec - preskakujemo


					}
					if(dol) {
						ps.add(new Poteza(i,j,i,j+1));
					}else if(j+2 <6) {
						if (plosca[i][j+2] == Polje.PRAZNO) {
							if(matrika[j][j+1][0]== 0 && matrika[i][j+2][0]==0) {
								ps.add(new Poteza(i,j,i,j+2));
							}
						}else {
							continue;
						}

					}
					}
				}
			}
		}
		else {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {


				// mislm da morm locit na vodoravno navpicno
					if (plosca[i][j] == Polje.B) {


					// na desno
					boolean na_desno = false;
					boolean na_levo = false;
					boolean gor = false;
					boolean dol = false;
					boolean preskakujemo = false;

					if(i+1 < 6) {
					if(plosca[i+1][j] == Polje.PRAZNO){
						na_desno = true;

					}
					}
					if(i-1 >=0) {
					if(plosca[i-1][j]== Polje.PRAZNO) {
						na_levo = true;

					}
					}
					if(j+1 <6) {
					if(plosca[i][j+1]== Polje.PRAZNO) {
						dol = true;
					}
					}
					if(j-1>=0) {
					if(plosca[i][j-1]== Polje.PRAZNO) {
						gor = true;
					}
					}


					if(na_levo) {
						ps.add(new Poteza(i,j,i-1,j));
					}else if (i-2 >= 0) {
							if(plosca[i-2][j]== Polje.PRAZNO) {

								if(matrika[i-1][j][1] == 0 && matrika[i-2][j][1]== 0) {
									preskakujemo = true;
									ps.add(new Poteza(i,j,i-2,j));
								} else {
									continue;
								}

							}else {
								preskakujemo = false;
							}
						}
					if(na_desno) {
						ps.add(new Poteza(i,j,i+1,j));

					}else if(i+2 < 6) {
							 if(plosca[i+2][j]== Polje.PRAZNO) {
								if(matrika[i+1][j][1] == 0 && matrika[i][j][1]==0) {
									ps.add(new Poteza(i,j,i+2,j));
								}else {
									preskakujemo = false;}
								}else {
									continue;
								}

						}
					else if(gor){
						ps.add(new Poteza(i,j,i,j-1));
					}else if(j-2 >= 0) {
						if(plosca[i][j-2]== Polje.PRAZNO) {
							if(matrika[i][j][0]== 0 && matrika[i][j-1][0]== 0) {
								ps.add(new Poteza(i,j,i,j-2));
								preskakujemo = true;
							}else {
								preskakujemo = false;
							}

						}
						else {
							continue;
						}
						// torej zgoraj je nek krogec - preskakujemo


					}
					if(dol) {
						ps.add(new Poteza(i,j,i,j+1));
					}else if(j+1 <6) {
						if (plosca[i][j+2] == Polje.PRAZNO) {
							if(matrika[j][j+1][0]== 0 && matrika[i][j+2][0]==0) {
								ps.add(new Poteza(i,j,i,j+2));
							}
						}else {
							continue;
						}

					}
					}
				}
			}
		}
		return ps;
			}
		
		

//
//
//
//
//					else if(plosca[i+1][j] != Polje.PRAZNO) {
//						if(i+ 2 > 5) {continue;
//						}else if(plosca[i+2][j]== Polje.PRAZNO) {
//							if(matrika[i+1][j][1] == 0 && matrika[i+2][j][1]==0) {
//								ps.add(new Poteza(i,j,i+2,j));
//							}
//
//						}
//
//					}else if(plosca[i-1][j] == Polje.PRAZNO){
//					ps.add(new Poteza(i,j, i-1, j));}
//					else if (plosca[i-1][j] != Polje.PRAZNO) {
//						if(plosca[i-2][j] == Polje.PRAZNO) {
//							if(matrika[i][j][1] == 0 && matrika[i-1][j][1]==0) {
//								ps.add(new Poteza(i,j,i-2,j));
//							}
//						}
//
//					}
//
//					else if(plosca[i][j+1] == Polje.PRAZNO){
//						ps.add(new Poteza(i,j, i, j+1));
//
//					}else if (plosca[i][j+1] != Polje.PRAZNO) {
//						if (plosca[i][j+2] == Polje.PRAZNO) {
//							if(matrika[j][j+1][0]== 0 && matrika[i][j+2][0]==0) {
//								ps.add(new Poteza(i,j,i,j+2));
//							}
//						}
//					}
//
//
//					else if(plosca[i][j-1]== Polje.PRAZNO){
//						ps.add(new Poteza(i,j, i,j-1));
//
//					}else if (plosca[i][j-1] != Polje.PRAZNO) {
//						if(plosca[i][j-2]== Polje.PRAZNO) {
//							if(matrika[i][j][0]== 0 && matrika[i][j-1][0]== 0) {
//								ps.add(new Poteza(i,j,i,j-2));
//							}
//
//						}
//
//					}
//
//					}
//				}
//			}
//
//		return ps;
//	}
//

	private Igralec cigavKot() {
		int count_C = 0;
		int count_B = 0;
		// ker so nastavljeni koti po "defaultu" se ve kdaj kdo zmaga
		// stetje za C
		for(int i = 0;i < 2 && (count_C <= 6|| count_B <= 6); i ++) {
			for(int j= 0;j < 2; j ++) {
			switch(plosca[i][i]) {
			case C: count_C += 1;break;
			case B: break;
			case PRAZNO: break;
			}
			}
			}
		for(int i = 4; i< 6 && (count_C <= 6 || count_B <= 6); i++) {
			for(int j = 4; j < 6; j ++ ) {
			switch(plosca[i][i]) {
			case C: break;
			case B: count_B += 1;
			case PRAZNO: break;

			}
		}
		}
		if (count_B == 4) { return Igralec.B; }
		if (count_C == 4) { return Igralec.C; }

		else { return null; }
	}




	/**
	 * @return zmagovalni kot, ali {@null}, ce ga ni
	 */

// to vrne zmagovalca  ne pa kota
	// ko vrne zmagovalca preveri nasprotnika
	public Igralec zmagovalnaPoteza() {
			Igralec lastnik = cigavKot();
			if (lastnik != null) { return lastnik; }
		return null;
	}

//	/**
//	 * @return trenutno stanje igre
//	 */

	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Igralec t = zmagovalnaPoteza();
		if (t != null & t == Igralec.C) {
			return Stanje.ZMAGA_C;
		}
		if(t!= null & t == Igralec.B) {
			return Stanje.ZMAGA_B;
		}

		else { if (naPotezi == Igralec.B) {
						return Stanje.NA_POTEZI_B;
					}
					else {
						return Stanje.NA_POTEZI_C;
					}
				}
			}



//
//	/**
//	 * Odigraj potezo p.
//	 *
//	 * @param p
//	 * @return true, Äe je bila poteza uspeĹĄno odigrana
//	 */


	public boolean odigraj(Poteza koncna) {
		System.out.println("smo v odigraj");

		int zac_X = koncna.getX0();
		int zac_Y = koncna.getY0();
		int konc_X= koncna.getX();
		int konc_Y = koncna.getY();

//
		boolean horizontalno = false;
		boolean vertikalno = false;
		boolean na_desno = false;
		boolean na_levo = false;
		boolean dol = false;
		boolean gor = false;

		if(zac_X != konc_X && zac_Y != konc_Y) {
			return false;
			// gremo navzkriz
		}
//
		if( zac_X == konc_X) {
			 vertikalno = true;
			 if(zac_Y < konc_Y) {
				 dol = true;
			 }else {
				 gor = true;
			 }
//
		 }
		 else {
			 horizontalno = true;
			 if(zac_X < konc_X) {
				 na_desno = true;
			 }else {
				 na_levo = true;
			 }
		 }
//
//
//		 // ugotovimo ali se premaknemo na levo ali na desno
//		 // oziroma gor ali dol
//		 // ce se premikamo vertikalno nas zanima samo zgornja ograja
//		// ce se premikamo horizontalno nas zanima samo leva ograja
//
		 if (horizontalno) {
			 if(plosca[konc_X][konc_Y] == Polje.PRAZNO) {
				 if(na_levo) {
					 // ce se premaknes za eno v levo, zanimajo nas samo desne ograje
					 if ((zac_X - konc_X) ==  1) {
						 int strosek = matrika[konc_X][zac_Y][1];
						 int stanje = st_potezB - strosek - 1; 
//						 st_potezB = st_potezB - strosek-1 ;

						 if(stanje > 0) {
							 st_potezB = st_potezB - strosek-1;
							 
							 System.out.println("strosek je "+ st_potezB);
							 return true;

							 }
						 if(stanje == 0) {
							 System.out.println("strosek je "+ st_potezB);
							plosca[zac_X][zac_Y] = naPotezi.getPolje();
							naPotezi = naPotezi.nasprotnik();
							st_potezB = 3;
							 return true;
							 }
						 if(stanje < 0 ){
							 System.out.println("strosek je "+ st_potezB);
							 return false;
							 }
						 }
					 // ce se premaknes za dva v levo - torej samo takrat ko nekoga preskocis(svojega igralca ali pa drugega)

					 if((zac_X - konc_X) == 2) {
						 if(plosca[zac_X - 1][zac_Y] != Polje.PRAZNO) {
							 if(zac_X + 1 <6 & konc_X <6) {
							 int strosek1 = matrika[zac_X+1][zac_Y][1];
							 int strosek2 = matrika[konc_X][zac_Y][1];

							 // če preskočimo se steje kot en korak in je OK, ce je vmes zid, ni OK
							 if (strosek1 >= 1 || strosek2 >=1 ) {
								 return false;
								 }
							 
							 else {
								 int stanje = st_potezB - strosek1-strosek2 - 1; 
								 
								 
								 if(stanje > 0) {
									 st_potezB = st_potezB - strosek1 -strosek2 - 1;
									 System.out.println("strosek je "+ st_potezB);
								 return true;
								 }
								 if(stanje == 0) {

									 plosca[zac_X][zac_Y] = naPotezi.getPolje();
									 naPotezi = naPotezi.nasprotnik();
									 st_potezB = 3;
									 System.out.println("strosek je "+ st_potezB);
									 return true;
								 	}
								 if(stanje < 0 ){
									 System.out.println("strosek je "+ st_potezB);
									 return false;
								 	}
							 }
						 }
						 
						 else {
							 return false;
						 }
						 }

					 }
					 if ((zac_X - konc_X) > 2) {
						 return false;
					 }

					 na_levo = false;

				 }

				 if(na_desno) {


					 if ((konc_X - zac_X) ==  1) {
						 int strosek = matrika[zac_X][zac_Y][1];
						 int stanje = st_potezB - strosek -1 ; 
						 
						 if(stanje > 0) {
							 st_potezB = st_potezB - strosek -1;
							 System.out.println("strosek je "+ st_potezB);
							 return true;
						 }
						 if(stanje == 0) {
							plosca[zac_X][zac_Y] = naPotezi.getPolje();
							naPotezi = naPotezi.nasprotnik();
							st_potezB = 3;
							System.out.println("strosek je "+ st_potezB);
							return true;

						 }
						 if(stanje < 0 ){
							 System.out.println("strosek je "+ st_potezB);
							 return false;
							 }
						 }
					 // ce se premaknes za dva v desno

					 if((konc_X - zac_X) == 2) {
						// če preskocimo se steje kot en korak in je OK, ce je vmes zid, ni OK
						 if(plosca[zac_X + 1][zac_Y]!= Polje.PRAZNO) {
							 int strosek1 = matrika[konc_X-1][konc_Y][1];
							 int strosek2 = matrika[zac_X][konc_Y][1];
							 if (strosek1 >= 1||strosek2 >= 1 ) {
								 return false;
								 }

							 else {
								 int stanje = st_potezB - strosek1 - strosek2 - 1;
								 

								 if(stanje > 0) {
									 st_potezB = st_potezB - strosek1 - strosek2 - 1;
									 System.out.println("strosek je "+ st_potezB);
									 return true;
								 }
								 if(stanje == 0) {
									 plosca[zac_X][zac_Y] = naPotezi.getPolje();
									 naPotezi = naPotezi.nasprotnik();
									 st_potezB = 3;
									 System.out.println("strosek je "+ st_potezB);
									 return true;
									 }
								 if(stanje < 0 ){
									 System.out.println("strosek je "+ st_potezB);
									 return false;
									 }
							 }

							 }
						 else {
							 return false;
							 }
						 }
					 if((konc_X - zac_X) > 2) {
						 return false;

					 }
					 na_desno = false;
					 }
				 }
			 else {
				 // ce polje ni prazno
				 return false;
			 }
			 horizontalno = false;
		 }
//
//
//
		 if (vertikalno) {
			 if(plosca[konc_X][konc_Y]== Polje.PRAZNO) {
				 // ce se premaknemo za 1

				 if(gor) {
					 if(zac_Y - konc_Y == 1) {
						 int strosek = matrika[zac_X][zac_Y][0];
						 int stanje = st_potezB - strosek-1;
						 if(stanje > 0) {
							 st_potezB = st_potezB - strosek-1;
							 System.out.println("strosek je "+ st_potezB);
							 return true;
						 }
						 if(stanje == 0) {
								 plosca[zac_X][zac_Y] = naPotezi.getPolje();
								 naPotezi = naPotezi.nasprotnik();
								 st_potezB = 3;
								 System.out.println("strosek je "+ st_potezB);
								 return true;

						 }
						 if(stanje < 0 ){
							 System.out.println("strosek je "+ st_potezB);
							 return false;
							 }


					 }
					 if (zac_Y - konc_Y == 2) {
						 if(plosca[zac_X][zac_Y-1]!= Polje.PRAZNO) {
							 int strosek1 = matrika[zac_X][zac_Y][0];
							 int strosek2 = matrika[zac_X][konc_Y+1][0];
							 if (strosek1 >=1 || strosek2 >=1) {
								 return false;
							 }
							 else {
								 int stanje = st_potezB - strosek1 - strosek2 -1;
								
								 if (stanje > 0 ) {
									 st_potezB = st_potezB - strosek1 - strosek2 -1 ;
									 System.out.println("strosek je "+ st_potezB);
									 return true;
								 }
								 if(stanje == 0) {
									 plosca[zac_X][zac_Y] = naPotezi.getPolje();
									 naPotezi = naPotezi.nasprotnik();
									 st_potezB = 3;
									 System.out.println("strosek je "+ st_potezB);
									 return true;
									 
								 }
								 if(stanje < 0) {
									 return false;
								 }
							
								 }
							 }


						 }
					 if(zac_Y - konc_Y > 2 ) {
						 return false;
					 }
					 gor = false;

					 }


				 if(dol) {
					 if(konc_Y -zac_Y== 1) {
						 int strosek = matrika[konc_X][konc_Y][0];
						 int stanje = st_potezB -strosek-1;
						 if(stanje > 0) {
							 st_potezB = st_potezB - strosek - 1;
							 System.out.println("strosek je "+ st_potezB);
							 return true;
						 }
						 if(stanje == 0) {
							 plosca[zac_X][zac_Y] = naPotezi.getPolje();
							 naPotezi = naPotezi.nasprotnik();
							 st_potezB = 3;
							 System.out.println("strosek je "+ st_potezB);
							 return true;
						 }
						 if(stanje < 0) {
							 return false;
						 }

					 }
					 if(konc_Y - zac_Y == 2) {
						 if(plosca[zac_X][zac_Y + 1]!= Polje.PRAZNO) {
							 int strosek1 = matrika[konc_X][konc_Y][0];
							 int strosek2 = matrika[konc_X][zac_Y+1][0];
							 int stanje = st_potezB - strosek1 - strosek2 -1; 
							 if(strosek1 >= 1|| strosek2 >= 1) {

								 return false;
								 }
							 if(stanje > 0 ) {
								 st_potezB = st_potezB - strosek1 - strosek2 -1;
								 System.out.println("strosek je "+ st_potezB);
								 return true;

							 	}
							 if(stanje == 0) {
								 plosca[zac_X][zac_Y] = naPotezi.getPolje();
								 naPotezi = naPotezi.nasprotnik();
								 st_potezB =3;
								 System.out.println("strosek je "+ st_potezB);
								 return true;

							 	}
							 if(stanje < 0){
								 return false;
								 }
							 }
						 else {
							 return false;
							 }
						 }
					 if(konc_Y - zac_Y > 2) {
						 return false;
						 }
					 dol = false;
					 }
				 }
			 // ce polje ni prazno
			 else {
				 return false;
				 }
			vertikalno = false;
			}
		return false;
		 }
	}

