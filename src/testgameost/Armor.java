package testgameost;

public class Armor implements Equipable{

	private String name;
	private int damageBonus;
	private int hpBonus;
	private int dmgBonusType;
	
	public Armor(String n, int d, int h, int t) {
		name = n;
		damageBonus = d;
		hpBonus = h;
		dmgBonusType = t;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDamageBonus() {
		return damageBonus;
	}
	
	public int getHpBonus() {
		return hpBonus;
	}
	
	public int getType(){
		return dmgBonusType;
	}
}
