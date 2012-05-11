package testgameost;

public class Weapon {

	private String name;
	private int damage;
	private int attackSpeed;
	private int type; //1 = melee, 2 = ranged, 3 = spell
	
	public Weapon(String n, int d, int s, int t) {
		name = n;
		damage = d;
		attackSpeed = s;
		type = t;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getAttackSpeed() {
		return attackSpeed;
	}
	
	public int getType() {
		return type;
	}
}