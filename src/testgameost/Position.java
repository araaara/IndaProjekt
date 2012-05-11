package testgameost;

import org.newdawn.slick.GameContainer;
import java.lang.Math;
import java.util.HashSet;

public class Position{
	
	private float x;
	private float y;
	
	public Position(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float xPos(){
		return x;
	}
	
	public float yPos(){
		return y;
	}
	
	public void offsetPos(float x, float y, Map map){
		Position result = map.checkMove(new Position(this.x,this.y), new Position(this.x+x,this.y+y));
		this.x = result.xPos();
		this.y = result.yPos();
	}
	
	public void offsetPos(float x, float y, Map map, Creature creature){
		HashSet<Collidable> collidables = map.getCollidables();
		collidables.remove(creature);
		Position result = map.checkMove(new Position(this.x,this.y), new Position(this.x+x,this.y+y), collidables);
		this.x = result.xPos();
		this.y = result.yPos();
	}
	
	public String toString(){
		return "" + this.x + ", " + this.y;
	}
	
	public static double distance(Position pos1, Position pos2){
		float a = (pos1.xPos()-pos2.xPos());
		float b = (pos1.yPos()-pos2.yPos());
		return (Math.sqrt((a*a)+(b*b)));
	}
}
