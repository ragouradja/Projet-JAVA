import javax.swing.JFrame;

public class Plateau{ 
	Case[][] plateau; // Plateau où les coups sont joués (Enregistre le Coup 4 --> actuel)
	Case[][] plateau2; // Plateau pour comparer les configurations avec plateau (Enregistre le Coup 0 --> Configuration)
	Case[][] plateauT1; // Plateau  de transition pour permettre à plateau2 d'enregistrer le coup suivant (Enregistre le Coup 1)
	Case[][] plateauT2;	// Plateau  de transition pour permettre à plateauT1 d'enregistrer le coup suivant (Enregistre le Coup 2)
						//  pour que plateau2 fasse de même 
	Case[][] plateau3; // Plateau pour revenir en arrière si plateau2 et plateau ont des contenus similaires (Enregistre le Coup 3)
	
	private int HAUTEUR,LARGEUR;
	private int coup = 1;
	Plateau(){}

	
	Plateau(int H, int L)
	{
		HAUTEUR = H;
		LARGEUR = L;
		plateau = new Case[HAUTEUR][LARGEUR];
		plateau2 = new Case[HAUTEUR][LARGEUR];
		plateau3 = new Case[HAUTEUR][LARGEUR];
		plateauT1 = new Case[HAUTEUR][LARGEUR];
		plateauT2 = new Case[HAUTEUR][LARGEUR];
	}
	

	int getHauteur()
	{
		return this.HAUTEUR;
	}
	
	int getLargeur()
	{
		return this.LARGEUR;
	}
	int getCoup()
	{
		return this.coup;
	}
	void incrCoup()
	{
		this.coup += 1;
	}
	
	void init()
	{
		for(int i = 0; i < HAUTEUR; i++)
			for(int j = 0; j < LARGEUR; j++)
			{
				plateau[i][j] = new Case();
				plateau2[i][j] = new Case();
				plateau3[i][j] = new Case();
				plateauT1[i][j] = new Case();
				plateauT2[i][j] = new Case();
			}
		this.placerBilles(this);
		copiePlateau(this.plateau,this.plateau2);
		copiePlateau(this.plateau,this.plateau3);
		copiePlateau(this.plateau,this.plateauT1);
		copiePlateau(this.plateau,this.plateauT2);
	}	
	
	
	// Placer les pions pour chaque coins du plateau
	void carre(Plateau p, Contenu camp, int i, int j)
	{
		int n = p.plateau.length - 2;

		if(i > n || j > n)
			return;
		for(int l=i; l<= i+1;l++)
			for(int k=j;k<=j+1;k++)
				p.plateau[l][k].bille = camp;
		
		
		carre(p,Contenu.NOIRE,i,j+n);
		carre(p,Contenu.NOIRE,i+n,j);
		carre(p,Contenu.BLANC,i+n,j+n);
		
	}
	
	
	void placerBilles(Plateau p)
	{
		int y = 2, x = 2;
		
		carre(p,Contenu.BLANC,0,0);
		
		// On place les 9 billes rouges qui forment un carre 3x3
		for(int i = 2; i < y + 3 ; i++)
			for(int j = 2; j < x + 3; j++)
				p.plateau[i][j].bille = Contenu.ROUGE;
		
		// Puis les 4 billes  restants a la main
		p.plateau[3][1].bille = Contenu.ROUGE;
		p.plateau[3][5].bille = Contenu.ROUGE;
		p.plateau[1][3].bille = Contenu.ROUGE;
		p.plateau[5][3].bille = Contenu.ROUGE;
		
	}
	

	// Voir si la prochaine case (selon la direction) est hors plateau ou non
	boolean horsPlateau(Plateau p, int i, int j, Direction direction)
	{
		int i2 = i+direction.getI(), j2 = j+direction.getJ();
		if(i2 >= p.getHauteur() || i2 < 0 || j2 >= p.getLargeur() || j2 < 0)
			return true;
		return false;
	}
	
	// Voir si la case actuelle est hors plateau ou non
	boolean horsPlateau(Plateau p, int i, int j)
	{
		if(i >= p.getHauteur() || i < 0 || j >= p.getLargeur() || j < 0)
			return true;
		return false;
	}
	boolean coins(Plateau p, int i, int j)
	{
		
		if(i == 0 || i == p.getHauteur() - 1)
			if(j == 0 || j == p.getLargeur() - 1)
				return false;
		return true;
	}
	
	
	codeErreur mouvementCheck(Plateau p, int i, int j, Direction direction, Contenu camp)
	{
		Direction diropp = Direction.dirsOpp[direction.getIndice()];
		int i2 = i+direction.getI(), j2 = j+direction.getJ();
		int i0 = i+diropp.getI(), j0 = j+diropp.getJ();
		
		
		if(p.plateau[i][j].bille == null)
			return codeErreur.PAS_DE_BILLE;
		if(horsPlateau(p,i,j,direction))
			return codeErreur.HORS_TAB;
		if(p.plateau[i][j].bille != camp)
			return codeErreur.CAMP_ADV;
		if(!horsPlateau(p,i,j,diropp))
			if(p.plateau[i0][j0].bille != null)
				return codeErreur.OPPOSE;
		if(p.plateau[i2][j2].bille != null)
			return codeErreur.PAS_LIBRE;
		
		
		return codeErreur.OK;
	}
	
