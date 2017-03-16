package gui;

import game.GameControl;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WarDisplay extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 900380817303473483L;
	
	private boolean doDisplayLog = true;
	
	private JFrame parent;
	
	private InformationPanel infoPanel;
//	private JButton upgradeButton;
	private BattleControls battleControls;
	private LogPanel logPanel;
	
	private LogManager logManager;
	
	public WarDisplay(GameControl newGameControl, LogManager newLogManager, Grid<Actor> grid, JFrame newParent)
	{
		logManager = newLogManager;
		
		parent = newParent;

	//	upgradeButton = new JButton("Upgrade");
	//	upgradeButton.addActionListener(this);
		infoPanel = new InformationPanel(newGameControl, logManager, grid);
		battleControls = new BattleControls(newGameControl, grid, infoPanel);
		logPanel = new LogPanel(logManager);

		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.gridheight = 1;
		add(infoPanel, constraints);
		
	/*	constraints.gridx = 7;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(upgradeButton, constraints);*/
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 4;
		constraints.gridheight = 3;
		constraints.weightx = 1.0;
		add(battleControls, constraints);
	
		if (doDisplayLog)
		{
			constraints.gridx = 0;
			constraints.gridy = 4;
			constraints.gridwidth = 9;
			constraints.gridheight = 3;
		//	constraints.weightx = 1.0;
			constraints.weighty = 1.0;
			add(logPanel, constraints);
		}
	}

	
	public void actionPerformed(ActionEvent arg0) 
	{
		new UpgradePanel(parent);
	}
	
}
