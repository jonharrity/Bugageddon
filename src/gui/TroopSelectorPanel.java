package gui;

import game.GameControl;

import java.awt.GridLayout;

import javax.swing.JPanel;

import resources.BugType;

public class TroopSelectorPanel extends JPanel
{
	private static final long serialVersionUID = 2470760309947590036L;
	
	public static final BugType[] types = {BugType.MineBug, BugType.NinjaBug, BugType.KnightBug, BugType.TNTBug, BugType.SuperBug};

	private TroopSelector[] selectors;
	
	public TroopSelectorPanel(GameControl gameControl, int y)
	{
		setLayout(new GridLayout(1, TroopPanel.TROOP_COUNT));
		
		selectors = new TroopSelector[TroopPanel.TROOP_COUNT];
		
		for (int i=0 ; i<selectors.length ; i++)
		{
			selectors[i] = new TroopSelector(types[i], gameControl, i, y);
			add(selectors[i]);
		}
	}
}
