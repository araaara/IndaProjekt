package testgameost;

public class Wall implements Collidable{
	private Position pos;
	private int width;
	private int height;
	
	public Wall(Position pos, int width, int height){
		this.pos = pos;
		this.width = width;
		this.height = height;
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}
	
	public float getXPos(){
		return pos.xPos();
	}
	
	public float getYPos(){
		return pos.yPos();
	}
	
}
