package testgameost;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Monster extends Creature{
	
	private int cooldown;
	private int damage;
	private int attackSpeed;



	public Monster()
			throws SlickException{
		super();
		damage = 10;
		cooldown = 0;
		imagePath = "resources/monster2.png";
		isHit = false;
		image = new Image(imagePath);
		attackSpeed = 120;
	}
	
	@Override
	public void die(Map map) {
		map.addCorpse(getPosition());
		map.removeMonster(this);
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int newDamage){
		damage = newDamage;
	}
	public int getCooldown(){
		return cooldown;
	}
	
	public void setAttackSpeed(int newSpeed){
		attackSpeed = newSpeed;
	}
	
	public void resetCooldown(){
		cooldown = attackSpeed;
	}
	
	public void countCooldown(){
		cooldown--;
		if(cooldown<0){
			cooldown = 0;
		}
	}
	
	public void moveTowardsPlayer(Player player, Map map){
		float x=0;
		float y=0;
		if(player.getXPos()>getXPos()){
			x=1;
		}
		if(player.getXPos()<getXPos()){
			x=-1;
		}
		if(player.getYPos()>getYPos()){
			y=1;
		}
		if(player.getYPos()<getYPos()){
			y=-1;
		}
		move(x, y, map);
	}
}
