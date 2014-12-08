package lD31;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.LinkedList;

public class ControllableAnimatedSprite extends AnimatedSprite {
	long lastBlockTime=System.currentTimeMillis();
	int health=100;
	LinkedList<String> blocks = new LinkedList<String>();
	public ControllableAnimatedSprite(URL path, int frameWidth, int frameHeight) {
		super(path, frameWidth, frameHeight);
		blocks.add("box.png");
		blocks.add("box.png");
	}
	
	public void contorl(boolean[] keys, Screen screen)
	{
		if (health<=0)
			screen.destroy(this);
		this.setAnimationBorder(0, this.currentFrameY, 0, this.currentFrameY);
		int x= this.x, y= this.y;
		
		if (keys[KeyEvent.VK_UP]){
			this.moveY(-1);
			this.setAnimationBorder(0, 1, 3, 1);
		} 
		if (keys[KeyEvent.VK_DOWN]){
			this.moveY(1);
			this.setAnimationBorder(0, 0, 3, 0);
		}
		if (keys[KeyEvent.VK_RIGHT]){
			this.moveX(1);
			this.setAnimationBorder(0, 2, 3, 2);
		}
		if (keys[KeyEvent.VK_LEFT]){
			this.moveX(-1);
			this.setAnimationBorder(0, 3, 3, 3);
		}
		
		if (keys[KeyEvent.VK_Z] && System.currentTimeMillis() - lastBlockTime>500 && blocks.size()>0){
			int blockX=this.x,blockY=this.y;
			if (this.currentFrameY==0)
				blockY+=this.height;
			else if (this.currentFrameY==1)
				blockY-=8; 
			else if (this.currentFrameY==2)
				blockX+=this.width;
			else blockX-=8;
			lastBlockTime = System.currentTimeMillis();
			String b = blocks.getFirst();
			int strength = 0;
			if (b.equals("dirt.png")) strength=50; else 
				if (b.equals("box.png")) strength=80; else 
					if (b.equals("rock.png")) strength=120; else 
						if (b.equals("steel.png")) strength=200; 
								
			DestroyableBlock block = new DestroyableBlock(getClass().getClassLoader().getResource(b),strength );
			blocks.removeFirst();
			block.setPosition(blockX, blockY);
			block.setDefaultBoxCollider();
			screen.addSprite(block);
		}
		boolean collide = false;
		for (GameObject s:screen.getSprites())
			if (s!= this && s.getClass()!=Bonus.class && isCollides(s))	collide = true;
		if (collide){
				this.setPosition(x,y);
				
		}
	//	System.out.println(collide);
		
	}
	public void downHealth(int h){ this.health-=h; }

	public void upHealth(int h){ this.health+=h; if (this.health>100) this.health=100; }
	
	public LinkedList<String> getBlocks(){ return blocks;}
}
