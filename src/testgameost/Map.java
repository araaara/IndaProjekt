package testgameost;
import java.util.HashSet;
import java.lang.Math;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Map{
		
		private HashSet<Exit> exits;
		private HashSet<Wall> walls;
		private HashSet<Position> corpses;
		private HashSet<Monster> monsters;
		private Image background;
		private Size size;
		
		public Map(String path)
			throws SlickException{
			background = new Image(path);
			walls = new HashSet<Wall>();
			exits = new HashSet<Exit>();
			this.size = new Size(400,300);
			monsters = new HashSet<Monster>();
			corpses = new HashSet<Position>();
		}
		
		public Map(){
			walls = new HashSet<Wall>();
			exits = new HashSet<Exit>();
			this.size = new Size(400,300);
		}
		
		public void addMonster(Monster m, Position pos){
			monsters.add(m);
			m.setPosition(pos);
		}
		
		public void addMonster(Monster m){
			monsters.add(m);
		}
		
		public void removeMonster(Monster m){
			monsters.remove(m);
		}
		
		public Image getBackground(){
			return background;
		}
		
		public void addWall(Position pos, int width, int height){
			walls.add(new Wall(pos,width,height));
		}
		
		public void addWall(Wall wall){
			walls.add(wall);
		}
		
		public void addExit(Map destMap, Position pos, int width, int height, Position destPos){
			exits.add(new Exit(destMap, pos, width, height, destPos));
		}
		
		public void addExit(Exit e){
			exits.add(e);
		}
		
		public Position checkMove(Position from, Position to){
			
			float moveX = to.xPos()-from.xPos();
			float moveY = to.yPos()-from.yPos();
			float borderX = to.xPos();
			if(moveX>0){
				borderX = to.xPos()+World.getPlayerWidth();
			}
			float borderY = to.yPos();
			if(moveY>0){
				borderY = to.yPos()+World.getPlayerHeight();
			}
			for(Wall wall : walls){
				if((to.yPos()>wall.getYPos() && to.yPos()<wall.getYPos()+wall.getHeight()) ||
						(to.yPos()+World.getPlayerHeight()>wall.getYPos() && to.yPos()+World.getPlayerHeight()<wall.getYPos()+wall.getHeight()) ||
						(to.yPos()<=wall.getYPos() && to.yPos()+World.getPlayerHeight()>=wall.getYPos()+wall.getHeight())){
					if((to.xPos()>wall.getXPos() && to.xPos()<wall.getXPos()+wall.getWidth()) ||
							(to.xPos()<=wall.getXPos() && to.xPos()+World.getPlayerWidth()>=wall.getXPos()+wall.getWidth()) ||
							(to.xPos()+World.getPlayerWidth()>wall.getXPos() && to.xPos()+World.getPlayerWidth()<wall.getXPos()+wall.getWidth())){
						if(from.yPos()+World.getPlayerHeight()>wall.getYPos() && from.yPos()<wall.getYPos()+wall.getHeight()){
							
							if(moveX>0){
								borderX=Math.min(borderX, wall.getXPos());
							}
							if(moveX<0){
								borderX=Math.max(borderX, wall.getXPos()+wall.getWidth());
							}
						}
						
						if(from.xPos()+World.getPlayerWidth()>wall.getXPos() && from.xPos()<wall.getXPos()+wall.getWidth()){
							if(moveY>0){
								borderY=Math.min(borderY, wall.getYPos());
							}
							if(moveY<0){
								borderY=Math.max(borderY, wall.getYPos()+wall.getHeight());
							}
						}
					}
				}
			}
			if(moveX>0){
				borderX = borderX-World.getPlayerWidth();
				}
			
			if(moveY>0){
				borderY = borderY-World.getPlayerHeight();
				}
			return (new Position(borderX,borderY));
		}
		
		public Exit checkExits(Position pos){
			for(Exit e : exits){
				/*
				if(pos.xPos()>=e.getXPos() && pos.xPos()<=e.getXPos()+e.getWidth() &&
						pos.yPos()>=e.getYPos() && pos.yPos()<=e.getYPos()+e.getHeight()){
					return e;
				}
				*/
				if((pos.yPos()>e.getYPos() && pos.yPos()<e.getYPos()+e.getHeight()) ||
						(pos.yPos()+World.getPlayerHeight()>e.getYPos() && pos.yPos()+World.getPlayerHeight()<e.getYPos()+e.getHeight()) ||
						(pos.yPos()<=e.getYPos() && pos.yPos()+World.getPlayerHeight()>=e.getYPos()+e.getHeight())){
					if((pos.xPos()>e.getXPos() && pos.xPos()<e.getXPos()+e.getWidth()) ||
							(pos.xPos()<=e.getXPos() && pos.xPos()+World.getPlayerWidth()>=e.getXPos()+e.getWidth()) ||
							(pos.xPos()+World.getPlayerWidth()>e.getXPos() && pos.xPos()+World.getPlayerWidth()<e.getXPos()+e.getWidth())){
						return e;
					}
				}
			}
			return null;
		}
		
		public Size getSize(){
			return size;
		}
		
		public HashSet<Wall> getWalls(){
			return walls;
		}
		
		public boolean isWalkable(Position pos){
			for(Wall w : walls){
				if((pos.yPos()>w.getYPos() && pos.yPos()<w.getYPos()+w.getHeight()) ||
						(pos.yPos()+World.getPlayerHeight()>w.getYPos() && pos.yPos()+World.getPlayerHeight()<w.getYPos()+w.getHeight()) ||
						(pos.yPos()<=w.getYPos() && pos.yPos()+World.getPlayerHeight()>=w.getYPos()+w.getHeight())){
					if((pos.xPos()>w.getXPos() && pos.xPos()<w.getXPos()+w.getWidth()) ||
							(pos.xPos()<=w.getXPos() && pos.xPos()+World.getPlayerWidth()>=w.getXPos()+w.getWidth()) ||
							(pos.xPos()+World.getPlayerWidth()>w.getXPos() && pos.xPos()+World.getPlayerWidth()<w.getXPos()+w.getWidth())){
						return false;
					}
				}
			}
			return true;
		}
		
		public boolean isWalkable(float x, float y){
			return isWalkable(new Position(x, y));
		}
		
		public void moveMonsters(Player player, Map map){
			for(Monster m : monsters){
				m.moveTowardsPlayer(player, map);
			}
		}
		
		public int width(){
			return size.width();
		}
		
		public int height(){
			return size.height();
		}
		
		public HashSet<Monster> monsters(){
			return monsters;
		}
		
		public HashSet<Position> getCorpses(){
			return corpses;
		}
		
		public void addCorpse(Position pos){
			corpses.add(pos);
		}
		
}
