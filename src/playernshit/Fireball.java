package playernshit;


public class Fireball extends Spell{
	
	private String name;
	private int damage;
	private int speed;
	private String imagePath;
	private boolean projectile;
	private int charge;
	
	public Fireball() {
		name = "Fireball";
		damage = 14;
		speed = 12;
		imagePath = "resources/fireball.png";
		projectile = true;
		charge = 60;
	}

	public void use() {
		//NO USE 
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isProjectile() {
		return projectile;
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

