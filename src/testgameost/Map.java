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
			monsters = new HashSet<Monster>();
			corpses = new HashSet<Position>();
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
				if((to.yPos()>wall.getYPos() && to.yPos()<wall.getYPos()+wall.height()) ||
						(to.yPos()+World.getPlayerHeight()>wall.getYPos() && to.yPos()+World.getPlayerHeight()<wall.getYPos()+wall.height()) ||
						(to.yPos()<=wall.getYPos() && to.yPos()+World.getPlayerHeight()>=wall.getYPos()+wall.height())){
					if((to.xPos()>wall.getXPos() && to.xPos()<wall.getXPos()+wall.width()) ||
							(to.xPos()<=wall.getXPos() && to.xPos()+World.getPlayerWidth()>=wall.getXPos()+wall.width()) ||
							(to.xPos()+World.getPlayerWidth()>wall.getXPos() && to.xPos()+World.getPlayerWidth()<wall.getXPos()+wall.width())){
						if(from.yPos()+World.getPlayerHeight()>wall.getYPos() && from.yPos()<wall.getYPos()+wall.height()){
							
							if(moveX>0){
								borderX=Math.min(borderX, wall.getXPos());
							}
							if(moveX<0){
								borderX=Math.max(borderX, wall.getXPos()+wall.width());
							}
						}
						
						if(from.xPos()+World.getPlayerWidth()>wall.getXPos() && from.xPos()<wall.getXPos()+wall.width()){
							if(moveY>0){
								borderY=Math.min(borderY, wall.getYPos());
							}
							if(moveY<0){
								borderY=Math.max(borderY, wall.getYPos()+wall.height());
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
		
		public Position checkMove(Position from, Position to, HashSet<Collidable> collidables){
			
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
			for(Collidable c : collidables){
				if((to.yPos()>c.getYPos() && to.yPos()<c.getYPos()+c.height()) ||
						(to.yPos()+World.getPlayerHeight()>c.getYPos() && to.yPos()+World.getPlayerHeight()<c.getYPos()+c.height()) ||
						(to.yPos()<=c.getYPos() && to.yPos()+World.getPlayerHeight()>=c.getYPos()+c.height())){
					if((to.xPos()>c.getXPos() && to.xPos()<c.getXPos()+c.width()) ||
							(to.xPos()<=c.getXPos() && to.xPos()+World.getPlayerWidth()>=c.getXPos()+c.width()) ||
							(to.xPos()+World.getPlayerWidth()>c.getXPos() && to.xPos()+World.getPlayerWidth()<c.getXPos()+c.width())){
						if(from.yPos()+World.getPlayerHeight()>c.getYPos() && from.yPos()<c.getYPos()+c.height()){
							
							if(moveX>0){
								borderX=Math.min(borderX, c.getXPos());
							}
							if(moveX<0){
								borderX=Math.max(borderX, c.getXPos()+c.width());
							}
						}
						
						if(from.xPos()+World.getPlayerWidth()>c.getXPos() && from.xPos()<c.getXPos()+c.width()){
							if(moveY>0){
								borderY=Math.min(borderY, c.getYPos());
							}
							if(moveY<0){
								borderY=Math.max(borderY, c.getYPos()+c.height());
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
				if((pos.yPos()>w.getYPos() && pos.yPos()<w.getYPos()+w.height()) ||
						(pos.yPos()+World.getPlayerHeight()>w.getYPos() && pos.yPos()+World.getPlayerHeight()<w.getYPos()+w.height()) ||
						(pos.yPos()<=w.getYPos() && pos.yPos()+World.getPlayerHeight()>=w.getYPos()+w.height())){
					if((pos.xPos()>w.getXPos() && pos.xPos()<w.getXPos()+w.width()) ||
							(pos.xPos()<=w.getXPos() && pos.xPos()+World.getPlayerWidth()>=w.getXPos()+w.width()) ||
							(pos.xPos()+World.getPlayerWidth()>w.getXPos() && pos.xPos()+World.getPlayerWidth()<w.getXPos()+w.width())){
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
		
		public HashSet<Collidable> getCollidables(){
			HashSet<Collidable> collidables = new HashSet<Collidable>();
			collidables.addAll(monsters);
			collidables.addAll(walls);
			collidables.add(World.player);
			return collidables;
		}
		
}
