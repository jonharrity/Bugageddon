package gui;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;

import javax.swing.JPanel;

public class FightPanel extends JPanel
{
	private static final long serialVersionUID = 1403868279247309566L;

	private GridPanel gridPanel;
	
	public FightPanel(Grid<Actor> grid)
	{
		gridPanel = new GridPanel(grid);
		
		add(gridPanel);
	}
	
	public GridPanel getGridPanel()
	{
		return gridPanel;
	}
}
