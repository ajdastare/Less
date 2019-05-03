package logika;

/**
 * @author AS
 * Vsak podstavek ima mozna 4 polja za igralca. Oznacena so z 0 (zgoraj levo), 1 (zgoraj desno), 
 * 2 (spodaj desno) in 3 (spodaj levo). Polja so zaporedoma shranjena v seznamu. V množicah polj je zapisano, 
 * ce prehod iz tega polja na polje zgoraj, desno, spodaj ali levo vsebuje ograjo, torej ce bi nas prehod stal vec kot eno piko. 
 */

public class Podstavek {
	
	String ime;
	int [][] ograje; 
	int oblika; //mozne rotacije podstavka, stevilo med 0 in 3 nam da stevilo zasukov v desno za 90 stopinj
	
	
	//KONSTRUKTOR
	public Podstavek(String ime) {
		this.ime = ime;
		ograje = new int[4][4];
		this.oblika = 0; 
	}
	
	//METODE
	public void rotiraj(int n) {
		//spremeni obliko podstavka z n-kratnim rotiranjem v desno
		oblika = n;
		int[][] ograja1 = new int[4][4];
		int[][] ograja2 = new int[4][4];
		int m = 4;
		for (int i=0; i<m; ++i) { //po malih poljih
			for (int j=0; j<m; j++) { //ograje znotraj polj
				//rotiramo ograje
				if (j+n<m) ograja1[i][j+n] = ograje[i][j];
				else ograja1[i][n-(m-j)] = ograje[i][j];
			}
			if (i+n<m) ograja2[i+n] = ograja1[i];
			else ograja2[n-(m-i)]= ograja1[i];
		}
		ograje = ograja2;
	}
	
	public String toString() {
		return ime;
	}

}
