package game;

import java.util.ArrayList;

public class BugCommand
{
	
	ArrayList<BugContainer> bugs;
	
	public BugCommand()
	{
		bugs = new ArrayList<BugContainer>();
	}
	
	public BugCommand(ArrayList<BugContainer> newBugs)
	{
		bugs = newBugs;
	}
	
	public BugCommand(BugContainer newBug)
	{
		bugs = new ArrayList<BugContainer>(1);
		bugs.add(newBug);		
	}
	
	public void addBug(BugContainer newBug)
	{
		bugs.add(newBug);
	}
	
	public ArrayList<BugContainer> getBugs()
	{
		return bugs;
	}
}
