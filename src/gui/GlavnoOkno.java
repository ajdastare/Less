package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Vodja;
import logika.Igralec;


/**
 * Glavno okno aplikacije, hrani trenutno stanje igre in nadzoruje potek
 * igre.
 * 
 * @author AS
 *
 */
@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega riĹĄemo X in O
	 */
	private IgralnoPolje polje;

	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;

	
	// Vodja igre
	private Vodja vodja;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;

	/**
	 * Ustvari novo glavno okno in pricni igrati igro.
	 */
	public GlavnoOkno() {
		
		this.setTitle("Less");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// vodja igre
		this.vodja = new Vodja(this);
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);

		igraClovekRacunalnik = new JMenuItem("Älovek â raÄunalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("RaÄunalnik â Älovek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);

		// igralno polje
		polje = new IgralnoPolje(vodja);

		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporocila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// zacnemo novo igro cloveka proti racunalniku
		
		vodja.novaIgra(Igralec.B);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			vodja.novaIgra(Igralec.B);
		}
		else if (e.getSource() == igraRacunalnikClovek) {
			vodja.novaIgra(Igralec.C);
		}
		
	}

	public void osveziGUI() {
		if (vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
//		
		else {
			switch(vodja.igra.stanje()) {
			case NA_POTEZI_B: status.setText("Na potezi je clovek"); break;
			case NA_POTEZI_C: status.setText("Na potezi je racunalnik"); break;
			case ZMAGA_B: status.setText("Zmagala je bela"); break;
			case ZMAGA_C: status.setText("Zmagala je črna"); break;
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			}
		}
		polje.repaint();
	}
	



}