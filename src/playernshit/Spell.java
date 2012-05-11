package playernshit;


public abstract class Spell { //NOT IN USE
	
	private String name;
	private int damage;
	private int speed;
	private String imagePath;
	private int charge;
	
	abstract void use();
	
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
