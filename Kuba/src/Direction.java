
public enum Direction {

	NORD(-1,0,0),
	SUD(+1,0,1),
	OUEST(0,-1,2),
	EST(0,+1,3);
	static Direction[] dirs =  {NORD,SUD,OUEST,EST};
	static Direction[] dirsOpp =  {SUD,NORD,EST,OUEST};
	
	private int di,dj, indice;

	private Direction(int di, int dj, int indice) {
		this.di = di;
		this.dj = dj;
		this.indice = indice;
	}
	
	int getI()
	{
		return this.di;
	}
	
	int getJ()
	{
		return this.dj;
	}
	
	int getIndice()
	{
		return this.indice;
	}
}