	// Fonction recursive pour pousser les pions
	codeErreur pousserBille(Plateau p, int i, int j, Direction direction, Contenu bille)
	{
		int i2 = i + direction.getI(), j2 = j + direction.getJ();
		codeErreur code;
		
		if(horsPlateau(p,i,j) || p.plateau[i][j].bille == null )
			return codeErreur.STOP;
		
		// Appel de la fonction jusqu a atteindre les limites du plateau ou une place vide
		code = pousserBille(p,i2,j2,direction,bille);
		
		if(code == codeErreur.MEME_CAMP)
			return code;
		
			
		// Si la prochaine case est hors plateau, on procede a l ejection sauf si la derniere bille est du meme camp que la bille qui pousse
		if(horsPlateau(p,i,j,direction))
		{
			if(p.plateau[i][j].bille == bille)
				return codeErreur.MEME_CAMP;
			if(	p.plateau[i][j].bille == Contenu.ROUGE)
			{				
				p.plateau[i][j].bille = null;
				return codeErreur.EJECT_ROUGE;
			}
			p.plateau[i][j].bille = null;
			return codeErreur.EJECT;
		}
		
		// Si on atteint une place vide, on pousse tous les pions un a un
		if(p.plateau[i2][j2].bille == null)
		{
			p.plateau[i2][j2].bille = p.plateau[i][j].bille;
			p.plateau[i][j].bille = null;
		}
		
		if(code == codeErreur.EJECT)
			return codeErreur.EJECT;			
		
		if(code == codeErreur.EJECT_ROUGE)
			return codeErreur.EJECT_ROUGE;	
		return codeErreur.OK;
	}
	
	boolean check_config(Plateau p)
	{
		
		for(int i = 0; i < p.HAUTEUR; i++)
			for(int j = 0; j < p.LARGEUR; j++)
				if(p.plateau2[i][j].bille !=  p.plateau[i][j].bille )
					return false;
			
		return true;
	}
	void copiePlateau(Case[][] src,Case[][] dest)
	{
		for(int i = 0; i < this.HAUTEUR; i++)
			for(int j = 0; j < this.LARGEUR; j++)
				dest[i][j].bille = src[i][j].bille;
			
		
	}
	codeErreur codeConfig(Plateau p, JFrame frame, Fenetre fenetre)
	{
		codeErreur code = null;
		int coup = p.getCoup();
		// Chaque plateau enregistre un coup afin d'enregistrer tout un cycle de coup
		if(coup == 1)
		{   // Les trois plateaux (3, T1, T2)  se déplaçent avec le vrai plateau, le plateau2 est laissé derrière car il enregistre la configuration juste avant ce coup
			copiePlateau(this.plateau,this.plateau3);
			copiePlateau(this.plateau,this.plateauT1);
			copiePlateau(this.plateau,this.plateauT2);
		}
		if(coup == 2)
		{	// le plateau T1 est laissé derrière, il va permettre à plateau2 d'avancer d'un pas plus tard
			copiePlateau(this.plateau,this.plateau3);			
			copiePlateau(this.plateau,this.plateauT2);
			
		}
		if(coup == 3)
			// le plateau T2 est laissé derrère, il va permettre à plateauT1 d'avancer d'un pas, ce qui va permettre de garder la chaine entre les plateaux
			// Pour que plateau2 puisse avancer en décalé
			copiePlateau(this.plateau,this.plateau3);
			
		// Une fois que tous les plateaux sont en places, on peut commencer à vérifier les configurations
		if(coup > 3)
		{
			if(check_config(p)) // Si les configurations sont similaires, on retourne en arrière
			{

				copiePlateau(this.plateau3,this.plateau); // Retour arriere
				code = codeErreur.CONFIG;
				fenetre.setText(code.toString());
				return code;
				
			}
			else // Tous les plateaux avancent d'un pas
			{
				copiePlateau(this.plateauT1,this.plateau2); 
				copiePlateau(this.plateauT2,this.plateauT1);
				copiePlateau(this.plateau3,this.plateauT2);
				copiePlateau(this.plateau,this.plateau3);
				
			}
		}
	
			
		frame.repaint();
		return codeErreur.OK;
	
	}
	codeErreur bougerBille(Plateau p, int i, int j, Direction direction, Contenu camp, String nom, JFrame frame, Fenetre fenetre)
	{	
		codeErreur code = mouvementCheck(p,i,j,direction,camp);
		codeErreur code_eject = null;
		
		int i2 = i + direction.getI(), j2= j + direction.getJ();
		
		if(code != codeErreur.OK && code != codeErreur.PAS_LIBRE)
		{	
			if(nom != "Bot")
				fenetre.setText(code.toString());
			
			return codeErreur.COORDS;
		}
		
		if(code == codeErreur.OK)
		{
			p.plateau[i2][j2].bille = p.plateau[i][j].bille;
			p.plateau[i][j].bille = null;
		}
		if(code == codeErreur.PAS_LIBRE)
			code_eject = pousserBille(p,i,j,direction,p.plateau[i][j].bille);			
		
		
		if((codeConfig(p,frame,fenetre)) == codeErreur.OK)
		{
			if(code == codeErreur.PAS_LIBRE)
			{
				frame.repaint();
				if(nom !="Bot")
					fenetre.setText(code_eject.toString());
				return code_eject;
			}		
			frame.repaint();
		}
		else
			return codeErreur.COORDS;
		
		
		return codeErreur.OK;	
	}
}