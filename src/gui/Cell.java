package gui;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Operators.SoldierBug;

public class Cell extends Component
{
	private static final long serialVersionUID = -4863257463242576990L;

	private Dimension preferredSize = new Dimension(25, 25);
	
	int x, y;
	Grid<Actor> grid;
	BufferedImage displayedImage = null;
	public Cell(int newX, int newY, Grid<Actor> newGrid)
	{
		x = newX;
		y = newY;
		grid = newGrid;

	}
	
	public void update()
	{
		Actor actor = getActor();
		
		if (actor != null)
		{
			
		}
		else
		{
			displayedImage = null;
			repaint();
			return;
		}
		
		InputStream input = null;
		
		switch (actor.getClass().getName())
		{
		case "Operators.ManaRock":
			input = ClassLoader.getSystemResourceAsStream("manaRock.jpg");
			break;
		
		case "Operators.MineBug":
			input = ClassLoader.getSystemResourceAsStream("minerBug.jpg");
			break;
			
		case "Operators.NinjaBug":
			input = ClassLoader.getSystemResourceAsStream("ninjaBug.jpg");
			break;
			
		case "Operators.KnightBug":
			input = ClassLoader.getSystemResourceAsStream("knightBug.jpg");
			break;
			
		case "Operators.TNTBug":
			input = ClassLoader.getSystemResourceAsStream("TNTBug.jpg");
			break;
			
		case "Operators.SuperBug":
			input = ClassLoader.getSystemResourceAsStream("supreBug.jpg");
			break;
			
		default: 
			displayedImage = null;
			return;
			
		}
		
		try 
		{
			displayedImage = ImageIO.read(input);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		repaint();
	}
	
	public void paint(Graphics g)
	{
		
		Rectangle bounds = g.getClipBounds();
		
		if (displayedImage == null || getActor() == null)
		{
			g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);
			return;
		}
		
		int dx1=0, dx2=0;
		if (((SoldierBug) getActor()).isGood())
		{
			dx1 = 0;
			dx2 = bounds.width;
		}
		else
		{
			dx1 = bounds.width;
			dx2 = 0;
		} 
	
		int dy1 = 0, dy2 = bounds.height;
		int sx1 = 0, sx2 = displayedImage.getWidth();
		int sy1 = 0, sy2 = displayedImage.getHeight();
		g.drawImage(displayedImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
	}

	public Dimension getPreferredSize()
	{
		return preferredSize;
	}
	
	private Actor getActor()
	{
		return grid.get(new Location(y, x));
	}
}
