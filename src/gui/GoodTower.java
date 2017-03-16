package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GoodTower extends AbstractTower
{
	private static final long serialVersionUID = -6356002940395499274L;
	
	public GoodTower()
	{

	}

	@Override
	protected BufferedImage getImage() {
		InputStream input = ClassLoader.getSystemResourceAsStream("GoodTower.jpg");
		
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
		getGameControl().heroLost();
	}
	
	
}
