package gui;

import game.GameControl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TroopPanel extends JPanel
{
	private static final long serialVersionUID = 7192731842610286867L;

	public static final int TROOP_COUNT = 5;
	private TroopSelectorPanel[] selectorPanels;
	
	public TroopPanel(GameControl gameControl)
	{
		selectorPanels = new TroopSelectorPanel[GridPanel.gridHeight];
	
		setLayout(new GridLayout(GridPanel.gridHeight, 1));
		
		for (int i=0 ; i<selectorPanels.length ; i++)
		{
			selectorPanels[i] = new TroopSelectorPanel(gameControl, i);
			add(selectorPanels[i]);
		}
		
		super.setBorder(BorderFactory.createEtchedBorder(new Color(45,45,60), new Color(20, 80, 240)));
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(180, getParent().getHeight());
	}
}
