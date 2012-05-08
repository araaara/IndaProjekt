package playernshit;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Projectile {
	
		 private float x;
	     private float y;
	     private Rectangle size; 
	     private int speed;
	     private Image image;
	     private int direction;
	     private int damage;
	     private int width;
	     private int height;
	     
	     
	     public Projectile(float x, float y, int w, int h, int s, Image i, int di, int da) {
	    	 this.x = x;
	    	 this.y = y;
	    	 width = w;
	    	 height = h;
	    	 speed = s;
	    	 image = i;
	    	 direction = di;
	    	 damage = da;
	     }
	     
	     	public float xPos(){
	    		return x;
	    	}
	    	
	    	public float yPos(){
	    		return y;
	    	}
	    	
	    	public void move() {
	    		if (direction == 0) {
	    			y = y + speed;
	    		}
	    		else if (direction == 1) {
	    			x = x + speed;
	    		}
	    		else if (direction == 2) {
	    			y = y - speed;
	    		}
	    		else {
	    			x = x - speed;
	    		}
	    	}
	    	
	    	public Image getImage() {
	    		return image;
	    	}
	    	
	    	public Rectangle getHitbox() {
	    		size = new Rectangle(x, y, width, height);
	    		return size;
	    	}
	    	
	    	public int getDamage() {
	    		return damage;
	    	}
	    	
	    }