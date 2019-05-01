package gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Vodja;
import logika.Igra;
import logika.Polje;
import logika.Vrsta;
import logika.Poteza;
import logika.Podstavek;

/**
 * Pravokotno obmocje, v katerem je narisano igralno polje.
 * 
 * @author AS
 *
 */
@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	
	private Vodja vodja;
	
	/**
	 * Relativna sirina crte
	 */
	private final static double LINE_WIDTH = 0.02;
	
	/**
	 * Relativni prostor okoli figuric
	 */
	private final static double PADDING = 0.3;
	
	public IgralnoPolje(Vodja vodja) {
		super();
		setBackground(Color.white);
		this.addMouseListener(this);
		
		this.vodja = vodja;
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	/**
	 * @return sirina enega kvadratka
	 */
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / Math.sqrt(Igra.N);
	}
	
	/**
	 * V graficni kontekst {@g2} narisi crnega v polje {@(i,j)}
	 * 
	 * @param g2
	 * @param i
	 * @param j
	 */
	private void paintC(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
		g2.drawOval((int)x, (int)y, (int)r , (int)r);
	}
	
	/**
	 * V graficni kontekst {@g2} narisi belega v polje {@(i,j)}
	 * @param g2
	 * @param i
	 * @param j
	 */
	private void paintB(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.gray);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
		g2.drawOval((int)x, (int)y, (int)r , (int)r);
	}
	
	private void narisiOgraje(Graphics2D g2, int[] ograje, int zacetekX, int zacetekY) {
		double h = squareWidth()/2;
		g2.setColor(Color.red);
		for (int i=0; i<ograje.length; i++) {
			if (ograje[0] == 1) {	
					g2.drawLine((int)(zacetekX),
						    (int)(zacetekY + h),
						    (int)(zacetekX + h),
						    (int)(zacetekY + h));
					
			}
			if (ograje[1] == 1) {	
				g2.drawLine((int)(zacetekX + h),
					    (int)(zacetekY + h),
					    (int)(zacetekX + h),
					    (int)(zacetekY + 0));
		}
			if (ograje[2] == 1) {	
				g2.drawLine((int)(zacetekX + h),
					    (int)(zacetekY + 0),
					    (int)(zacetekX +0),
					    (int)(zacetekY));		
		}
			if (ograje[3] == 1) {	
				g2.drawLine((int)(zacetekX + 0),
					    (int)(zacetekY),
					    (int)(zacetekX + 0),
					    (int)(zacetekY + h));
				
		}
		}
		
	}
	
	public void narisiPodstavek(Graphics2D g2, int[][] podstavek, int zaceteX, int zacetekY) {
		int h = (int) squareWidth()/2;
		g2.setColor(Color.red);
		int i = 3;
		int[] x_os = {zaceteX,zaceteX+h,zaceteX+h,zaceteX};
		int[] y_os = {zacetekY,zacetekY,zacetekY+h,zacetekY+h};
		while (i>=0) {
			int[] ograje = podstavek[i];
			narisiOgraje(g2, ograje, x_os[i], y_os[i]);
			i -= 1;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		int w = (int)squareWidth();

		// ce imamo zmagovalni kot, njegovo ozadje pobarvamo
//		Vrsta t = null;
//		if (vodja.igra != null) {t = vodja.igra.zmagovalnaPoteza();}
//		if (t != null) {
//			g2.setColor(new Color(255, 255, 196));
//			for (int k = 0; k < Igra.N; k++) {
//				int i = t.x[k];
//				int j = t.y[k];
//				g2.fillRect((int)(w * i), (int)(w * j), (int)w, (int)w);
//			}
//		}
		
		
		// crte
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH*2)));
		for (int i = 0; i < Math.sqrt(Igra.N)+1; i++) {
			g2.drawLine((int)(i * w),
					    (int)(0),
					    (int)(i * w),
					    (int)(Math.sqrt(Igra.N) * w));
			g2.drawLine((int)(0),
					    (int)(i * w),
					    (int)(Math.sqrt(Igra.N) * w),
					    (int)(i * w));
		}
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH*0.9)));
		for (int j = 0; j<Math.sqrt(Igra.N); ++j) {
			g2.drawLine((int)(j * w + w/2),
				    	(int)(0),
				    	(int)(j * w + w/2),
				    	(int)(Math.sqrt(Igra.N) * w));
			g2.drawLine((int)(0),
				     	(int)(j * w + w * 0.5),
				     	(int)(Math.sqrt(Igra.N) * w),
				     	(int)(j * w + w * 0.5));
	
		}
		
		//nastavljanje izgleda izzrebanih podstavkov
//		g2.setColor(Color.red);
//		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH*1.3)));
//		int[] x_os = {0,0,0,1,1,1,2,2,2};
//		int[] y_os = {0,1,2,0,1,2,0,1,2};
//		int d = 8;
//		while (d>=0) {
//			int[][] plosca = Igra.podatki[d];
//			narisiPodstavek(g2, Igra.podatki[d], x_os[d], y_os[d]);
//			d -= 1;
//		}
			
		
		System.out.println(vodja);
		
		// kriĹžci in kroĹžci
		Polje[][] plosca;;
		if (vodja.igra != null) {
			plosca = vodja.igra.getPlosca();
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch(plosca[i][j]) {
					case C: paintC(g2, i, j); break;
					case B: paintB(g2, i, j); break;
					default: break;
					}
				}
			}
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		if (vodja.clovekNaVrsti) {
//			int x = e.getX();
//			int y = e.getY();
//			int w = (int)(squareWidth());
//			int i = x / w ;
//			double di = (x % w) / squareWidth() ;
//			int j = y / w ;
//			double dj = (y % w) / squareWidth() ;
//			if (0 <= i && i < Igra.N &&
//					0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
//					0 <= j && j < Igra.N && 
//					0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
//				vodja.clovekovaPoteza(new Poteza(i, j));
//			}
//		}
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}
	
}