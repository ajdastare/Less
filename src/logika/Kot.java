package logika;

/**
 * @author - AS
 * Objekt, ki predstavlja en kot na plosci.
 */
public class Kot {
	// Terica na plosci je predstavljena z dvema tabelama, prva dolžine 9, druga 4.
	// To sta tabeli x in y koordinat. X koordinata je plošča, na kateri smo, y pa polje plošče.
	public int[] x;
	public int[] y;
	
	public Kot(int[] x, int y[]) {
		this.x = x;
		this.y = y;
	}
}