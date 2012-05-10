package testgameost;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends Creature{
	
	private boolean alive;
	
	public Player(Position Pos)
		throws SlickException{
		super(Pos);
		image = new Image("resources/char2.png");
		alive = true;
		movementSpeed = 4;
	}
	
	@Override
	public void die(Map map) {
		map.addCorpse(getPosition());
		alive = false;
	}
	
	public boolean alive(){
		return alive;
	}
}
