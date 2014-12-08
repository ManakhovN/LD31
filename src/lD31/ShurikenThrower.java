package lD31;

import java.net.URL;
import java.util.ArrayList;

public class ShurikenThrower extends AnimatedSprite{
	long lastTime=0;
	long delta=8000;
	ArrayList<AnimatedSprite> shurikanes;
	boolean[] activeShurikanes = new boolean[4];
	public ShurikenThrower(URL path, URL subitem, int frameWidth, int frameHeight) {
		super(path, frameWidth, frameHeight);
		shurikanes = new ArrayList<AnimatedSprite>();
		
		for (int i=0; i<4; i++) {
			AnimatedSprite shurikane = new AnimatedSprite(subitem,4,4);
			shurikane.setAnimationBorder(0,0, 1, 0);
			shurikane.setDefaultBoxCollider();
			shurikane.setPosition(this.x+this.width/2, this.y+this.height/2);
			shurikanes.add(shurikane);
			activeShurikanes[i]=true; 	
		}

	}

	public void update(Screen screen)
	{

		for (GameObject o:screen.getSprites())
		{
			for (GameObject shurikane:shurikanes)
			if  (activeShurikanes[shurikanes.indexOf(shurikane)]) 
			{
				if (shurikane.isCollides(o))
					if (o.getClass() == DestroyableBlock.class)
					{ 
						activeShurikanes[shurikanes.indexOf(shurikane)]=false;
						((DestroyableBlock)o).downHealth(5);
					} else
						if (o.getClass() == ControllableAnimatedSprite.class)
						{ 
							activeShurikanes[shurikanes.indexOf(shurikane)]=false;
							((ControllableAnimatedSprite)o).downHealth(1);
							((ControllableAnimatedSprite)o).useMask(0xFFFF0000);
						}
			}
		}
		for (int i=0; i<4; i++) if (!activeShurikanes[i]) screen.deleteSprite(shurikanes.get(i));
		shurikanes.get(0).moveX(1); shurikanes.get(0).moveY(1);
		shurikanes.get(1).moveX(1); shurikanes.get(1).moveY(-1);
		shurikanes.get(2).moveX(-1); shurikanes.get(2).moveY(-1);
		shurikanes.get(3).moveX(-1); shurikanes.get(3).moveY(1);
		if (System.currentTimeMillis() - lastTime>this.delta)
		{
			lastTime = System.currentTimeMillis();
			for (int i=0; i<4; i++) {
				screen.deleteSprite(shurikanes.get(i));
				activeShurikanes[i]= true;
				shurikanes.get(i).setPosition(this.x+this.width/2, this.y+this.height/2);
				screen.addSprite(shurikanes.get(i));
			}
		}
	}
	
	public void setDelta(int delta)	{	this.delta = delta;	}
	
	public long getDelta(){  return this.delta;	}
}
