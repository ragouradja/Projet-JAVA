 class Coords {
	Coords(){};
	
	int i;
	int j;
	Direction direction;
	
	int geti()
	{
		return this.i;
	}
	int getj()
	{
		return this.j;
	}
	
	Direction getDir()
	{
		return this.direction;
	}
	
	void setCoords(int di, int dj, Direction dir)
	{				
		this.i = di;
		this.j = dj;
		this.direction = dir;
	
	}
	
	


	
}
