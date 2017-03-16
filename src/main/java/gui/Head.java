package gui;

import java.awt.Dimension;

import game.GameControl;
import info.gridworld.actor.Actor;	
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;

import javax.swing.JFrame;

public class Head 
{
	private WarDisplay warDisplay;
	Grid<Actor> grid;
	GameControl gameControl;
	LogManager logManager;

	public Head()
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Bugageddon: The Wrath of Bug");
		frame.setMinimumSize(new Dimension(1128, 680));
		
		logManager = new LogManager();
		
		grid = new BoundedGrid<Actor>(GridPanel.gridHeight, GridPanel.gridWidth);
		
		
		//gameControl = new GameControl(grid, logManager);
		
 		warDisplay = new WarDisplay(gameControl, logManager, grid, frame);
		frame.add(warDisplay);

		frame.pack();
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new Head();
			}
		});
	}
}
