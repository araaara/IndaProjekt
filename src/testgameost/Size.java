package testgameost;

public class Size {

	private int width;
	private int height;
	
	public Size(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}
	
	public int area(){
		return height*width;
	}
}
