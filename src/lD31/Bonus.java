package lD31;

import java.net.URL;
import java.util.Random;

public class Bonus extends Sprite{
	String contains;
	ControllableAnimatedSprite player;
	public Bonus(URL temp, ControllableAnimatedSprite player) {
		super(temp);
		this.player = player;
		this.setDefaultBoxCollider();
		reroll();
	}
	public void reroll()
	{
		String[] stuffs = {"rock.png","box.png","steel.png","dirt.png","medicine"};		
		contains = stuffs[(int) (System.currentTimeMillis()%stuffs.length)];
	}
	
	public void setPlayer(ControllableAnimatedSprite player){ this.player=player;}
	public boolean landing()
	{
		Random r = new Random();
		int rgb = 0xFF;
		rgb = (rgb << 8) + r.nextInt(255);
		rgb = (rgb << 8) + r.nextInt(255);
		rgb = (rgb << 8) + r.nextInt(255);
		this.useMask(rgb);
		if (this.isCollides(player))
		{
			if (contains.equals("medicine"))
				player.upHealth(30);
			else
				player.getBlocks().add(contains);
			return true;
		}
		return false;
	}
}
