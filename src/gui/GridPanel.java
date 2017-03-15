package gui;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GridPanel extends JPanel
{
	private static final long serialVersionUID = 2648153338425725382L;
	
	public static final int gridHeight = 6;
	public static final int gridWidth = 20;

	private GridCellPanel[][] cells;

	public GridPanel(Grid<Actor> grid)
	{	
		setLayout(new GridLayout(gridHeight, gridWidth));
		
		cells = new GridCellPanel[gridHeight][gridWidth];
		
		for (int i=0 ; i<gridHeight ; i++)
		{
			for (int j=0 ; j<gridWidth ; j++)
			{
				cells[i][j] = new GridCellPanel(j, i, grid);
				
				add(cells[i][j]);
			}
		}
	}
	
	public void update()
	{
		for (int i=0 ; i<gridHeight ; i++)
		{
			for (int j=0 ; j<gridWidth ; j++)
			{
				cells[i][j].update();
			}
		}
	}
}
