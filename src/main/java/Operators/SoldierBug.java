package Operators;

import game.GameControl;
import gui.GridPanel;
import info.gridworld.actor.*;
import info.gridworld.grid.*;


public abstract class SoldierBug extends Bug{

	private int weapon,health = 100, lifeCount = 55;
	private double armor;
	private boolean detonate,isGood,isDead;
	
	protected GameControl gameControl;

	SoldierBug(int direction){
		if (direction == Location.RIGHT){
			setDirection(Location.RIGHT);
			isGood = true;
		}
		else{
			this.setDirection(Location.LEFT);
			isGood = false;
		}
	}

	public void act(){
		if (canMove()){
			moveTo(getLocationInFront());
		}
		else if (isGood && getLocation().getCol() == (GridPanel.gridWidth - 2))
		{
			gameControl.damageVillainTower(weapon);
		}
		else if (!isGood && getLocation().getCol() == 1)
		{
			gameControl.damageGoodTower(weapon);
		}
		else if(isGood && thingInFront(getLocation()) instanceof SoldierBug && !((SoldierBug) thingInFront(getLocation())).isGood()){
			calculateDamage(this,(SoldierBug) thingInFront(this.getLocation()));
		}
		
		lifeCount--;
		
		if(isDead(0))
			die();
	}
	
	public void setGameControl(GameControl newGameControl)
	{
		gameControl = newGameControl;
	}
	
	@Override
	public boolean canMove()
	{
		if (getGrid().get(getLocationInFront()) == null)
		{
			if (isGood)
			{
				if (getLocationInFront().getCol() == GridPanel.gridWidth-1)
				{
					return false;
				}
				return true;
			}
			else
			{
				if (getLocationInFront().getCol() == 0)
				{
					return false;
				}
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	public int getWeapon() {
		return weapon;
	}

	public void setWeapon(int weapon) {
		this.weapon = weapon;
	}

	public double getArmor() {
		return armor;
	}

	public void setArmor(double armor) {
		this.armor = armor;
	}

	public boolean detonates() {
		return detonate;
	}

	public void setDetonate(boolean detonate) {
		this.detonate = detonate;
	}

	public boolean isGood(){
		return (this.isGood ? true:false);
	}

	public boolean isDead(){
		return this.isDead;
	}

	public void die(){
		isDead = true;
		removeSelfFromGrid();
	}
	
	public void fight(SoldierBug hero,SoldierBug villain){
		
	}

	public int mineRock(){
		int num = ((Math.random() >= .9) ? 3:1);
		//mana counter += num;
		return num;
	}

	public Actor thingInFront(Location loc){
		try
		{
			if (getGrid().get(new Location(loc.getRow(),loc.getCol()+(isGood ? 1:-1))) == null)
				return null;
			return getGrid().get(new Location(loc.getRow(),loc.getCol()+(isGood ? 1:-1)));
		}
		catch (NullPointerException | IllegalArgumentException e)
		{
			return null;
		}
	}
	
	public Actor getActorInFront()
	{
		return getGrid().get(getLocationInFront());
	}
	
	public Location getLocationInFront()
	{
		Location loc = getLocation();
		int direction = isGood ? 1 : -1;
		return new Location(loc.getRow(), loc.getCol() + direction);
	}

	public boolean isDead(int damage){
		if (this.health-damage < 1 || lifeCount < 1){
			return true;
		}
		return false;
	}

	public void calculateDamage(SoldierBug hero,SoldierBug villain){
		int damageToVillain = 0,damageToHero = 0;
		int heroAttack = hero.getWeapon(),villainAttack = villain.getWeapon();
		double heroResistance = hero.getArmor(),villainResistance = villain.getArmor();

		if (hero instanceof NinjaBug){
			damageToVillain += (int) (heroAttack*villainResistance);
			villain.health -= damageToVillain;
			if (villain.isDead(damageToVillain)){
				return;
			}	
		}
		if (villain instanceof NinjaBug){
			damageToHero += (int) (villainAttack*heroResistance);
			hero.health -= damageToHero;
			if (isDead(damageToHero)){
				return;
			}
		}

		

		if (hero instanceof MineBug){
			damageToVillain += (int) (heroAttack*villainResistance);
			villain.health -= damageToVillain;
			if (villain.isDead(damageToVillain)){
				return;
			}
		}
		if (villain instanceof MineBug){
			damageToHero += (int) (villainAttack*heroResistance);
			hero.health -= damageToHero;
			if (isDead(damageToHero)){
				return;
			}
		}

		

		if (hero instanceof KnightBug || hero instanceof NinjaBug){
			damageToVillain += (int) (heroAttack*villainResistance);
			villain.health -= damageToVillain;
			if (villain.isDead(damageToVillain)){
				return;
			}
		}
		if (villain instanceof KnightBug || villain instanceof NinjaBug){
			damageToHero += (int) (villainAttack*heroResistance);
			hero.health -= damageToHero;
			if (isDead(damageToHero)){
				return;
			}
		}

		if (hero instanceof TNTBug)
		{
			villain.lifeCount -= (int) (Math.random() * 3 + 5);
			if (villain.isDead())
			{
				return;
			}
		}
		
		if (villain instanceof TNTBug)
		{
			hero.lifeCount -= (int) (Math.random() * 3 + 5);
			if (hero.isDead())
			{
				return;
			}
		}

		if (hero instanceof SuperBug){
			damageToVillain += (int) (heroAttack*villainResistance);
			villain.health -= damageToVillain;
			if (villain.isDead(damageToVillain)){
				return;
			}
		}
		if (villain instanceof SuperBug){
			damageToHero += (int) (villainAttack*heroResistance);
			hero.health -= damageToHero;
			if (isDead(damageToHero)){
				return;
			}
		}
	}
	
	public void removeSelfFromGrid()
	{
		getGrid().remove(getLocation());
	}
}
