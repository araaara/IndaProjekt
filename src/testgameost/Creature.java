package testgameost;
import java.lang.Double;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public abstract class Creature {
	
	protected static Size size;
	protected Position pos;
	protected int mp;
	protected int maxHp;
	protected int maxMp;
	protected Image image;
	public int hitpoints;
	protected String imagePath;
	protected boolean isHit;
	private float movementSpeed;
	
	public Creature(Position pos){
		this.pos = pos;
		size=new Size(40,60);
		mp=100;
		maxHp=200;
		maxMp=100;
		hitpoints = 200;
		movementSpeed = 1;
	}
	
	public Creature(){
		size=new Size(40,60);
		mp=100;
		maxHp=200;
		maxMp=100;
		pos = new Position(130,100);
		hitpoints = 200;
		movementSpeed = 1;
	}
	
	public int gethp(){
		return hitpoints;
	}
	
	public int getMp(){
		return mp;
	}
	
	public int getMaxHp(){
		return maxHp;
	}
	
	public int getMaxMp(){
		return maxMp;
	}
	
	public void changeHp(int hp, Map map){
		hitpoints = hitpoints + hp;
		if(hitpoints<=0){
			hitpoints = 0;
			die(map);
		}
		if(hitpoints>maxHp){
			hitpoints = maxHp;
		}
	}
	
	public void changeMp(int mp){
		this.mp = this.mp + mp;
		if(this.mp<=0){
			this.mp = 0;
		}
		if(this.mp>maxMp){
			this.mp = maxMp;
		}
	}
	
	public static int width(){
		return size.width();
	}
	public static int height(){
		return size.height();
	}
	
	public double getHpPercentage(){
		return (new Double(hitpoints)/new Double(maxHp));
	}
	
	public double getMpPercentage(){
		return (new Double(hitpoints)/new Double(maxMp));
	}
	
	public Position getPosition(){
		return pos;
	}
	
	public float getXPos(){
		return pos.xPos();
	}
	
	public float getYPos(){
		return pos.yPos();
	}
	
	public void setPosition(Position pos){
		this.pos = pos;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void move(float x, float y, Map map){
		if(x==0 || y==0){
			pos.offsetPos((x*movementSpeed), (y*movementSpeed), map);
		}
		else{
			float moveX = (float)Math.cos(Math.atan(Math.abs(x)/Math.abs(y)))*movementSpeed;
			float moveY = (float)Math.sin(Math.atan(Math.abs(x)/Math.abs(y)))*movementSpeed;
			pos.offsetPos(x*moveX, y*moveY, map);
		}
	}
	
	public void setMovementSpeed(float newSpeed){
		movementSpeed = newSpeed;
	}


	
	public Rectangle getHitbox() {
		return new Rectangle(pos.xPos(), pos.yPos(), width(), height());
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void loseHitpoints(int damage, Map map) {
		changeHp((-damage),map);
	}
	
	public boolean getIsHit() {
		return isHit;
	}
	
	public void setHit() {
		isHit = true;
	}
	
	public void resetHit() {
		isHit = false;
	}
	
	public void die(Map map) {
		map.addCorpse(getPosition());
	}

}
