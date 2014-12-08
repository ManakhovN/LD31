package lD31;

import java.net.URL;

public class Spider extends AnimatedSprite {
	Sprite shadow;
	long lastTime = System.currentTimeMillis();
	long delta=5000;
	boolean falling = true;
	
	public Spider(URL path, URL shadow, int frameWidth, int frameHeight) {
		super(path, frameWidth, frameHeight);
		this.setDefaultBoxCollider();
		this.shadow = new Sprite(shadow);
		this.y = -80;
	}

	public void update(Screen screen)
	{
		 if (falling){
			if (screen.getSprites().indexOf(shadow)==-1)
				screen.addSprite(shadow);
			this.moveY(1);
			if (this.y+this.height/2>shadow.getY()){
					screen.deleteSprite(shadow);
					falling=false;			
					lastTime = System.currentTimeMillis();
			}
		} else{
		  if (System.currentTimeMillis() - lastTime<delta){ 
			for (GameObject o:screen.getSprites()){
				 if (this.isCollides(o)){
					if (o.getClass() == ControllableAnimatedSprite.class){
						((ControllableAnimatedSprite)o).downHealth(5);
						screen.destroy(this, 500);
						break;
					}
					else 
					if (o.getClass() == DestroyableBlock.class)	{	 
							((DestroyableBlock)o).downHealth(5);
							screen.destroy(this, 500);
							break;
					}
				} else 
				if (o.getClass() == ControllableAnimatedSprite.class)
				  {
					  if (this.x<o.getX()) this.moveX(1); else this.moveX(-1);
					  if (this.y<o.getY()) this.moveY(1); else this.moveY(-1);
				  }
			}
		  } else 
			  screen.destroy(this,500);
		}
	}
	
	public void setShadowPosition(int x, int y)
	{
		shadow.setPosition(x, y);
		this.x = x;
	}
}