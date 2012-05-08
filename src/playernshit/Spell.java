package playernshit;


public abstract class Spell {
	
	private String name;
	private int damage;
	private int speed;
	private String imagePath;
	private boolean projectile;
	private int charge;
	
	abstract void use();
	
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
