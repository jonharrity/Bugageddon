package gui;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GridCellPanel extends JPanel
{
	private static final long serialVersionUID = 6915834890970706399L;
	
	private Cell cell;

	public GridCellPanel(int x, int y, Grid<Actor> grid)
	{
		cell = new Cell(x, y, grid);
		add(cell);
		
		setBorder();
	}
	
	public void update()
	{
		cell.update();
	}
	
	
	private void setBorder()
	{
		setBorder(BorderFactory.createLineBorder(new Color(0, 0, 250)));
	}
}
