package testgameost;

public class Exit{
	private Position pos;
	private int width;
	private int height;
	private Map destMap;
	private Position destPos;
	
	public Exit(Map destMap, Position pos, int width, int height, Position destPos){
		this.destMap = destMap;
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.destPos = destPos;
		
	}
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public float getXPos(){
		return pos.xPos();
	}
	
	public float getYPos(){
		return pos.yPos();
	}
	
	public Position getPos(){
		return pos;
	}
	
	public Map getDestMap(){
		return destMap;
	}
	
	public Position getDestPos(Position pos){
		float x = pos.xPos()-this.pos.xPos();
		float y = pos.yPos()-this.pos.yPos();
		return new Position(destPos.xPos()+x,destPos.yPos()+y);
	}
}
