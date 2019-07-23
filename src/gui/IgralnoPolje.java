package gui;
import java.awt.BasicStroke;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

//import com.sun.tools.javac.code.Attribute;

import logika.Vodja;
import logika.Igra;
import logika.Polje;
import logika.Kot;
import logika.Poteza;
import logika.Pozicija;
import logika.Podstavek;

/**
 * Pravokotno obmocje, v katerem je narisano igralno polje.
 *
 * @author AS
 *
 */
@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	private JLabel statusbar;
	private Vodja vodja;
	boolean dragging = false;

//	LinkedList<Pozicija> pozicijaB = new LinkedList();
	Pozicija pozicija ;

	/**
	 * Relativna sirina crte
	 */
	private final static double LINE_WIDTH = 0.02;

	/**
	 * Relativni prostor okoli figuric
	 */
	private final static double PADDING = 0.3;

	public IgralnoPolje(Vodja vodja) {

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
	private int squareWidth() {
		return (int) (Math.min(getWidth(), getHeight()) / Math.sqrt(Igra.N));
	}

	/**
	 * V graficni kontekst {@g2} narisi crnega v polje {@(i,j)}
	 *
	 * @param g2
	 * @param i
	 * @param j
	 */

	private void paintC(Graphics2D g2, int i, int j) {
	
		int w = squareWidth();//500 x500
//		double h = w/12;

		double r = w /2 - 2* 6*w/2 *LINE_WIDTH ; // premer O
//
//		double x = (((i%3)*4)+1)*h;
//		if (j == 1 || j == 3) {
//			x =  x + 2 * h;
//
//		}
//
//		// + 1 če je j 1 ali 3, oziroma + 0 če je j (0 ,2)

		double x = i+6*w/2 *LINE_WIDTH;
//		double y = ((i/3) * 4 +1)*h;
//		if (j == 2||j == 3) {
//			y = y + 2 * h;
//
//		}
//		y = y/10;
//
		double y = j +6*w/2 *LINE_WIDTH;
//		System.out.println("nariši C na" + x+","+y+ "koordinatah");

		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float)(w* LINE_WIDTH), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
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
		
		int w = squareWidth();//500 x500
		double h = w/12;


		double r = w /2 - 2* 6*w/2 *LINE_WIDTH ; // premer O

//
		double x = i+6*w/2 *LINE_WIDTH;
//
		double y = j + 6*w/2 *LINE_WIDTH;
//		System.out.println("nariši B na" + x+","+y+ "koordinatah");
		g2.setColor(Color.ORANGE);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
		g2.drawOval((int)x, (int)y, (int)r , (int)r);
	}




	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		int w = squareWidth();

//		 ce imamo zmagovalni kot, njegovo ozadje pobarvamo
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
		g2.setColor(Color.gray);
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
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH * 0.9)));
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
		System.out.println(vodja);

		//nastavljanje izgleda izzrebanih podstavkov

		int h = w/2;

		g2.setColor(Color.red);
		int[][][] matrika = Igra.matrika;
		for (int i=0; i<matrika[0].length; i++) {
			for (int j=0; j<matrika.length; j++) {
				if (matrika[i][j][0] != 0) {
					g2.setStroke(new BasicStroke((float) (h * LINE_WIDTH * 3 * matrika[i][j][0])));
					g2.drawLine(
							(int)(i * h),
						    (int)(j * h),
						    (int)(i * h + h),
						    (int)(j * h));
				}
				if (matrika[i][j][1] != 0) {
					g2.setStroke(new BasicStroke((float) (h * LINE_WIDTH * 3 * matrika[i][j][1])));
					g2.drawLine(
							(int)(i * h + h),
						    (int)(j * h),
						    (int)(i * h + h),
						    (int)(j * h + h));
				}
			}
		}



		System.out.println(vodja);

		Polje[][] plosca;


		if (vodja.igra != null) {
			plosca = vodja.igra.getPlosca();
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {
					switch(plosca[i][j]) {
					case C: paintC(g2, i * h,j* h); break;
					case B: paintB(g2,i * h , j*h); break;
//					case PRAZNO: paintPrazno(g2,x_os[i], y_os[j]);break;
					default: break;
					}
							}

		}
		}
		}


//	@Override
	public void mouseClicked(MouseEvent e) {

	}





	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int w = (int)(squareWidth());
		int h = w/2;
		int i = x /h ;
//		double di = (x % h) / h;
		int j = y / h ;
//		double dj = (y % h) / h ;


		// trenutni (i,j) bi radi zabeležili da bomo lahko izračunali poteze

		 Pozicija zacetna1 = new Pozicija(i,j);

		if(vodja.clovekNaVrsti) {
			if(0 <= i && i < 6 &&
					0 <= j && j < 6
					) {
			pozicija = zacetna1;

//			
			}

		}
//		if(vodja.naVrstiC){
//			if(0 <= i && i < 6 &&
//					0 <= j && j < 6
//					) {
//			pozicija= zacetna1;
//			}
//		}
//			
		}
	




	public void mouseDragged(MouseEvent e) {
//		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int w = (int)(squareWidth());
		int h = w/2;
		int i = x /h ;
		double di = (x % h) / h;
		int j = y / h ;
		double dj = (y % h) / h ;

		// trenutni (i,j) bi radi zabeležili da bomo lahko izračunali poteze

		Poteza koncna = new Poteza(pozicija.getX(),pozicija.getY(),i,j);
	




		if (vodja.clovekNaVrsti) {
			if (0 <= i && i < 6 &&
					0 <= j && j < 6 ) {

				vodja.clovekovaPoteza(koncna);


			}
			repaint();

			}


//		if (vodja.naVrstiC) {
//			if (0 <= i && i < 6 &&
//					0 <= j && j < 6 ) {
//
//				vodja.clovekovaPoteza(koncna);
//
//
//			}
//			repaint();
//
//		}
	}






	@Override
	/* enter space
	 */

	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}



}
