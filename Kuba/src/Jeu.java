import java.awt.Frame;
import java.util.Scanner;

import javax.swing.JFrame;

public class Jeu{

	static void Jouer(Plateau p)
	{

		Joueur j1 = null;
		Joueur j2 = null;
		Contenu camp;
		int mode = -1;
		
		JFrame frame = new JFrame ("Kuba");
    	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    	FenetrePop fenetrepop= new FenetrePop(true);
    	frame.add (fenetrepop);
    	frame.setAlwaysOnTop(true);
    	frame.setResizable(false);
    	frame.pack ();
    	frame.setVisible (true);
    	frame.setLocationRelativeTo(null);
    	
    	
		if((camp = fenetrepop.getCamp()) != null && (mode = fenetrepop.getMode()) != -1)
			frame.setVisible (false);
		
		j1 = new Joueur(camp,"Joueur 1");
		
		if(mode == 1)
			j2 = new Joueur(camp.getTeamAdv(), "Joueur 2");
		else
			j2 = new Joueur(camp.getTeamAdv(), "Bot");
		
		
		
		jeu(p,j1,j2);
	}
	
	static void jeu(Plateau p, Joueur j1, Joueur j2)
	{		
		p.init();
		
		JFrame frame = new JFrame ("Kuba");
    	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    	Fenetre fenetre= new Fenetre(p);
    	frame.add (fenetre);
    	frame.setAlwaysOnTop(true);
    	frame.setResizable(false);
    	frame.pack ();
    	frame.setVisible (true);
    	frame.setLocationRelativeTo(null);
    	
    	fenetre.setText(j1.presentation() + " et " +j2.presentation());
		
		while(!j1.gagnant() && !j2.gagnant())	
		{

			j1.coup(p,frame,fenetre);
			
			if(j1.gagnant())
				break;
			j2.coup(p,frame,fenetre);
			
		}	
		frame.setAlwaysOnTop(false);
		if(j1.gagnant())
			fenetre.win(j1);
		else
			fenetre.win(j2);	
		
		while(!fenetre.getFin())
			frame.setVisible(true);
		
		frame.setVisible(false);
		System.exit(1);
	}
	
		
}
