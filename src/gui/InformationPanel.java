package gui;

import game.GameControl;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformationPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -5187449302850290709L;

	private static GameControl gameControl;
	private LogManager logManager;
	private Grid<Actor> grid;
	private GridPanel gridPanel;
	
	private Tower goodTower;
	private Tower villainTower;
	
	private JLabel manaLabel;
//	private JLabel goldLabel;
//	private JButton optionsButton;
	private JButton startButton;
	private JButton pauseButton;
	
	private boolean isRunning = false;
	private boolean isPaused = false;
	
	public InformationPanel(GameControl newGameControl, LogManager newLogManager, Grid<Actor> newGrid)
	{
		gameControl = newGameControl;
		logManager = newLogManager;
		grid = newGrid;
		
		manaLabel = new JLabel("Mana: ");
	//	goldLabel = new JLabel("Gold: ");
	//	optionsButton = new JButton("Options");
		startButton = new JButton("Start");
		startButton.setActionCommand("startButton");
		startButton.addActionListener(this);
		pauseButton = new JButton("Pause");
		pauseButton.setEnabled(false);
		pauseButton.setActionCommand("pauseButton");
		pauseButton.addActionListener(this);
		
		
		add(manaLabel);
		//add(goldLabel);
//		add(optionsButton);
		add(startButton);
		add(pauseButton);
	}
	
	public void setGridPanel(GridPanel newGridPanel)
	{
		gridPanel = newGridPanel;
	}
	
	public void setMana(int newMana)
	{
		manaLabel.setText("Mana: " + newMana);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
		case "startButton":
			if (isRunning)//Clicked stop button
			{
				isRunning = false;
				gameControl.interrupt();				 
				pauseButton.setEnabled(false);
				startButton.setText("Start");
			}
			else//clicked start button
			{
				isRunning = true;
				gameControl = new GameControl(grid, logManager, gridPanel, this, goodTower, villainTower);
				gameControl.start();
				pauseButton.setEnabled(true);
				startButton.setText("Stop");
				
				goodTower.setGameControl(gameControl);
				villainTower.setGameControl(gameControl);
			}
			break;

		case "pauseButton":
			gameControl.pauseGame();
			
			if (isPaused)
			{
				pauseButton.setText("Pause");
			}
			else
			{
				pauseButton.setText("Resume");
			}
			
			isPaused = ! isPaused;
			
			break;
			
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
	
	public static GameControl getGameControl()
	{
		return gameControl;
	}
	
	public void endGame()
	{
		isRunning = false;
		gameControl.interrupt();				 
		pauseButton.setEnabled(false);
		startButton.setText("Start");
	}
}
