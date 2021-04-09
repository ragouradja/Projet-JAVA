
import java.util.Random;


import javax.swing.JFrame;

public class Joueur{
	private Contenu camp;
	private int capture_rouge;
	private int capture_adv;
	private String nom;
	Joueur(){};
	
	Joueur(Contenu camp, String nom)
	{
		this.camp = camp;
		this.nom = nom;
	}
	public String toString()
	{
		return nom;
	}
	
	Contenu getCamp()
	{
		return this.camp;
	}
	int getCapRouge()
	{
		return this.capture_rouge;
	}
	
	int getCapAdv()
	{
		return this.capture_adv;
	}
	String presentation()
	{
		return (this + " a les pieces " + this.camp);
	}
	boolean gagnant()
	{
		if(this.capture_adv == 8  || this.capture_rouge == 7)
			return true;
		return false;
	}
	
	String win()
	{
		
		if(this.capture_rouge == 7)
			return (this.camp + " a gagne en capturant " + this.capture_rouge + " billes rouges !");
		else
			return (this.camp + " a gagne en capturant toutes les billes adverses !");
	}
	

	
	void coup(Plateau p, JFrame frame,Fenetre fenetre)
	{
		
		Random r = new Random();
		Coords co = new Coords();
		codeErreur code = codeErreur.CONTINUE;
		char choix = 'y';
		boolean choix_bot;
		
		
		p.incrCoup();
		fenetre.setText("Le tour est aux " + this.camp);
		Vue.afficher(p);
		
		if(this.nom == "Bot")
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		
		while(code != codeErreur.OK)
		{
			if(this.nom == "Bot")
				Interaction.getCoordsBot(this,co,p);
			
			else
				Interaction.getCoordsJoueur(this,fenetre,co);
			
			code = p.bougerBille(p,co.geti(), co.getj(), co.getDir(),this.camp,this.nom,frame,fenetre);
			
			if(code == codeErreur.EJECT || code == codeErreur.EJECT_ROUGE)
			{
				if(code == codeErreur.EJECT) this.capture_adv += 1;					
				else this.capture_rouge += 1;
					
				if(!this.gagnant())
				{
					
					if(this.nom == "Bot")
					{
						choix_bot = r.nextBoolean();
						if(!choix_bot) choix = 'n';
						
					}
					else
						if(fenetre.getRejouer() == 0) choix = 'n';
						
							
					fenetre.resetRejouer();
					
					if(choix == 'n')
					{
						fenetre.setText(this + " ne rejoue pas");
						code = codeErreur.OK;
					}			
					else
						fenetre.setText(this + " Rejoue");
				}
				else
					code = codeErreur.OK;
			}				
		
		}
		
	}
	
}
