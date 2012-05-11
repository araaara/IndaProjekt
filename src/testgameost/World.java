package testgameost;


import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import playernshit.Projectile;
import playernshit.Spell;


public class World extends BasicGameState {
	
	private static final int ID = 2;
	
	private Image char2;
	private Image bars;
	private Image corpse;
	
	//Animationer
	private Animation[] facing;
	private Animation currentAnim;
	private Animation[] walking;
	private Animation[] swordAttack;
	private Animation[] strike;
	
	//PLAYER DIMENSIONS
	private static int PWIDTH = 40;
	private static int PHEIGHT = 60;
	
	//Input
	private boolean keyUp;
	private boolean keyDown;
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keySquare;
	private boolean keyTakeDmg;
	private boolean keyEsc;
	private boolean toggleHitBox;
	private boolean togglePosition;
	private boolean showEnemyHp;
	private boolean keyA;
	private boolean key1;
	private boolean key2;
	private boolean key3;
	private boolean keyR; //Debug för Power Weapon/Armor
	
	//Maps
	private Map map1;
	private Map map2;
	private Map map3;
	private Map map4;
	private Map map5;
	private Map currentMap;
	

	//Player
	public static Player player;
	private Position playerPos = new Position(200,200);
	private int direction = 0;
	private int ATKTYPE = 1;
	private int swordCooldown = 0;
	
	private boolean isAttacking = false;
	
	private static int ATKSPEED = 40;
	private static int PROJECTILESPEED = 10;
	private static int ATKDMG;
	private static int HPBOOST;
	
	private Weapon currentWeapon;
	private Armor currentArmor;
	private Fireball currentSpell;
	
	private HashSet<Projectile> shots = new HashSet<Projectile>();
	
	private boolean charging = true;
	private int chargeTime = 0;
	
	private String lastDmg;
	
	
	//Shape for testing hitboxes
	private Shape hitBoxTest;
	
	//Game over counter
	private int gameOver = 60*7;
	
	
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		GameTest1.gameStarted = true;
		buildWorld(container, game);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawImage(currentMap.getBackground(), 0, 0);
		for(Position p : currentMap.getCorpses()){
			g.drawImage(corpse,p.xPos(),p.yPos());
		}
		for(Monster m : currentMap.monsters()){
			g.drawImage(m.getImage(), m.getXPos(), m.getYPos());
			if(showEnemyHp){
				g.drawString(""+m.gethp(), m.getXPos(), m.getYPos()-15);
			}
			
			if(swordCooldown>0) {
			}
	    	else {
	    		m.resetHit();
	    	}
			
			if(swordCooldown > 0 && m.getIsHit()) {
	    		g.drawString(lastDmg, m.getXPos()+20, m.getYPos()-20);
	    	}
		}
		if(player.alive()){
			g.drawAnimation(currentAnim, player.getXPos(), player.getYPos());
			if(isAttacking){
				drawStrikeAnimation(g);
			}	
		}
		else{
			g.drawString("GAME OVER", 170, 100);
			gameOver--;
		}
		if(togglePosition){
			g.drawString(player.getPosition().toString(), 10, 10);
		}
		
		if(swordCooldown>0) {
		    swordCooldown--;
		}
    	
		if(swordCooldown == 0){
			isAttacking=false;
		}
    	
    	
    	if(!shots.isEmpty()) {
    		Iterator<Projectile> it = shots.iterator();
            while(it.hasNext()){
            	Projectile shot = it.next();
            	shot.move();
            	Rectangle hitbox = shot.getHitbox();
            	g.drawImage(shot.getImage(), shot.xPos(), shot.yPos());
            	checkHit(hitbox, shot.getDamage(), g);
            	if (shot.xPos() > container.getWidth() || shot.xPos() < -30 || shot.yPos() > container.getHeight() || shot.yPos() < - 30) {
            		it.remove();
            	}
            	for(Monster m : currentMap.monsters()){
            		if (m.getIsHit()) {
                		it.remove();
                	}
            	}
            	
            }   
    	}
    	
    	if(keyA && ATKTYPE == 1 && swordCooldown == 0){ //Melee
    		attackMelee(g);
    		keyA=false;
    		isAttacking = true;
    		swordAttack[direction].restart();
    		currentAnim=swordAttack[direction];
    		swordCooldown = ATKSPEED;
    	}
    	
