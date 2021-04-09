
public class Vue {


	public static void afficher(Plateau p)
	{
		System.out.println();
		for(int i = 0; i < p.getHauteur(); i++)
		{
			for(int j = 0; j < p.getLargeur(); j++)
			{
				if(p.plateau[i][j].bille!= null)
				
					switch(p.plateau[i][j].bille) {
						case ROUGE:
							System.out.print("R ");
							break;
						case BLANC:
							System.out.print("B ");
							break;
						case NOIRE:
							System.out.print("N ");
							break;
					}
				else
					System.out.print(". ");
			}
			System.out.println();
			
		}				
		
	}	
	
}
