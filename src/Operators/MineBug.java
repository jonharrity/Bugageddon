package Operators;

public class MineBug extends SoldierBug{

	public boolean mines;
	
	public MineBug(int direction){
		super(direction);
		setWeapon(15);
		setArmor(.9);
	}

	public void fight(SoldierBug hero,SoldierBug villain) {
		
	}
	
}
