package testgameost;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

public class Credits extends BasicGameState {
	
	private static final int ID = 3;
	private static final int MENUID = 1;
	
	private StateBasedGame game;
	private float yPos;
	private String[] rollingCredits ={"GAME AND GRAPHICS MADE BY", "VIKTOR", "SIMON", "N ALL DAT"};
	private boolean keyEsc;
	private boolean rollingDone = false;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		yPos = 400;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		if(keyEsc) {
			game.enterState(MENUID);
		}
		
		if (!rollingDone) {
		g.setColor(Color.white);
		g.drawString(rollingCredits[0], 80, yPos);
		g.drawString(rollingCredits[1], 160, yPos+80);
		g.drawString(rollingCredits[2], 164, yPos+160);
		g.drawString(rollingCredits[3], 146, yPos+240);
		yPos--;
		}
		
		if (yPos == -260) {
			rollingDone = true;
		}
		
		if (rollingDone) {
			GameTest1.gameStarted=false;
			game.enterState(MENUID);
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		keyEsc = container.getInput().isKeyDown(Input.KEY_ESCAPE);

	}

	@Override
	public int getID() {
		return ID;
	}
	
	

}
