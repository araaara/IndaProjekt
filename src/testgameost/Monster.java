package testgameost;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Monster extends Creature{
	
	private int cooldown;



	public Monster()
			throws SlickException{
		super();
		cooldown = 0;
		imagePath = "resources/monster2.png";
		isHit = false;
		image = new Image(imagePath);
	}
	
	@Override
	public void die(Map map) {
		map.addCorpse(getPosition());
		map.removeMonster(this);
	}
	
	public int getCooldown(){
		return cooldown;
	}
	
	public void resetCooldown(){
		cooldown = 60;
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
		pos.offsetPos(x, y, map);
	}
}
