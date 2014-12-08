package lD31;

import java.util.Random;

public class Spawner {
	long createTime;
	int statement;		
	Random r = new Random();
	public Spawner()
	{
		this.createTime =  System.currentTimeMillis();
		statement = 0;
	}
	
	public void update(Screen screen)
	{

		if (System.currentTimeMillis() - this.createTime>7000)
		{
			createTime=System.currentTimeMillis();
			if (statement%4==3){
				for (int i=0; i<statement/3+1; i++)
				spawnBomb(screen);
			} else
			if (statement%4==2)	{
				int k=r.nextInt(4);
				if (k==0) spawnThrower(screen,r.nextInt(screen.getWidth()-8), 0);
				if (k==1) spawnThrower(screen,r.nextInt(screen.getWidth()-8), screen.getHeight()-8);
				if (k==2) spawnThrower(screen,0, r.nextInt(screen.getHeight()-24)+8);
				if (k==3) spawnThrower(screen,screen.getWidth()-8, r.nextInt(screen.getHeight()-24)+8);
			}else
			if (statement%4==1)	{
				int k=r.nextInt(4);
				if (k==0) spawnLaser(screen,r.nextInt(screen.getWidth()-8), 2, 						  500,800);
				if (k==1) spawnLaser(screen,r.nextInt(screen.getWidth()-8), screen.getHeight()-8,    500,800);
				if (k==2) spawnLaser(screen,2, r.nextInt(screen.getHeight()-24)+8, 					  500,800);
				if (k==3) spawnLaser(screen,screen.getWidth()-8, r.nextInt(screen.getHeight()-24)+8, 500,800);
			
			} else
			if (statement%4==0){
				for (int i=0; i<statement/4+1; i++)
				spawnSpider(screen);
			}
			statement++;
		}
	}
//	r.nextInt(screen.getWidth()-24)+8, r.nextInt(screen.getHeight()-24)+8
	public void spawnThrower(Screen screen, int x, int y)
	{
		ShurikenThrower thrower = new ShurikenThrower(getClass().getClassLoader().getResource("shurikenspawner.png"), 
				  getClass().getClassLoader().getResource("shuriken.png"), 8, 8);
		thrower.setAnimationBorder(0, 0, 3, 0);
		thrower.setPosition(x,y);
		screen.addSprite(thrower);
	}
	
	public void spawnLaser(Screen screen, int x, int y, int shootingTime, int shootCoolDown)
	{
		Laser laser = new Laser(getClass().getClassLoader().getResource("laser.png"),8,8);
		laser.setAnimationBorder(0, 0, 4, 0);
		laser.setDelta(1000);
		laser.setPosition(x, y);
		laser.setShootingTime(shootingTime);
		laser.setShootCoolDown(shootCoolDown);
		screen.addSprite(laser);
	}
	
	public void spawnBomb(Screen screen)
	{
		Bomb bomb = new Bomb(getClass().getClassLoader().getResource("rocket.png"), 
						      getClass().getClassLoader().getResource("shadow.png"), 4, 11);
		bomb.setShadowPosition(r.nextInt(screen.getWidth()-24)+8, r.nextInt(screen.getHeight()-24)+8);
		screen.addSprite(bomb);
	}
	public void spawnSpider(Screen screen)
	{
		Spider spider = new Spider(getClass().getClassLoader().getResource("spider.png"), 
						      getClass().getClassLoader().getResource("shadow.png"), 8, 8);
		spider.setShadowPosition(r.nextInt(screen.getWidth()-24)+8, r.nextInt(screen.getHeight()-24)+8);
		screen.addSprite(spider);
	}
}
