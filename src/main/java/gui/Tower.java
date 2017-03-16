package gui;

import game.GameControl;

public interface Tower 
{
	public void takeDamage(int damage);
	public void setMaxHealth(int maxHealth);
	public void setGameControl(GameControl gameControl);
	public GameControl getGameControl();
	public void reset();

}
