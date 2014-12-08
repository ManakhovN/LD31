package lD31;

import java.util.Random;

public class BonusSpawner {
	long lastSpawn = System.currentTimeMillis();
	long delta=5000;
	Bonus bonus = new Bonus(getClass().getClassLoader().getResource("bonus.png"),null);
	public BonusSpawner(long delta)
	{
		this.delta = delta;
	}
	
	public void update(Screen screen, ControllableAnimatedSprite player)
	{
		if (System.currentTimeMillis() - lastSpawn>delta)
		{
			bonus.setPlayer(player);
			Random r= new Random();
			screen.deleteSprite(bonus);
			bonus.setPosition(r.nextInt(screen.getWidth()-24)+8, r.nextInt(screen.getHeight()-24)+8);
			bonus.reroll();
			screen.addGameObject(bonus);
			lastSpawn = System.currentTimeMillis();
		}
	}
}
