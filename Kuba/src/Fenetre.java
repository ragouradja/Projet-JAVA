import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;


public class Fenetre extends JPanel{
	Plateau p;

	private Direction dir;
	
	// -1 valeur par defaut pour détecter une valeur nulle
	private int i = - 1 ,j = -1;
	int rejouer = -1 ;
	
	static final int WIDTH = 900, HEIGHT = 750;
	
	// Position pour positionner les flèches autour d'un rectangle
 	int x_RECTFLECHE = 750;
	int y_RECTFLECHE = 100;
	int DIM_RECTFLECHE = 50;
	
	   
	int xTEXTE = 750;
	int yTEXTE = 100;
    int DIM_TEXTE = 600;

	int xPLAT = 10;
	int yPLAT = 10;
	
	int xZONE_TEXTE = 10;
	int yZONE_TEXTE = 675;
    
	// Images
    BufferedImage img_back; 
    BufferedImage img_bille_blanche,img_bille_rouge,img_bille_NOIRE;
    BufferedImage img_plateau;
    BufferedImage img_fleche_nord;
    BufferedImage img_fleche_sud;
    BufferedImage img_fleche_ouest;
    BufferedImage img_fleche_est;
    BufferedImage img_oui;
    BufferedImage img_non;
   
    
    JLabel texte = new JLabel("");

	private JButton ButtonOUI;

	private JButton ButtonNON;

    boolean FIN = false;

    
    Fenetre(Plateau p)
    {
    	
    	this.setLayout(null);
    	this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
    	
    	createBackgroundImage ();
    	loadForegroundImage (); 
    	listener();
    	createButtons();
    	buttonChoix();
    	areaText();

    	this.p = p;
    }
		
	
    protected void paintComponent (Graphics g) {
    	 
        int DEBUT_GRIDx = 33;
    	int DEBUT_GRIDy = 35;
    	int DIM_GRID = 87;
    	
    	int y = DEBUT_GRIDy;
    	int x = DEBUT_GRIDx;	
    	
    	int DIM_BILLE = 60;
    	int DIM_PLATEAU = 650;
    	Graphics2D g2 = (Graphics2D) g;
        BufferedImage img = null;
        int c = 0;
   
    	
    	g.drawImage (img_back, 0, 0, null);  
    	g.drawImage(img_plateau, xPLAT ,yPLAT,DIM_PLATEAU,DIM_PLATEAU,null);

    
    	// Gras et epaisseur
    	g2.setStroke(new BasicStroke(4));
    	g.setFont( new Font("",Font.BOLD, 15));
    	
   
    	// Formation de la grille (drawRect) sur le plateau + placement des billes
    	for(int i=0; i<7; i++)
    	{    	
    		for(int j = 0; j < 7 ; j++)
    		{
    			if((j < 6 && i < 6))
    					g.drawRect(x + DIM_GRID /2,y + DIM_GRID / 2, DIM_GRID, DIM_GRID);
    			if(p.plateau[i][j].bille != null) {
    				    			
	    			if(p.plateau[i][j].bille == Contenu.ROUGE)
	    				img = img_bille_rouge;    				
	  
	    			if(p.plateau[i][j].bille == Contenu.BLANC)    				
	    				img = img_bille_blanche;
	    		
	    			if(p.plateau[i][j].bille == Contenu.NOIRE)    				
	    				img = img_bille_NOIRE;
	    			
	    			g.drawImage (img, x - ( DIM_GRID/2 -(img.getWidth() / 2 )) ,
	    					y - (DIM_GRID/2 - (img.getHeight() / 2 )), DIM_BILLE,DIM_BILLE,null); 
	    			
	    			
    			}
    			if(i == 0)
    				g.drawString(""+j,  x + DIM_GRID/2, DEBUT_GRIDy - 7);	

	    		x+= DIM_GRID;
    			
    		}
    		c = 'G' - i;
    		g.drawString(""+(char)c, DEBUT_GRIDx - 18, y + DIM_GRID/2);
    		y += DIM_GRID;	
    		x = DEBUT_GRIDx;
    	}
    	
    	// Zone de texte
    	g.setColor(Color.WHITE);
    	g.drawRect(xZONE_TEXTE, yZONE_TEXTE, 650,  50);
    	g.fillRect(xZONE_TEXTE, yZONE_TEXTE, 650,  50);
    	
   
    	
    }
    

