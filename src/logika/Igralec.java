package logika;

/**
 * @author AS
 * Mozni igralci.
 */

public enum Igralec {
	C, B;

	public Igralec nasprotnik() {	
		return (this == C ? B : C);
	}

	public Polje getPolje() {
		return (this == C ? Polje.C : Polje.B);
	}
}