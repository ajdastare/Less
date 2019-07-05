package logika;

public class Poteza {
	public static int x; //plosca (podstavek), vrednosti med 0 in 5
	public static int y; //stevilka polja (katero polje znotraj ploscice), vrednosti med 0 in 5
	
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