   void buttonChoix() // Pb avec ces boutons, ils ne s'affichent que si on passe la souris dessus ( à droite du rectangle blanc lors du "Rejouer ?") 
   				     // ou alors ils s'affichent 1 fois sur 2
   {
	   this.ButtonOUI = new JButton(new ImageIcon(img_oui));
	   this.ButtonNON = new JButton(new ImageIcon(img_non));
	
	  
	   ButtonOUI.setVisible(false);
	   ButtonNON.setVisible(false);
	
	   ButtonOUI.setLocation(700,663);
	   ButtonOUI.setSize(img_oui.getHeight(),img_oui.getWidth());
    	

	   ButtonNON.setLocation(800, 663);
	   ButtonNON.setSize(img_non.getHeight(),img_non.getWidth());  

	   ActionListener OUI = new ActionListener () {
		
			public void actionPerformed (ActionEvent e) {
				rejouer = 1;
				ButtonOUI.setVisible(false);
				ButtonNON.setVisible(false);
			}
	
		};
		ActionListener NON = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				rejouer = 0;
				ButtonOUI.setVisible(false);
				ButtonNON.setVisible(false);
			}
		};
		ButtonOUI.addActionListener(OUI);
		
		ButtonOUI.setOpaque(false);
     	ButtonOUI.setContentAreaFilled(false);
     	ButtonOUI.setBorderPainted(false);
		
    
     	
     	ButtonNON.setOpaque(false);
     	ButtonNON.setContentAreaFilled(false);
     	ButtonNON.setBorderPainted(false);
     	ButtonNON.addActionListener(NON);
     	  	
     	
     	
     	this.add(ButtonOUI);
     	this.add(ButtonNON);
        
   }
   
   // Fleches
   void createButtons()
   {
    	JButton ButtonNORD = new JButton(new ImageIcon(img_fleche_nord));
    	JButton ButtonSUD = new JButton(new ImageIcon(img_fleche_sud));
    	JButton ButtonOUEST= new JButton(new ImageIcon(img_fleche_ouest));
    	JButton ButtonEST = new JButton(new ImageIcon(img_fleche_est));

    	JButton[] tabButton = {ButtonNORD,ButtonSUD,ButtonOUEST,ButtonEST};
    	
    	
    	ActionListener NORD = new ActionListener () {
		
			public void actionPerformed (ActionEvent e) {
				dir = Direction.NORD;
			}
	
		};
		ActionListener SUD = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				dir = Direction.SUD;
			}
		};
		ActionListener OUEST = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				dir = Direction.OUEST;
			}
		};
		ActionListener EST = new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				dir = Direction.EST;
			}
		};
		ActionListener[] tabListener = {NORD,SUD,OUEST,EST};

		
		
		for(int i = 0; i < 4; i++) {
			//tabButton[i].setLayout(null);			
			tabButton[i].setSize(60,60);
			tabButton[i].setOpaque(false);
			tabButton[i].setContentAreaFilled(false);
			tabButton[i].setBorderPainted(false);			
			tabButton[i].addActionListener(tabListener[i]);
			this.add(tabButton[i]);
		}
		
		ButtonNORD.setLocation(x_RECTFLECHE, y_RECTFLECHE - img_fleche_nord.getHeight());
		ButtonSUD.setLocation(x_RECTFLECHE, y_RECTFLECHE + img_fleche_sud.getHeight());
		ButtonOUEST.setLocation(x_RECTFLECHE - img_fleche_ouest.getWidth(), y_RECTFLECHE);
		ButtonEST.setLocation(x_RECTFLECHE + img_fleche_est.getWidth(), y_RECTFLECHE);
   }
    
    
   	// On recupère les coordonnées dans la fonction Coords() qui va selon la position en x et y, donner la position (entier entre 0 et 6) dans le plateau
    void listener()
    {
	    MouseInputAdapter ma = new MouseInputAdapter() {
			public void mousePressed(MouseEvent e) {
				i = Coords(e.getY());
				j = Coords(e.getX());				
			}
			};
	    
		
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
    }
	
    

    void createBackgroundImage () {
    	this.img_back = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    	Graphics g_back = img_back.getGraphics ();
    	g_back.setColor ((Color.DARK_GRAY));
    	g_back.fillRect (0, 0, WIDTH, HEIGHT);
        }
	
 
    void loadForegroundImage () {
    	try {
    		img_bille_blanche = ImageIO.read (new File ("img/bille_blanche.png"));
    		img_bille_rouge = ImageIO.read (new File ("img/bille_rouge.png"));
    		img_bille_NOIRE = ImageIO.read (new File ("img/bille_NOIRE.png"));
    		img_plateau = ImageIO.read (new File ("img/plateau.png"));
    		img_fleche_nord = ImageIO.read (new File ("img/fleche_haut.png"));
    		img_fleche_sud = ImageIO.read (new File ("img/fleche_bas.png"));
    		img_fleche_ouest = ImageIO.read (new File ("img/fleche_gauche.png"));
    		img_fleche_est = ImageIO.read (new File ("img/fleche_droite.png"));
    		img_oui = ImageIO.read (new File ("img/OUI.png"));
    		img_non = ImageIO.read (new File ("img/NON.png"));
    		
    	} catch (IOException e) {
    	    e.printStackTrace ();
    	    System.exit (1);
    	}   
    	    	
    	
    }
 
    // Return un entier de 0 et 6 selon la position x captée par la souris au clique
    int Coords(int x)
    {
    	if( x > 40 && x < 100)
    		return 0;
    	if( x > 125 && x < 190)
    		return 1;
    	if( x > 215 && x < 280)
    		return 2;
    	if( x > 300 && x < 362)
    		return 3;
    	if( x > 390 && x < 460)
    		return 4;
    	if( x > 475 && x < 532)
    		return 5;
    	if( x > 566 && x < 622)
    		return 6;
    	return -1;
    }
    

    void resetDir()
    {
    	this.dir = null;
    }
    
    
    Direction getDir()
    {
    	return dir;
    }
   
    void resetCoords()
    {
    	i = -1;
    	j = -1;
    }
    int[] getCoords()
    {
    	int[] a = {i,j};
    	return a;
    }
    void resetRejouer()
    {
 	   rejouer = -1;
    }
    
    int getRejouer()
    { 	
    	ButtonOUI.setVisible(true);  	   
    	ButtonNON.setVisible(true); 	  
    	while(rejouer == -1)  		
    		System.out.print(""); 	    	   
 	   return rejouer;
    }
    
    void areaText()
    {
 	   texte.setFont(new Font("", Font.BOLD,18));
 	   texte.setBounds(15,395, DIM_TEXTE, DIM_TEXTE);	   

 	   this.add(texte);
    }
    
    void setText(String text)
    {
 	   texte.setText(text);
    }
    
    void win(Joueur j)
    {
    	JFrame frame = new JFrame ("WINNER !");
    	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    	FenetrePop fenetrepop= new FenetrePop(false);
    	frame.add (fenetrepop);
    	frame.setAlwaysOnTop(true);
    	frame.setResizable(false);
    	frame.pack ();
    	frame.setVisible (true);
    	frame.setLocationRelativeTo(null);
    	
    	fenetrepop.setTexte(j.win());
    	this.getFin(fenetrepop,frame);
    }
  
    void getFin(FenetrePop fenetrepop, JFrame frame)
    {
    	
    	while(!(FIN= fenetrepop.getFin()))
    		System.out.print("");
    	frame.setVisible(false);
    }
    
    boolean getFin()
    {
 
    	return FIN;
    }

}

