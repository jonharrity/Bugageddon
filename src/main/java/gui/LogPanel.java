package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogPanel extends JPanel implements LogChangeListener
{
	private static final long serialVersionUID = -6650510060023724643L;
	
	private LogManager manager;
	private JTextArea logTextArea;
	private JScrollPane scrollPane;
	
	public LogPanel(LogManager newManager)
	{ 
		manager = newManager;
		manager.addListener(this);
		
		setLayout(new GridLayout(1, 1));
		
		logTextArea = new JTextArea("");
		
		scrollPane = new JScrollPane(logTextArea);
		
		add(scrollPane);
		
		addWelcomeMessage();
	}
	
	private void addWelcomeMessage()
	{
		manager.addToLog("Welcome to: Bugageddon: The Wrath of Bug !");
		manager.addToLog("In this game, your objective is to destroy the enemies tower, which is on the right (your tower is on the left).");
		manager.addToLog("To do this, you will have to train bug soldiers to attack. Bugs cost mana to train. You initially start out with");
		manager.addToLog("80 mana, and you slowly regenerate mana. To train a bug, click on its image in the row that you want to train the");
		manager.addToLog("bug. Bugs, when trained in a row, can not switch rows, and will travel down that row to the enemies tower, which it");
		manager.addToLog("will attack. If the bug encounters an enemy bug, the bugs will fight each other. If a bug is stuck behind a rock, it");
		manager.addToLog("can not move, unless it a miner bug. Miner bugs (the leftmost selection) will mine away at rocks when they are next to");
		manager.addToLog("one. This increases your mana regeneration until the miner bug either dies or the resources availible in the rock have");
		manager.addToLog("been depleted. Bugs eventually die on their own after a short amount of time. Rocks also periodically appear.\n");
		manager.addToLog("Bugs availible for training:");
		manager.addToLog("Mine bug\tCost: 40 mana");
		manager.addToLog("Ninja bug\tCost: 120 mana");
		manager.addToLog("Knight bug\tCost: 200 mana");
		manager.addToLog("TNT bug\tCost: 280 mana");
		manager.addToLog("Super bug\tCost: 400 mana\n");
		manager.addToLog("You will be facing an army of villainous creatures, who in turn, will try to destroy your tower. Do not let this happen.\n");
		manager.addToLog("Good luck general.\n\n");
		
		logTextArea.setCaretPosition(0);
	}
	
	public void receiveLogEntry(String newEntry)
	{
		logTextArea.append(newEntry);
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(getParent().getWidth(), 100);
	}

}
