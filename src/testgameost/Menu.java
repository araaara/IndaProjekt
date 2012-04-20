package testgameost;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {
	
	private static final int ID = 1;
	private static final int WORLDID = 2;
	
	private boolean keyUp;
	private boolean keyDown;
	private boolean keySelectOption;
	
	private int keyUpCoolDown;
	private int keyDownCoolDown;
	
	private static final int COOLDOWN=10;
	
	private int selectedOption;
	private String[] options={"New Game","Credits","Controls"};
	
	private StateBasedGame game;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		selectedOption=0;
		keyUpCoolDown=0;
		keyDownCoolDown=0;

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		for(int i = 0; i < options.length;i++){
			g.setColor(Color.blue);
			if(selectedOption == i){
				g.setColor(Color.white);
			}
			g.drawString(options[i], 50, 50 + (i * 30));
		}

	}
	

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		keyUp = container.getInput().isKeyDown(Input.KEY_UP);
		keyDown = container.getInput().isKeyDown(Input.KEY_DOWN);
		keySelectOption = container.getInput().isKeyDown(Input.KEY_ENTER);
		
		if(keyUp){
			if(keyUpCoolDown==0){
				selectedOption--;
				keyUpCoolDown=COOLDOWN;
			}
		}
		else{
			keyUpCoolDown=0;
		}
		if(keyDown){
			if(keyDownCoolDown==0){
				selectedOption++;
				keyDownCoolDown=COOLDOWN;
			}
			
		}
		else{
			keyDownCoolDown=0;
		}
		if(selectedOption>options.length-1){
			selectedOption=0;
		}
		if(selectedOption<0){
			selectedOption=options.length-1;
		}
		
		coolDown();
		
		if(keySelectOption&&selectedOption==0){
			game.enterState(WORLDID);
		}

	}

	@Override
	public int getID() {
		return ID;
	}
	
	private void coolDown(){
		if(keyUpCoolDown>0){
			keyUpCoolDown--;
		}
		if(keyDownCoolDown>0){
			keyDownCoolDown--;
		}
	}

}
