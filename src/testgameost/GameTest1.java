package testgameost;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameTest1 extends StateBasedGame{
	
	private static final int MAXFPS = 60;
	public static final int SCALE = 2;
	
	public GameTest1() {
		super("GameTest1");
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new Menu());
		addState(new World());
		addState(new Credits());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new GameTest1());
			
			container.setDisplayMode(200*SCALE,200*SCALE,false);
			container.setTargetFrameRate(MAXFPS);
			container.setShowFPS(false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
}
