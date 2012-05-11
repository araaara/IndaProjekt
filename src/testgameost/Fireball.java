package testgameost;

public class Fireball {
	
	private String name;
	private int damage;
	private int speed;
	private String imagePath;
	private int charge;
	
	public Fireball(int d, int s, int c) {
		name = "Fireball";
		damage = d;
		speed = s;
		imagePath = "resources/fireball.png";
		charge = c;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public int getCharge() {
		return charge;
	}

}

