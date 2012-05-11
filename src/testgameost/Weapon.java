package testgameost;

public class Weapon implements Equipable{

	private String name;
	private int damage;
	private int attackSpeed;
	private int type; //1 = melee, 2 = ranged, 3 = spell
	private int damageBonus;
	private int hpBonus;
	
	public Weapon(String n, int d, int s, int t, int b, int p) {
		name = n;
		damage = d;
		attackSpeed = s;
		type = t;
		damageBonus = b;
		hpBonus = p;
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
	
	public int getDamageBonus(){
		return damageBonus;
	}
	
	public int getHpBonus(){
		return hpBonus;
	}
}