    	if(keyA && ATKTYPE == 2 && swordCooldown == 0){ //Range
    		
    		Image arrow = new Image("resources/arrow.png");
    		sendProjectile(arrow, PROJECTILESPEED, ATKDMG);
    		swordCooldown = ATKSPEED;
    	}
    	
    	if(keyA && ATKTYPE == 3 && swordCooldown == 0) { //Spell
    		charging = true;
    	}
    	else {
    		charging = false;
    		chargeTime = 0;
    	}
    	
    	if (charging && swordCooldown == 0) {
    		//Image chargingPic = new Image("resources/charging.png");
    		//g.drawImage(chargingPic, playerPos.xPos()+2, playerPos.yPos()+2);
    		chargeTime++;
    	}
    	
    	
    	
    	if(chargeTime == currentSpell.getCharge()) {
    		Image spellPic = new Image(currentSpell.getImagePath());
    		sendProjectile(spellPic, currentSpell.getSpeed(), currentSpell.getDamage());
    		chargeTime = 0;
    		swordCooldown = ATKSPEED;
    	}
    	
    	
    	
    	if(key1 && swordCooldown == 0) { //Switch to melee
    		//currentWeapon = new Weapon("Basic Sword", 10, 60, 1, 0);
    		//updateWeapon(currentWeapon);
    		//ATKTYPE = currentWeapon.getType();
    	}
    	
    	if(key2 && swordCooldown == 0) { //Switch to range
    		//currentWeapon = new Weapon("Basic Bow", 15, 60, 2, 0);
    		//updateWeapon(currentWeapon);
    		//ATKTYPE = currentWeapon.getType();
    	}
    	
    	if(key3 && swordCooldown == 0) { //Switch to spell
    		currentSpell = new Fireball(20, 12, 60);
    		ATKTYPE = 3;
    	}
    	/*
    	if(keyR && swordCooldown == 0) {
    		Weapon powerWeapon;
    		Armor powerArmor = new Armor("Power Armor", 100, 1000);
    		if (ATKTYPE == 1) {
    			powerWeapon = new Weapon("Power Weapon", 100, 40, 1, 100);
    			updateWeapon(powerWeapon);
    		}
    		else if (ATKTYPE == 2) {
    			powerWeapon = new Weapon("Power Weapon", 100, 40, 2, 100);
    			updateWeapon(powerWeapon);
    		}
    		else { //Fireball, eftersom det inte är ett vapen
    			currentSpell = new Fireball(120, 12, 40);
    		}
    		updateArmor(powerArmor);
    		
    		swordCooldown = ATKSPEED;
    	}
    	*/
    	
