package logika;

public class Poteza {
	public  int x; //plosca (podstavek), vrednosti med 0 in 5
	public  int y; //stevilka polja (katero polje znotraj ploscice), vrednosti med 0 in 5
	public int x0;
	public int y0;
	
	
	
	public Poteza(int x0, int y0, int x, int y) {
		this.x0 = x0;
		this.y0 = y0;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public int getY0() {
		return y0;
	}
	public int getX0() {
		return x0;
	}

	@Override
	public String toString() {
		return "Poteza [x=" + x + ", y=" + y + "]";
	}
}