package logika;

public class Poteza {
	private int x; //crka polja (katera ploscica)
	private int y; //stevilka polja (katero polje znotraj ploscice)
	
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