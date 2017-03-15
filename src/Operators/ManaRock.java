package Operators;
import info.gridworld.actor.Actor;
import info.gridworld.grid.*;

public class ManaRock extends SoldierBug
{
	private int manaAvailible = 90;
	private int manaDepletionRate = 3;
	
	public ManaRock(int direction) 
	{
		super(direction);
	}
	
	public boolean isDead()
	{
		return manaAvailible <= 0;
	}
	
	
	public void act()
	{		
		if (gameControl == null)
		{
			return;
		}
		
		if (hasMineBug())
		{
			gameControl.addManaFromRock(manaDepletionRate);
			manaAvailible -= manaDepletionRate;
		}
		
		if (hasVillainBug())
		{
			manaAvailible -= manaDepletionRate;
		}
		
		if (isDead())
		{
			removeSelfFromGrid();
		}
	
	}

	private boolean hasMineBug()
	{
		Location leftLocation = getLocation().getAdjacentLocation(Location.LEFT);
		
		if (getGrid().get(leftLocation) instanceof MineBug)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean hasVillainBug()
	{
		Location rightLocation = getLocation().getAdjacentLocation(Location.RIGHT);
		Actor actorToRight = getGrid().get(rightLocation);
		
		return actorToRight instanceof SoldierBug && ! (actorToRight instanceof ManaRock);
	}
		
	
	
}
