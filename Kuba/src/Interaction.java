import java.util.Random;

public class Interaction {

	
	static void getCoordsJoueur(Joueur j,Fenetre fenetre, Coords co)
	{
		int di;
		int dj;
		Direction dir;
		
		while((di = fenetre.getCoords()[0]) == -1 || (dj = fenetre.getCoords()[1]) == -1  || (dir = fenetre.getDir()) == null)
		{
			System.out.print("");
		}
		
		co.setCoords(di,dj,dir);
		fenetre.resetCoords();
		fenetre.resetDir();
	}
	
	static void getCoordsBot(Joueur j, Coords co, Plateau p)
	{
		Direction dir = null;
		int di,dj;
		
		Random r = new Random();
		
		di = r.nextInt(p.getHauteur());
		dj = r.nextInt(p.getLargeur());
		dir = Direction.dirs[r.nextInt(4)];
		
		co.setCoords(di, dj, dir);
	}
}