		g.drawImage(bars,0,300);
		g.setColor(Color.green);
		g.fillRect(26, 326, new Float(player.getHpPercentage()*160), 12);
		g.fillRect(26, 358, new Float(player.getMpPercentage()*160), 12);
		g.setColor(Color.white);
		//g.drawString(("" + player.getHpPercentage()*100).substring(0,3)+ "%", 80, 323);
		g.drawString("" + player.gethp()+"/"+player.getMaxHp(), 76, 323);
		g.drawString("" + player.getMp()+"/"+player.getMaxMp(), 76, 355);
		
		
		

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		keyEsc = container.getInput().isKeyDown(Input.KEY_ESCAPE);
		if(container.getInput().isKeyPressed(Input.KEY_H)){
			toggleHitBox = !toggleHitBox;
		}
		if(container.getInput().isKeyPressed(Input.KEY_J)){
			togglePosition = !togglePosition;
		}
		if(container.getInput().isKeyPressed(Input.KEY_K)){
			showEnemyHp = !showEnemyHp;
		}
		if(keyEsc){
			game.enterState(1);
		}
		if(gameOver <= 0){
			game.enterState(3, new FadeOutTransition(Color.black,60*6), new FadeInTransition(Color.black,60*6));
		}
		if(player.alive() && !isAttacking){
			keyUp = container.getInput().isKeyDown(Input.KEY_UP);
			keyDown = container.getInput().isKeyDown(Input.KEY_DOWN);
			keyLeft = container.getInput().isKeyDown(Input.KEY_LEFT);
			keyRight = container.getInput().isKeyDown(Input.KEY_RIGHT);
			keySquare = container.getInput().isKeyDown(Input.KEY_Q);
			keyTakeDmg = container.getInput().isKeyDown(Input.KEY_W);
			keyA = container.getInput().isKeyDown(Input.KEY_A);
			key1 = container.getInput().isKeyDown(Input.KEY_1);
			key2 = container.getInput().isKeyDown(Input.KEY_2);
			key3 = container.getInput().isKeyDown(Input.KEY_3);
			keyR = container.getInput().isKeyDown(Input.KEY_R);
			
			float moveX=0;
			float moveY=0;
			
			currentAnim=facing[direction];
    	
			if(keyUp){
				moveY=moveY-1;
			}
    	
			if(keyDown){
				moveY=moveY+1;
			}
    	
			if(keyLeft){
				moveX=moveX-1;
			}
		
			if(keyRight){
				moveX=moveX+1;
			}
			
			if(moveY>0 && moveX==0){
				direction = 0;
			}
			else if(moveY>0 && moveX>0){
				direction = 1;
			}
			else if(moveY==0 && moveX>0){
				direction = 2;
			}
			else if(moveY<0 && moveX>0){
				direction = 3;
			}
			else if(moveY<0 && moveX==0){
				direction = 4;
			}
			else if(moveY<0 && moveX<0){
				direction = 5;
			}
			else if(moveY==0 && moveX<0){
				direction = 6;
			}
			else if(moveY>0 && moveX<0){
				direction = 7;
			}
			
			if(moveX==0 && moveY==0){
				currentAnim=facing[direction];
			}
			else{
				currentAnim=walking[direction];
			}
    		player.move(moveX, moveY, currentMap);
        	Exit exit = currentMap.checkExits(player.getPosition());
        	if(exit!=null){
        		currentMap = exit.getDestMap();
        		player.setPosition(exit.getDestPos(player.getPosition()));
        	}
    	}
    	
    	
    	currentMap.moveMonsters(player, currentMap);
    	for(Monster m : currentMap.monsters()){
    		if(m.getDmgCirle().intersects(player.getHitbox()) && m.getCooldown()==0 && player.gethp()>0){
    			player.loseHitpoints(m.getDamage(), currentMap);
    			m.resetCooldown();
    		}
    		m.countCooldown();
    		/*
    		if(Position.distance(player.getPosition(), m.getPosition())<50 && m.getCooldown()==0 && player.gethp()>0){
        		player.loseHitpoints(m.getDamage(), currentMap);
        		m.resetCooldown();
        	}
    		m.countCooldown();
    		*/
    	}
    	
    	
    	
    	if(keySquare){
    	}
    	else{
    	}
    	
