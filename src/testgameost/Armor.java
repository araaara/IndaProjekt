package testgameost;

public class Armor {

	private String name;
	private int damageBoost;
	private int hpBoost;
	
	public Armor(String n, int d, int h) {
		name = n;
		damageBoost = d;
		hpBoost = h;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDamageBoost() {
		return damageBoost;
	}
	
	public int getHpBoost() {
		return hpBoost;
	}
}
