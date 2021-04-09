import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FenetrePop extends JPanel{
	
	static final int WIDTH = 500, HEIGHT = 300;
	static final int WIDTH_HALF = WIDTH/2, HEIGHT_HALF = HEIGHT/2;
	
	JButton ButtonBOT, ButtonJOUEUR;
	JButton ButtonBLANC, ButtonNOIRE;
	int BUTTONSIZE = 120;
	JButton ButtonOK;
	int OKSIZE = 60;
	int DIM_TEXTE = 400;
	
	BufferedImage img_bille_blanche,img_bille_NOIRE, img_back;
	
	JLabel texte = new JLabel("");
	
	
	boolean BOT_CHOIX = false;
	boolean JOUEUR_CHOIX = false;
	boolean BLANC_CHOIX = false;
	boolean NOIRE_CHOIX = false;
	boolean FIN = false;
	
	FenetrePop(boolean mode)
	{    	
		
		if(mode)
			FenetreDebut();		
	
		else
			FenetreFin();
	}
	
	
	void FenetreDebut()
	{
		this.setLayout(null);
    	this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
    	
    	ButtonDebut();
    	createBackgroundImage ();

	}
	
	void loadImage()
	{
		 
	    	try {
	    		img_bille_blanche = ImageIO.read (new File ("img/bille_blanche.png"));	    		
	    		img_bille_NOIRE = ImageIO.read (new File ("img/bille_NOIRE.png"));    		
	    	} catch (IOException e) {
	    	    e.printStackTrace ();
	    	    System.exit (1);
	    	}   
	    	    	
		    	
		    
	}
	
	  void createBackgroundImage () {
	    	this.img_back = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    	Graphics g_back = img_back.getGraphics ();
	    	g_back.setColor ((Color.BLACK));
	    	g_back.fillRect (0, 0, WIDTH, HEIGHT);
	   }
		
	void ButtonDebut()
	{
		loadImage();
		areaText("Choisir un adversaire et une bille");
		ButtonBOT = new JButton("BOT");
		ButtonJOUEUR = new JButton("JOUEUR");
		ButtonBLANC = new JButton(new ImageIcon(img_bille_blanche));
		ButtonNOIRE = new JButton(new ImageIcon(img_bille_NOIRE));
		
		JButton[] tabButton = {ButtonBOT,ButtonJOUEUR,ButtonBLANC,ButtonNOIRE};
		
		
		ActionListener BOT = new ActionListener () {
			
			public void actionPerformed (ActionEvent e) {
				BOT_CHOIX = true;
				JOUEUR_CHOIX = false;
				
			}
	
		};
		ActionListener JOUEUR = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				BOT_CHOIX = false;
				JOUEUR_CHOIX = true;
				
			}
		};
		ActionListener BLANC = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				BLANC_CHOIX = true;
				NOIRE_CHOIX = false;
				
			}
		};
		ActionListener NOIRE = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				BLANC_CHOIX = false;
				NOIRE_CHOIX = true;
				
			}
		};
		ActionListener[] tabListener = {BOT,JOUEUR,BLANC,NOIRE};

		
		
		for(int i = 0; i < 4; i++)
		{	
			tabButton[i].setSize(BUTTONSIZE,BUTTONSIZE);
			tabButton[i].setOpaque(false);
			tabButton[i].setContentAreaFilled(false);
			tabButton[i].setBorderPainted(false);			
			tabButton[i].addActionListener(tabListener[i]);
			this.add(tabButton[i]);
			
			
		}
		
		ButtonBOT.setLocation( WIDTH_HALF/2 - BUTTONSIZE/2,HEIGHT_HALF/2 - BUTTONSIZE / 2);
		ButtonJOUEUR.setLocation(WIDTH_HALF + WIDTH_HALF/2 - BUTTONSIZE/2,HEIGHT_HALF/2 - BUTTONSIZE / 2);
		ButtonBLANC.setLocation( WIDTH_HALF/2 - BUTTONSIZE/2,HEIGHT_HALF + HEIGHT_HALF/2 - BUTTONSIZE / 2);
		ButtonNOIRE.setLocation( WIDTH_HALF +WIDTH_HALF/2 - BUTTONSIZE/2,HEIGHT_HALF +HEIGHT_HALF/2 - BUTTONSIZE / 2);
		
		
	}
	int getMode()
	{
		while(!BOT_CHOIX && !JOUEUR_CHOIX)
			System.out.print("");
		if(BOT_CHOIX)
			return 0;
		else if(JOUEUR_CHOIX)
			return 1;
		return -1;
	}
	
	Contenu getCamp()
	{
		while(!BLANC_CHOIX && !NOIRE_CHOIX)
			System.out.print("");
		if(BLANC_CHOIX)
			return Contenu.BLANC;
		else if(NOIRE_CHOIX)
			return Contenu.NOIRE;
		return null;
	}

	void areaText(String text)
    {
	   texte.setLayout(null);
	   texte.setHorizontalAlignment(SwingConstants.CENTER);
	   texte.setVerticalAlignment(SwingConstants.NORTH);
 	   texte.setFont(new Font("", Font.BOLD,15));
 	   texte.setBounds(50,0, DIM_TEXTE, DIM_TEXTE);	   
 	   texte.setText(text);
 	   this.add(texte);
    }
    

	void FenetreFin()
	{
		this.setLayout(null);
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT_HALF));
		areaText("");  
		texte.setBounds(0,0, DIM_TEXTE+100, DIM_TEXTE+100);	   // Phrase plus longue
	 	   
		ButtonFin();
		
		
	}
	
	void ButtonFin()
	{
		ButtonOK = new JButton("OK");
		

		ActionListener EXIT = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				FenetrePop.this.END();
				
			}
		};
		
		
		ButtonOK.setSize(OKSIZE,OKSIZE);
		ButtonOK.setOpaque(true);
		ButtonOK.setContentAreaFilled(true);
		ButtonOK.setBorderPainted(true);			
		ButtonOK.addActionListener(EXIT);
		
	
		ButtonOK.setLocation(WIDTH_HALF - OKSIZE/2,HEIGHT_HALF/2 - OKSIZE / 2);
		this.add(ButtonOK);
		
		
	}
	
	void END()
	{
		FIN = true;
	}
	
	boolean getFin()
	{
		return FIN;
	}
	
	void setTexte(String text)
	{
		texte.setText(text);
	}


}