    	if(keyTakeDmg){
    		player.changeHp(-1, currentMap);
    	}

	}

	@Override
	public int getID() {
		return ID;
	}
	
	public static int getPlayerWidth(){
		return PWIDTH;
	}
	
	public static int getPlayerHeight(){
		return PHEIGHT;
	}
	
	public void setCurrentMap(Map map){
		currentMap = map;
	}
	
	public void addLink(Map map1, Map map2, Position pos, int length){
		if(map1!=null && map2!=null){
			if(pos.xPos()==0){
				map1.addExit(map2, new Position(-20-PWIDTH,pos.yPos()),20,length,new Position(map1.width()-PWIDTH,pos.yPos()));
				map2.addExit(map1, new Position(map2.width()+PWIDTH,pos.yPos()),20,length,new Position(20,pos.yPos()));
			}
			else if(pos.xPos()==map1.width()){
				map1.addExit(map2, new Position(map1.width()+PWIDTH,pos.yPos()),20,length,new Position(20,pos.yPos()));
				map2.addExit(map1, new Position(-20-PWIDTH,pos.yPos()),20,length,new Position(map2.width()-PWIDTH,pos.yPos()));
			}
			else if(pos.yPos()==0){
				map1.addExit(map2, new Position(pos.xPos(),-30-PHEIGHT),length,30,new Position(pos.xPos(), map1.height()-PHEIGHT));
				map2.addExit(map1, new Position(pos.xPos(), map1.height()+PHEIGHT),length,30,new Position(pos.xPos(),30));
			}
			else if(pos.yPos()==map1.height()){
				map1.addExit(map2, new Position(pos.xPos(), map1.height()+PHEIGHT),length,30,new Position(pos.xPos(),30));
				map2.addExit(map1, new Position(pos.xPos(),-30-PHEIGHT),length,30,new Position(pos.xPos(), map2.height()-PHEIGHT));
			}
			
		}
	}
	
	public int getDirection() {
		return direction;
	}
	
	/*
	private void updateArmor(Armor a) {
		currentArmor = a;
		ATKDMG = currentWeapon.getDamage()+currentArmor.getDamageBoost();
		HPBOOST = currentArmor.getHpBoost();
		player.setMaxHp(HPBOOST);
	}
	
	private void updateWeapon(Weapon w) {
		currentWeapon = w;
		ATKDMG = currentWeapon.getDamage()+currentArmor.getDamageBoost();
		ATKSPEED = currentWeapon.getAttackSpeed();
	}
	*/
	
	
	private void attackMelee(Graphics g) {
		Shape hitbox;
		int direction = getDirection();
		
		float x;
		float y;
		if (direction == 0) {
			x = player.getXPos()-10;
			y = player.getYPos()+PHEIGHT;
			hitbox = new Rectangle(x, y, 60, 40);
		}
		else if (direction == 1) {
			Polygon poly = new Polygon();
			poly.addPoint(player.getXPos()+(PWIDTH/2),player.getYPos()+PHEIGHT);
			poly.addPoint(player.getXPos()+PWIDTH,player.getYPos()+PHEIGHT);
			poly.addPoint(player.getXPos()+PWIDTH,player.getYPos()+(PHEIGHT/2));
			poly.addPoint(player.getXPos()+PWIDTH+40,player.getYPos()+(PHEIGHT/2));
			poly.addPoint(player.getXPos()+PWIDTH+40,player.getYPos()+PHEIGHT);
			poly.addPoint(player.getXPos()+PWIDTH,player.getYPos()+PHEIGHT+40);
			poly.addPoint(player.getXPos()+(PWIDTH/2),player.getYPos()+PHEIGHT+40);
			hitbox=poly;
		}
		else if (direction == 2) {
			x = player.getXPos()+PWIDTH;
			y = player.getYPos();
			hitbox = new Rectangle(x, y, 40, 60);
		}
		else if (direction == 3) {
			Polygon poly = new Polygon();
			poly.addPoint(player.getXPos()+(PWIDTH/2),player.getYPos());
			poly.addPoint(player.getXPos()+PWIDTH,player.getYPos());
			poly.addPoint(player.getXPos()+PWIDTH,player.getYPos()+(PHEIGHT/2));
			poly.addPoint(player.getXPos()+PWIDTH+40,player.getYPos()+(PHEIGHT/2));
			poly.addPoint(player.getXPos()+PWIDTH+40,player.getYPos());
			poly.addPoint(player.getXPos()+PWIDTH,player.getYPos()-40);
			poly.addPoint(player.getXPos()+(PWIDTH/2),player.getYPos()-40);
			hitbox=poly;
		}
		else if (direction == 4) {
			x = player.getXPos()-10;
			y = player.getYPos()-40;
			hitbox = new Rectangle(x, y, 60, 40);
		}
		else if (direction == 5) {
			Polygon poly = new Polygon();
			poly.addPoint(player.getXPos()+(PWIDTH/2),player.getYPos());
			poly.addPoint(player.getXPos(),player.getYPos());
			poly.addPoint(player.getXPos(),player.getYPos()+(PHEIGHT/2));
			poly.addPoint(player.getXPos()-40,player.getYPos()+(PHEIGHT/2));
			poly.addPoint(player.getXPos()-40,player.getYPos());
			poly.addPoint(player.getXPos(),player.getYPos()-40);
			poly.addPoint(player.getXPos()+(PWIDTH/2),player.getYPos()-40);
			hitbox=poly;
		}
		else if (direction == 6) {
			x = player.getXPos()-PWIDTH;
			y = player.getYPos();
			hitbox = new Rectangle(x, y, 40, 60);
		}
		else{
			Polygon poly = new Polygon();
			poly.addPoint(player.getXPos()+(PWIDTH/2),player.getYPos()+PHEIGHT);
			poly.addPoint(player.getXPos(),player.getYPos()+PHEIGHT);
			poly.addPoint(player.getXPos(),player.getYPos()+(PHEIGHT/2));
			poly.addPoint(player.getXPos()-40,player.getYPos()+(PHEIGHT/2));
			poly.addPoint(player.getXPos()-40,player.getYPos()+PHEIGHT);
			poly.addPoint(player.getXPos(),player.getYPos()+PHEIGHT+40);
			poly.addPoint(player.getXPos()+(PWIDTH/2),player.getYPos()+PHEIGHT+40);
			hitbox=poly;
		}
		
		hitBoxTest = hitbox;
		checkHit(hitbox, ATKDMG, g);
	}
	
	private void sendProjectile(Image image, int speed, int damage) {
		float x;
		float y;
		int direction = getDirection();
		if (direction == 0) {
			image.rotate(90f);
			x = player.getXPos()+PWIDTH-30;
			y = player.getYPos()+PHEIGHT-10;
		}
		else if (direction == 1) {
			image.rotate(45f);
			x = player.getXPos()+PWIDTH-10;
		    y = player.getYPos()+PHEIGHT-10;
		}
		else if (direction == 2) {
			x = player.getXPos()+PWIDTH;
		    y = player.getYPos()+PHEIGHT-30;
		}
		else if (direction == 3) {
			image.rotate(315f);
			x = player.getXPos()+PWIDTH-10;
		    y = player.getYPos()+10;
		}
		else if (direction == 4) {
			image.rotate(270f);
			x = player.getXPos()+PWIDTH-30;
			y = player.getYPos()+10;
		}
		else if (direction == 5) {
			image.rotate(225f);
			x = player.getXPos();
			y = player.getYPos()+10;
		}
		else if (direction == 6){
			image.rotate(180f);
			x = player.getXPos();
			y = player.getYPos()+PHEIGHT-30;
		}
		else {
			image.rotate(135f);
			x = player.getXPos()-10;
			y = player.getYPos()+PHEIGHT-10;
		}

		Projectile shot = new Projectile(x, y, image.getWidth(), image.getHeight(), speed, image, getDirection(), damage);
		shots.add(shot);
	}
	
	private void checkHit(Shape hitbox, int damage, Graphics g) {
		for(Monster m : currentMap.monsters()){
			if (m!=null){
				if (hitbox.intersects(m.getHitbox()) && !m.getIsHit()) {
					m.setHit();
					m.loseHitpoints(damage, currentMap);
					String dmg = new Integer(damage).toString();
					lastDmg = dmg;
				}
			}
		}
		
	}
	
	private void drawStrikeAnimation(Graphics g){
		if (direction == 0) {
			g.drawAnimation(strike[direction],player.getXPos(), player.getYPos()+PHEIGHT-10);
		}
		else if (direction == 1) {
			g.drawAnimation(strike[direction],player.getXPos()+PWIDTH-5, player.getYPos()+PHEIGHT-20);
		}
		else if (direction == 2) {
			g.drawAnimation(strike[direction],player.getXPos()+PWIDTH, player.getYPos());
		}
		else if (direction == 3) {
			g.drawAnimation(strike[direction],player.getXPos()+30, player.getYPos()-35);
		}
		else if (direction == 4) {
			g.drawAnimation(strike[direction],player.getXPos(), player.getYPos()-40);
		}
		else if (direction == 5) {
			g.drawAnimation(strike[direction],player.getXPos()-30, player.getYPos()-30);
		}
		else if (direction == 6) {
			g.drawAnimation(strike[direction],player.getXPos()-40, player.getYPos());
		}
		else{
			g.drawAnimation(strike[direction],player.getXPos()-25, player.getYPos()+PHEIGHT-20);
		}
		if(toggleHitBox){
			g.fill(hitBoxTest);
		}
	}
	
	public void buildWorld(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		player = new Player(playerPos);
		player.setMovementSpeed(2);
		player.setMaxHp(200);
		player.setHp(200);
		
		PWIDTH = player.width();
		PHEIGHT = player.height();
		
		toggleHitBox = false;
		togglePosition = false;
		showEnemyHp = false;
		
		char2 = new Image("resources/guy2.png");
		bars = new Image("resources/bars.png");
		
		walking = new Animation[8];
		facing = new Animation[8];
		swordAttack = new Animation[8];
		strike = new Animation[8];
		
		Image[] fDown = {char2};
		Image[] fRight = {new Image("resources/guy2_fr.png")};
		Image[] fLeft = {new Image("resources/guy2_fl.png")};
		Image[] fUp = {new Image("resources/guy2_b.png")};
		
		
		
		facing[0] = new Animation(fDown,1);
		facing[1] = new Animation(fDown,1);
		facing[2] = new Animation(fRight,1);
		facing[3] = new Animation(fUp,1);
		facing[4] = new Animation(fUp,1);
		facing[5] = new Animation(fUp,1);
		facing[6] = new Animation(fLeft,1);
		facing[7] = new Animation(fDown,1);
		
		//Walking animations
		Image[] wDown = {char2,new Image("resources/guy2_2.png"), char2,new Image("resources/guy2_3.png")};
		Image[] wRight = {new Image("resources/guy2_fr.png"), new Image("resources/guy2_fr_2.png"), 
				new Image("resources/guy2_fr.png"),new Image("resources/guy2_fr_3.png")};
		Image[] wLeft = {new Image("resources/guy2_fl.png"), new Image("resources/guy2_fl_2.png"), 
				new Image("resources/guy2_fl.png"),new Image("resources/guy2_fl_3.png")};
		Image[] wUp = {new Image("resources/guy2_b.png"), new Image("resources/guy2_b_2.png"),
				new Image("resources/guy2_b.png"), new Image("resources/guy2_b_3.png")};
		
		walking[0] = new Animation(wDown,180);
		walking[1] = new Animation(wDown,180);
		walking[2] = new Animation(wRight, 180);
		walking[3] = new Animation(wUp,180);
		walking[4] = new Animation(wUp,180);
		walking[5] = new Animation(wUp,180);
		walking[6] = new Animation(wLeft, 180);
		walking[7] = new Animation(wDown,180);
		
		//Sword attack animations
		Image[] swordAttackRight = {new Image("resources/guy2_fr_sw.png"), new Image("resources/guy2_fr_sw_2.png"),
				new Image("resources/guy2_fr_sw_3.png"), new Image("resources/guy2_fr_sw.png")};
		Image[] swordAttackLeft = {new Image("resources/guy2_fl_sw.png"), new Image("resources/guy2_fl_sw_2.png"),
				new Image("resources/guy2_fl_sw_3.png"), new Image("resources/guy2_fl_sw.png")};
		
		swordAttack[0] = new Animation(swordAttackLeft,180);
		swordAttack[1] = new Animation(swordAttackRight,180);
		swordAttack[2] = new Animation(swordAttackRight,180);
		swordAttack[3] = new Animation(swordAttackRight,180);
		swordAttack[4] = new Animation(swordAttackRight,180);
		swordAttack[5] = new Animation(swordAttackLeft,180);
		swordAttack[6] = new Animation(swordAttackLeft,180);
		swordAttack[7] = new Animation(swordAttackLeft,180);
		
		swordAttack[0].stopAt(3);
		swordAttack[1].stopAt(3);
		swordAttack[2].stopAt(3);
		swordAttack[3].stopAt(3);
		swordAttack[4].stopAt(3);
		swordAttack[5].stopAt(3);
		swordAttack[6].stopAt(3);
		swordAttack[7].stopAt(3);
		
		Image strikeRight = new Image("resources/strike.png");
		Image strikeUp = strikeRight.copy();
		Image strikeDown = strikeRight.copy();
		Image strike1Img = strikeRight.copy();
		Image strike3Img = strikeRight.copy();
		Image strike5Img = strikeRight.copy();
		Image strike7Img = strikeRight.copy();
		
		strikeUp.rotate(-90);
		strikeDown.rotate(90);
		strike1Img.rotate(45);
		strike3Img.rotate(-45);
		strike5Img.rotate(-135);
		strike7Img.rotate(135);
		
		Image[] strike0 = {strikeDown};
		Image[] strike1 = {strike1Img};
		Image[] strike2 = {strikeRight};
		Image[] strike3 = {strike3Img};
		Image[] strike4 = {strikeUp};
		Image[] strike5 = {strike5Img};
		Image[] strike6 = {strikeRight.getFlippedCopy(true, false)};
		Image[] strike7 = {strike7Img};
		
		
		
		strike[0] = new Animation(strike0, 180);
		strike[1] = new Animation(strike1, 180);
		strike[2] = new Animation(strike2, 180);
		strike[3] = new Animation(strike3, 180);
		strike[4] = new Animation(strike4, 180);
		strike[5] = new Animation(strike5, 180);
		strike[6] = new Animation(strike6, 180);
		strike[7] = new Animation(strike7, 180);
		
		
		currentAnim = facing[0];
		
		corpse = new Image("resources/lik.png");
		
		map1 = new Map("resources/map_1.png");
		map1.addWall(new Position(0,0), 25, 300);
		map1.addWall(new Position(375,0), 25, 300);
		map1.addWall(new Position(25,-100), 125, 145);
		map1.addWall(new Position(25,260), 350, 45);
		map1.addWall(new Position(260,-100), 155, 145);
		map1.addWall(new Position(84,136),70,52);
		map2 = new Map("resources/map_2.png");
		map2.addWall(new Position(-100,0), 125, 90);
		map2.addWall(new Position(375,0), 100, 100);
		map2.addWall(new Position(25,-100), 125, 145);
		map2.addWall(new Position(25,260), 125, 45);
		map2.addWall(new Position(-100,180), 125, 120);
		map2.addWall(new Position(260,-100), 155, 140);
		map2.addWall(new Position(260,260), 150, 150);
		map2.addWall(new Position(375,200),100,150);
		map3 = new Map("resources/map_3.png");
		map3.addWall(new Position(0,0),25,300);
		map3.addWall(new Position(0,0),400,45);
		map3.addWall(new Position(375,0),25,300);
		map3.addWall(new Position(260,260), 150, 150);
		map3.addWall(new Position(0,260), 150, 150);
		map4 = new Map("resources/map_4.png");
		map4.addWall(new Position(0,0),25,300);
		map4.addWall(new Position(0,0),400,45);
		map4.addWall(new Position(0,255), 400, 45);
		map4.addWall(new Position(375,0),150,90);
		map4.addWall(new Position(375,180), 150, 120);
		map5 = new Map("resources/map_5.png");
		map5.addWall(new Position(-100,0),125,100);
		map5.addWall(new Position(0,0),400,45);
		map5.addWall(new Position(0,255), 400, 45);
		map5.addWall(new Position(375,0),25,300);
		map5.addWall(new Position(-100,200), 125, 120);
		currentMap = map1;
		
		addLink(map2,map1,new Position(150,300),100);
		addLink(map2,map3,new Position(150,0),100);
		addLink(map2,map4,new Position(0,90),90);
		addLink(map2,map5,new Position(400,100),100);
		
		map3.addMonster(new Monster(), new Position(130,100));
		Monster m1 = new Monster();
		m1.setDamage(0);
		m1.setMovementSpeed(0);
		m1.setMaxHp(50);
		map5.addMonster(m1, new Position(130,130));
		
		
		
		char2 = new Image("resources/char2.png");
		bars = new Image("resources/bars.png");
		
		
		Weapon basicSword = new Weapon("Basic Sword", 10, 60, 1, 0, 0);

		currentWeapon = basicSword;
		
		currentArmor = new Armor("Basic Armor", 10, 50, 0);
		/*
		ATKDMG = currentWeapon.getDamage()+currentArmor.getDamageBoost();
		ATKSPEED = currentWeapon.getAttackSpeed();
		HPBOOST = currentArmor.getHpBoost();
		player.increaseMaxHp(HPBOOST);
		*/
		
		currentSpell = new Fireball(20, 12, 60);
		
		
		playerPos = new Position(200,200);
		direction = 0;
		ATKTYPE = 1;
		swordCooldown = 0;
		
		isAttacking = false;
		
		ATKSPEED = 40;
		PROJECTILESPEED = 10;
		
		shots = new HashSet<Projectile>();
		
		charging = true;
		chargeTime = 0;
		
		gameOver = 60*7;
	}
	
	public void leave(GameContainer container, StateBasedGame game)
	           throws SlickException{
		if(gameOver<1){
			buildWorld(container, game);
		}
	}

}
