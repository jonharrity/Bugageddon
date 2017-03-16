package game;

import Operators.SoldierBug;

public class BugContainer 
{
	SoldierBug bug;
	int row;
	
	public BugContainer(SoldierBug newBug, int newRow)
	{
		bug = newBug;
		row = newRow;
	}
	
	public SoldierBug getBug()
	{
		return bug;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public void setRow(int newRow){
		row = newRow;
	}
	
	public void setBug(SoldierBug newBug){
		bug = newBug;
	}
}
