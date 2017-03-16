package game;

import gui.GridPanel;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

import Operators.ManaRock;
import Operators.MineBug;
import Operators.SoldierBug;

public class VillainAI implements ArtificialIntelligenceInterface
{
	
	private int ticksBetweenAddingBugs = 70;
	private final int ticksBeforeIncreasingDifficulty = 70;
	private int ticksLeftToAddBug = ticksBetweenAddingBugs;
	private int ticksLeftToIncreaseDifficulty = ticksBeforeIncreasingDifficulty;
	
	private GameControl gameControl;
	
	public VillainAI(GameControl newGameControl)
	{
		gameControl = newGameControl;
	}
	
	@Override
	public BugCommand getCommands()
	{
		ticksLeftToAddBug--;
		ticksLeftToIncreaseDifficulty--;
		
		if (ticksLeftToIncreaseDifficulty == 0)
		{
			ticksBetweenAddingBugs--;
			ticksLeftToIncreaseDifficulty = ticksBeforeIncreasingDifficulty;
		}
		
		if (ticksLeftToAddBug != 0)
		{
			return new BugCommand();
		}
		
		ticksLeftToAddBug = ticksBetweenAddingBugs;
		
		int newBugCount = getEnemyBugCount();
		if (newBugCount > GridPanel.gridHeight)
		{
			newBugCount = GridPanel.gridHeight;
		}
		
		ArrayList<Integer> rows = getRows(newBugCount);
				
		
		return getNewBugCommand(rows);
	}
	
	private BugCommand getNewBugCommand(ArrayList<Integer> rows)
	{
		BugCommand newCommand = new BugCommand();
		
		for (int i=0 ; i<rows.size() ; i++)
		{
			SoldierBug newBug = getNewBug();
			newCommand.addBug(new BugContainer(newBug, rows.get(i)));
		}
		
		return newCommand;
	}
	
	private SoldierBug getNewBug()
	{
		return new MineBug(Location.LEFT);
	}
	
	private ArrayList<Integer> getRows(int bugs)
	{
		int[] options = new int[GridPanel.gridHeight];
		
		for (int i=0 ; i<options.length ; i++)
		{
			options[i] = i;
		}
		
		int rowToSwap, swapData;
		
		for (int i=0 ; i<options.length ; i++)
		{
			rowToSwap = (int) (Math.random() * GridPanel.gridHeight);
			swapData = options[i];
			options[i] = options[rowToSwap];
			options[rowToSwap] = swapData;
		}
		
		ArrayList<Integer> rows = new ArrayList<Integer>(bugs);
		
		for (int i=0 ; i<bugs ; i++)
		{
			rows.add(new Integer(options[i]));
		}
		
		return rows;
	}
	
	private int getEnemyBugCount()
	{
		Grid<Actor> grid = gameControl.getGrid();
		ArrayList<Location> locations = gameControl.getGrid().getOccupiedLocations();
		
		Actor actor;
		int enemyBugCount = 0;
		
		for (int i=0 ; i<locations.size() ; i++)
		{
			actor = grid.get(locations.get(i));
			if (actor instanceof SoldierBug && !(actor instanceof ManaRock) && ((SoldierBug) actor).isGood())
			{
				enemyBugCount++;
			}
		}
		
		return enemyBugCount;
	}
	
	

}
