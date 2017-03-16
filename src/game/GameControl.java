package game;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

import Operators.*;
import gui.GridPanel;
import gui.InformationPanel;
import gui.LogManager;
import gui.Tower;
/*
 * TODO
 * 
 * 
 */
public class GameControl extends Thread
{
	private Grid<Actor> grid;
	private ArrayList<Actor> actors;
	private ArrayList<SoldierBug> villains;
	private LogManager log;
	private GridPanel gridPanel;
	private InformationPanel informationPanel;

	private boolean isPaused = false;

	private final int tickPause = 200;

	private final int ticksBeforeAddingRocks = 50;
	private int ticksUntilAddingRock;
	
	private final int initialMana = 80;
	private final int[] manaCosts = new int[] {40, 120, 200, 280, 400};
	private final int manaIncreasePerTick = 1;
	private int mana;
	
	private Tower goodTower;
	private Tower villainTower;
	
	
	public GameControl(Grid<Actor> newGrid, LogManager newLog, GridPanel newGridPanel, InformationPanel newInformationPanel, Tower newGoodTower, Tower newVillainTower)
	{
		grid = newGrid;
		actors = new ArrayList<Actor>();
		log = newLog;
		gridPanel = newGridPanel;
		informationPanel = newInformationPanel;
		goodTower = newGoodTower;
		villainTower = newVillainTower;
		
		addInitialRocks();
	}

	public Grid<Actor> getGrid()
	{
		return grid;
	}

	public void pauseGame()
	{
		isPaused = ! isPaused;
	}

	public void addBug(SoldierBug newBug, int y)
	{
		Location location = new Location(y, 0);
		
		if (! super.isAlive() || grid.get(location) != null)
		{
			return;
		}
		
		if (getManaCost(newBug) > mana)
		{
			return;
		}
		else
		{
			mana -= getManaCost(newBug);
		}

		actors.add(newBug);
		newBug.setGameControl(this);

		if (grid.getOccupiedLocations().contains(location))
		{
			return;
		}

		newBug.putSelfInGrid(grid, location);
		updateVisuals();
	}


	public void run()
	{
		log.addToLog("Game is starting!");

		mana = initialMana;
		ticksUntilAddingRock = ticksBeforeAddingRocks;
		
		ArtificialIntelligenceInterface ai = new VillainAI(this);
		villains = new ArrayList<SoldierBug>();
		
		long nextTickTime = System.currentTimeMillis();

		while (true)
		{
			nextTickTime += tickPause;
			if (isPaused)
			{
				do
				{
					try 
					{
						Thread.sleep(25L);
					} 
					catch (InterruptedException e) 
					{
						reset(); 

						return;
					}
				} while (isPaused);
			}
			
			informationPanel.setMana(mana);

			Actor actor;
			for (int i=0 ; i<actors.size() ; i++)
			{
				actor = actors.get(i);
				
				if (actor instanceof SoldierBug)
				{
					SoldierBug bug = (SoldierBug) actor;

					try
					{
						actor.act();

						if (bug.isDead())
						{
							actors.remove(actor);
						}
					}
					catch (Exception e)
					{
				//		e.printStackTrace();
					}
				}

			}
			for (int i=0 ; i<villains.size() ; i++)
			{
				SoldierBug villain = villains.get(i);
				if (villain.isDead())
				{
					villain.removeSelfFromGrid();
					villains.remove(i);
				}
				else
				{
					try
					{
						villain.act();
					}
					catch (Exception e)
					{
						
					}
				}
			}			
			ArrayList<BugContainer> newVillains = ai.getCommands().getBugs();
			BugContainer newBugContainer;
			SoldierBug newBug;
			int newRow;
			Location newLocation;
			for (int i=0 ; i<newVillains.size() ; i++)
			{
				newBugContainer = newVillains.get(i);
				newBug = newBugContainer.getBug();
				newRow = newBugContainer.getRow();
				newLocation = new Location(newRow, GridPanel.gridWidth-1);
					
				if (grid.get(newLocation) == null)
				{
					newBug.putSelfInGrid(grid, newLocation);
					newBug.setGameControl(this);
					villains.add(newBug);
				}
				else
				{
				}
			}
			
			ticksUntilAddingRock--;
			if (ticksUntilAddingRock == 0)
			{
				addMidGameRocks();
				ticksUntilAddingRock = ticksBeforeAddingRocks;
			}
			

			updateVisuals();

			try
			{
				Thread.sleep(nextTickTime - System.currentTimeMillis());
			}
			catch (IllegalArgumentException e)
			{
				
			}
			catch (InterruptedException e)
			{
				reset();

				return;
			}
			
			mana += manaIncreasePerTick;
		}

	}
	
	public void setGoodTower(Tower tower)
	{
		goodTower = tower;
	}
	
	public void setVillainTower(Tower tower)
	{
		villainTower = tower;
	}
	
	public void damageGoodTower(int damage)
	{
		goodTower.takeDamage(damage);
	}
	
	public void damageVillainTower(int damge)
	{
		villainTower.takeDamage(damge);
	}
	
	public void heroLost()
	{
		log.addToLog("You lost! D:\n");
		informationPanel.endGame();
		resetTowers();
	}
	
	public void villainLost()
	{
		log.addToLog("You won! :D\n");
		informationPanel.endGame();
		resetTowers();
	}
	
	public void addManaFromRock(int additionalMana)
	{
		mana += additionalMana;
	}
	
	private void addInitialRocks()
	{
		addRock(new Location(2, 3));
		addRock(new Location(2, 18));
		
		addRock(new Location(5, 5));
		addRock(new Location(5, 8));
	}
	
	private void addMidGameRocks()
	{
		addRock(getRandomValidLocation());
	}
	
	private void addRock(Location loc)
	{
		ManaRock rock = new ManaRock(0);
		rock.setGameControl(this);
		rock.putSelfInGrid(grid, loc);
		actors.add(rock);
	}
	
	private Location getRandomValidLocation()
	{
		int x, y;
		x = (int) (Math.random() * (GridPanel.gridWidth-2)) + 1;
		y = (int) (Math.random() * GridPanel.gridHeight);
		
		return new Location(y, x);
	}
	
	private int getManaCost(SoldierBug bug)
	{
		if (bug instanceof MineBug)
		{
			return manaCosts[0];
		}
		else if (bug instanceof NinjaBug)
		{
			return manaCosts[1];
		}
		else if (bug instanceof KnightBug)
		{
			return manaCosts[2];
		}
		else if (bug instanceof TNTBug)
		{
			return manaCosts[3];
		}
		else if (bug instanceof SuperBug)
		{
			return manaCosts[4];
		}
		else
		{
			return 0;
		}
	}

	private void reset()
	{
		for (Location loc : grid.getOccupiedLocations())
		{
			grid.get(loc).removeSelfFromGrid();
		}
		
		resetTowers();
		updateVisuals();
	}
	
	private void resetTowers()
	{
		goodTower.reset();
		villainTower.reset();
	}

	private void updateVisuals()
	{
		gridPanel.update();
	}

}
