package gui;

import game.GameControl;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Operators.KnightBug;
import Operators.MineBug;
import Operators.NinjaBug;
import Operators.SoldierBug;
import Operators.SuperBug;
import Operators.TNTBug;
import resources.BugType;

public class TroopSelector extends Component implements MouseListener
{
	
	private static final long serialVersionUID = 1904173229093362523L;
	
	private final Color borderColor = new Color(50, 50, 20);
	private BugType type;
	int x, y;
	
	
	private GameControl gameControl;
	
	public TroopSelector(BugType newType, GameControl newGameControl, int newX, int newY)
	{
		type = newType;
		gameControl = newGameControl;
		x = newX;
		y = newY;
		
		super.addMouseListener(this);
	}
	
	public void paint(Graphics g)
	{
		paintImage(g);
		setBorder(g);
	}
	
	private void paintImage(Graphics g)
	{
		BufferedImage bugImage = getBugImage();
		Rectangle bounds = g.getClipBounds();
		int dx1 = 1, dx2 = bounds.width-1;
		int dy1 = 1, dy2 = bounds.height-1;
		int sx1 = 0, sx2 = bugImage.getWidth();
		int sy1 = 0, sy2 = bugImage.getHeight();
		
		g.drawImage(bugImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
	}
	
	private void setBorder(Graphics g)
	{
		Rectangle bounds = g.getClipBounds();
		g.setColor(borderColor);
		g.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 5, 5);
	}
	
	private BufferedImage getBugImage()
	{
		InputStream input = null;
		
		switch (type)
		{
		case MineBug:
			input = ClassLoader.getSystemResourceAsStream("minerBug.jpg");
			break;
			
		case NinjaBug:
			input = ClassLoader.getSystemResourceAsStream("ninjaBug.jpg");
			break;
			
		case KnightBug:
			input = ClassLoader.getSystemResourceAsStream("knightBug.jpg");
			break;
			
		case TNTBug:
			input = ClassLoader.getSystemResourceAsStream("TNTBug.jpg");
			break;
			
		case SuperBug:
			input = ClassLoader.getSystemResourceAsStream("supreBug.jpg");
			break;
						
		default:
			break;
			
		}
		
		try 
		{
			return ImageIO.read(input);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		gameControl = InformationPanel.getGameControl();
		
		if (gameControl == null || gameControl.isInterrupted())
		{
			return;
		}
		SoldierBug newBug;
		
		switch (type)
		{
		case KnightBug:
			newBug = new KnightBug(Location.RIGHT);
			break;
			
		case MineBug:
			newBug = new MineBug(Location.RIGHT);
			break;
			
		case NinjaBug:
			newBug = new NinjaBug(Location.RIGHT);
			break;
			
		case SuperBug:
			newBug = new SuperBug(Location.RIGHT);
			break;
			
		case TNTBug:
			newBug = new TNTBug(Location.RIGHT);
			break;
			
		default:
			newBug = null;
		
		}
		
		try
		{
			gameControl.addBug(newBug, y);
		}
		catch (Exception exception)
		{
			System.out.println("Bug was NOT added");
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}

}
