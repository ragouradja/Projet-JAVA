public enum Contenu {
	BLANC(1),
	NOIRE(-1),
	ROUGE(0);
	
	private int team;
	
	Contenu(int team)
	{
		this.team = team;
	}
	
	int getTeam()
	{
		return this.team;
	}
	
	Contenu getTeamAdv()
	{
		if(this.team == 1)
			return Contenu.NOIRE;
		else
			return Contenu.BLANC;
	}
}
