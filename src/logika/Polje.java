package logika;

/**
 * @author AS
 * Mozne vrednosti polj na plosci.
 */

public enum Polje {
	PRAZNO,
	C,
	B;

	public String toString() {
		switch (this) {
		case PRAZNO: return " ";
		case C: return "Č";
		case B: return "B";
		default: return "?";
		}
	}
}