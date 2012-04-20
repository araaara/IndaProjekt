package testgameost;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class World extends BasicGameState {
	
	private static final int ID = 2;
	
	private Image map1;
	private Image char2;
	private static final int PWIDTH = 40;
	private static final int PHEIGHT = 60;
	
	private boolean keyUp;
	private boolean keyDown;
	private boolean keyLeft;
	private boolean keyRight;
	
	private static final float SPEED = 2;
	
	private Position playerPos = new Position(150,150);
	
	private StateBasedGame game;
	
	private class Position{
    	
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
    	
    	public void offsetX(float x, GameContainer container){
    		
    		this.x = this.x + x;
    		
    		if(this.x+PWIDTH>container.getWidth()){
    			this.x = container.getWidth()-PWIDTH;
    		}
    		if(this.x<0){
    			this.x = 0;
    		}
    	}
    	public void offsetY(float y, GameContainer container){
    		
    		this.y = this.y + y;
    		
    		if(this.y+PHEIGHT>container.getHeight()){
    			this.y = container.getHeight()-PHEIGHT;
    		}
    		if(this.y<0){
    			this.y = 0;
    		}
    	}
    }

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		map1 = new Image("resources/map_1.png");
		char2 = new Image("resources/char2.png");

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawImage(map1, 0, 0);
		g.drawImage(char2, playerPos.xPos(), playerPos.yPos());
		
		

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		keyUp = container.getInput().isKeyDown(Input.KEY_UP);
    	keyDown = container.getInput().isKeyDown(Input.KEY_DOWN);
    	keyLeft = container.getInput().isKeyDown(Input.KEY_LEFT);
    	keyRight = container.getInput().isKeyDown(Input.KEY_RIGHT);
    	
    	if(keyUp){
    		playerPos.offsetY(-SPEED, container);
    	}
    	
    	if(keyDown){
    		playerPos.offsetY(SPEED, container);
    	}
    	
    	if(keyLeft){
    		playerPos.offsetX(-SPEED, container);
    	}
		
    	if(keyRight){
    		playerPos.offsetX(SPEED, container);
    	}

	}

	@Override
	public int getID() {
		return ID;
	}

}
