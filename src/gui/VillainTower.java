package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class VillainTower extends AbstractTower
{
	private static final long serialVersionUID = 3205042403517401873L;
	
	@Override
	protected BufferedImage getImage() {
		InputStream input = ClassLoader.getSystemResourceAsStream("villainTower.jpg");
		
		try
		{
			return ImageIO.read(input);
		} 
		catch (IOException e) 
		{
			return null;
		}
	}
	
	public void towerDestroy()
	{
		getGameControl().villainLost();
	}
	
	
}
