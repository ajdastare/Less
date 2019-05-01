package logika;

import java.util.*;

/**
 * @author - AS
 * Objekt, ki predstavlja eno vrsto na plosci.
 */
public class Vrsta {
	// Terica na plosci je predstavljena z dvema tabelama, prva dolžine 9, druga 4.
	// To sta tabeli x in y koordinat. X koordinata je plošča, na kateri smo, y pa polje plošče.
	public int[] x;
	public int[] y;
	
	public Vrsta(int[] x, int y[]) {
		this.x = x;
		this.y = y;
	}

	
}