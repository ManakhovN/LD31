package lD31;

import java.net.URL;

public class Bomb extends AnimatedSprite {
	Sprite shadow;
	
	public Bomb(URL path, URL shadow, int frameWidth, int frameHeight) {
		super(path, frameWidth, frameHeight);
		this.setDefaultBoxCollider();
		this.shadow = new Sprite(shadow);
		this.y = -80;
	}

	public void update(Screen screen)
	{
		if (screen.getSprites().indexOf(shadow)==-1)
			screen.addSprite(shadow);
		this.moveY(1);
		
			
		if (this.y+this.height/2>shadow.getY()){
			screen.deleteSprite(shadow);
			
			for (GameObject o:screen.getSprites())
			  if (this.isCollides(o))
				if (o.getClass() == ControllableAnimatedSprite.class){
					((ControllableAnimatedSprite)o).downHealth(10);
					((ControllableAnimatedSprite)o).useMask(0xFFFF0000);
					}
				else 
				if (o.getClass() == DestroyableBlock.class)
					((DestroyableBlock)o).downHealth(50);
			
			addMoreParticles(screen, 0xFFFFFF00);
			addMoreParticles(screen, 0xFF000000);
			addMoreParticles(screen, 0xFFFF0000);
			screen.destroy(this, 3000);
		}
	}
	
	private void addMoreParticles(Screen screen, int color)
	{
		int[][] part = new int[10][10];
		
		for (int i=0; i<10; i++)
			for (int j=0; j<10; j++)
				part[i][j] = color;
		
		DestroyableSprite explosion = new DestroyableSprite(part);
		explosion.initBombParts();
		explosion.setLifeTime(3000);
		explosion.setPosition(x, y);
		screen.addGameObject(explosion);
	}
	
	public void setShadowPosition(int x, int y)
	{
		shadow.setPosition(x, y);
		this.x = x;
	}
}
