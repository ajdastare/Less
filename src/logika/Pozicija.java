package logika;

public class Pozicija {
	public static int x; //plosca (podstavek), vrednosti med 0 in 5
	public static int y; //stevilka polja (katero polje znotraj ploscice), vrednosti med 0 in 5
	
	public Pozicija(int x, int y) {
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
		return "Pozicija [x=" + x + ", y=" + y + "]";
	}
}


