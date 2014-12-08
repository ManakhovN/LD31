package lD31;

import java.net.URL;

public class DestroyableBlock extends Sprite{
	int health;
	public DestroyableBlock(URL path,int health) {
		super(path);
		this.health = health;
	}


	public void downHealth(int h){ this.health-=h; }
	
	public int getHealth() { return health; }
}
