package lD31;

import java.net.URL;

public class Laser extends AnimatedSprite{
	int laserX=0, laserY=0;
	
	long shootCoolDown=800;
	long lastShoot=System.currentTimeMillis();
	long shootingTime=400;
	public Laser(URL path, int frameWidth, int frameHeight) {
		super(path, frameWidth, frameHeight);
	}
	
	public void findLaserEnding(Screen screen)
	{
		if (System.currentTimeMillis() - lastShoot>shootCoolDown)
			lastShoot = System.currentTimeMillis();
		if (System.currentTimeMillis() - lastShoot<shootingTime){
				boolean horizontal= this.currentFrameY>1;
				if (this.currentFrameY==2)
				laserX=this.x+100; 
				if (this.currentFrameY==3)
					laserX=this.x-100; 
				if (this.currentFrameY==1)
					laserY=this.y+100;
				if (this.currentFrameY==0)
					laserY=this.y-100;
				if (horizontal) laserY = this.y+this.height/2;
				else laserX = this.x + this.width/2;
			 GameObject foundedObject = null;
				for (GameObject o:screen.getSprites())
				if (!o.equals(this) && o.getClass()!=Bonus.class)
				{
						if(horizontal && o.isCollides(o.getX()+o.getWidth()/2, laserY) && ((Math.abs(this.x-laserX)>Math.abs(this.x-o.getX()-o.getWidth()/2)  &&
																							Math.abs(this.x-o.getX()-o.getWidth()/2)>5)))
						{	
							foundedObject =o;
							laserX = o.getX()+o.getWidth()/2;	
						}
						if(!horizontal && o.isCollides(laserX , o.getY()+o.getHeight()/2) && (Math.abs(this.y-laserY)>Math.abs(this.y-o.getY()-o.getHeight()/2) &&
																										 Math.abs(this.y-o.getY()-o.getHeight()/2)>5)) 
							{
								foundedObject =o;
								laserY = o.getY()+o.getHeight()/2;
							}
				}
				if (foundedObject.getClass() == ControllableAnimatedSprite.class ){
					((ControllableAnimatedSprite)(foundedObject)).downHealth(1);
					((Sprite)(foundedObject)).useMask(0xFFFF0000);
				} else 
				if (foundedObject.getClass() == DestroyableBlock.class)
					((DestroyableBlock)(foundedObject)).downHealth(1);
		} else
		{
			if (this.x<10)
				this.setAnimationBorder(0, 2, 3, 2); 
			if (this.x>screen.getWidth()-10)
				this.setAnimationBorder(0, 3, 3, 3); 
			if (this.y<10)
				this.setAnimationBorder(0, 1, 3, 1); 
			if (this.y>screen.getHeight()-10)
				this.setAnimationBorder(0, 0, 3, 0); 
			laserX=this.x+this.width/2;
			laserY=this.y+this.height/2;
		}
	}
	
	public int getLaserX()	{ return laserX;}
	
	public int getLaserY() { return laserY;}
	
	public void setShootCoolDown(int shootCoolDown){ this.shootCoolDown = shootCoolDown; }
	
	public void setShootingTime(int shootingTime){ this.shootingTime = shootingTime; }
	
}
