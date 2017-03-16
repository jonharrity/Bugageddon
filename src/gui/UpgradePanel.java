package gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UpgradePanel extends JDialog
{
	private static final long serialVersionUID = 2345083589230860996L;

	public UpgradePanel(JFrame parent)
	{
		super(parent, "Upgrades", true);
		JPanel container = new JPanel();
		
		JButton button = new JButton("a");
		
		container.add(button);
		
		add(container);
		
		//super.setUndecorated(true);
		
		
		super.pack();
		setVisible(true);
	}
}
