package gui;

import game.GameControl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class AbstractTower extends Component implements Tower
{
	private static final long serialVersionUID = -8486508397968438661L;
	
	private int maxHealth = 10000;
	private int health = maxHealth;
	
	private GameControl gameControl;
	
	public void setGameControl(GameControl newGameControl)
	{
		gameControl = newGameControl;
	}
	
	public GameControl getGameControl()
	{
		return gameControl;
	}
	
	public void setHealth(int newHealth)
	{
		health = newHealth;
		repaint();
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public void setMaxHealth(int newMaxHealth)
	{
		health += newMaxHealth - maxHealth;
		maxHealth = newMaxHealth;
		repaint();
	}
	
	public void paint(Graphics g)
	{
		setImage(g);
		paintHealth(g);
	}
	
	private void setImage(Graphics g)
	{
		BufferedImage image = getImage();
		Rectangle bounds = g.getClipBounds();
		
		int dx1 = 0, dx2 = bounds.width;
		int dy1 = 0, dy2 = bounds.height;
		int sx1 = 0, sx2 = image.getWidth();
		int sy1 = 0, sy2 = image.getHeight();
		g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
	}
	
	private void paintHealth(Graphics g)
	{
		Rectangle bounds = g.getClipBounds();
		
		double percentHealth = getPercentHealth();
		
		int distanceFromBottom = 5;
		int distanceFromSides = 5;
		int width = bounds.width - distanceFromSides * 2;
		int barHeight = 7;
		int x1 = distanceFromSides;
		int y1 = bounds.height - distanceFromBottom - barHeight;
		
		int barWidth = (int) (percentHealth * width);
		
		g.setColor(Color.black);
		g.drawRect(x1, y1, width, barHeight);
		
		g.setColor(Color.blue);
		g.fillRect(x1, y1+1, barWidth, barHeight-1);
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(50, 100);
	}
	
	public void takeDamage(int damage) 
	{
		setHealth(getHealth() - damage);
		repaint();
		
		if (getHealth() <= 0)
		{
			towerDestroy();
		}
	}
	
	private double getPercentHealth()
	{
		return (double) getHealth() / (double) getMaxHealth();
	}
	
	public void reset()
	{
		setHealth(getMaxHealth());
	}
	
	protected abstract BufferedImage getImage();
	public abstract void towerDestroy();
	
}
