package testgameost;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.HashSet;

public class Player extends Creature{
	
	private int baseSwordDmg;
	private int baseBowDmg;
	private int baseMagicDmg;
	private int dmgTaken;
	private int mpCounter;
	private int mpRegen;
	private boolean alive;
	private HashSet<Equipable> equipment;
	
	public Player(Position Pos)
		throws SlickException{
		super(Pos);
		image = new Image("resources/char2.png");
		alive = true;
		movementSpeed = 4;
		equipment = new HashSet<Equipable>();
		mpRegen = 60;
	}
	
	@Override
	public void die(Map map) {
		map.addCorpse(getPosition());
		alive = false;
	}
	
	public boolean alive(){
		return alive;
	}
	
	public int gethp(){
		return maxHp + getHpBonus() - dmgTaken;
	}
	
	public int getMp(){
		return mp;
	}
	
	public int getMaxHp(){
		return maxHp + getHpBonus();
	}
	
	public int getMaxMp(){
		return maxMp;
	}
	
	public void increaseMaxHp(int hp) {
		maxHp = maxHp + hp;
		hitpoints = hitpoints + hp;
	}
	
	public void setMaxHp(int newMax){
		maxHp = newMax;
		if(hitpoints>maxHp){
			hitpoints = maxHp;
		}
	}
	
	public void setMaxMp(int newMax){
		maxMp = newMax;
		if(mp>maxMp){
			mp = maxMp;
		}
	}
	
	public void changeHp(int hp, Map map){
		dmgTaken = dmgTaken - hp;
		if(gethp()<=0){
			die(map);
		}
		if(dmgTaken<0){
			dmgTaken = 0;
		}
		if(dmgTaken>getMaxHp()){
			dmgTaken = getMaxHp();
		}
	
	/*
		hitpoints = hitpoints + hp;
		if(hitpoints<=0){
			hitpoints = 0;
		 	die(map);
		}
		if(hitpoints>maxHp){
		hitpoints = maxHp;
		}
		 */
		
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
	
	public double getHpPercentage(){
		return (new Double(gethp())/new Double(getMaxHp()));
	}
	
	public double getMpPercentage(){
		return (new Double(mp)/new Double(maxMp));
	}
	
	private int getHpBonus(){
		int hpBonus = 0;
		for(Equipable e : equipment){
			if(e!=null){
				hpBonus = hpBonus + e.getHpBonus();
			}
		}
		return hpBonus;
	}
	
	public void equip(Equipable e){
		equipment.add(e);
	}
	
	public void mpCount(){
		mpCounter--;
		if(mpCounter == 0){
			changeMp(1);
			mpCounter = mpRegen;
		}
	}
}
