package gui;

import game.GameControl;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class BattleControls extends JPanel
{
	private static final long serialVersionUID = 6206514533545331677L;

	TroopPanel troopPanel;
	GoodTower goodTower;
	FightPanel fightPanel;
	VillainTower villainTower;
	
	public BattleControls(GameControl gameControl, Grid<Actor> grud, InformationPanel infoPanel)
	{
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridwidth = 1;
//		constraints.weightx = 1.0;
		add(troopPanel = new TroopPanel(gameControl), constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 1;
		add(goodTower = new GoodTower(), constraints);
		
		constraints.gridx = 2;
		constraints.gridwidth = 2;
//		constraints.weightx = 1.0;
		add(fightPanel = new FightPanel(grud), constraints);
		
		constraints.gridx = 4;
		constraints.gridwidth = 1;
		add(villainTower = new VillainTower(), constraints);
		
		infoPanel.setGoodTower(goodTower);
		infoPanel.setVillainTower(villainTower);
		infoPanel.setGridPanel(getGridPanel());
	
	}
	
	public GridPanel getGridPanel()
	{
		return fightPanel.getGridPanel();
	}

}
