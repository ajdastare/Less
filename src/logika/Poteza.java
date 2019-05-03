package logika;

public class Poteza {
	private int x; //plosca (podstavek), vrednosti med 0 in 8
	private int y; //stevilka polja (katero polje znotraj ploscice), vrednosti med 0 in 4
	
	public Poteza(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Poteza [x=" + x + ", y=" + y + "]";
	}
}