package lD31;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class DestroyableSprite extends Sprite{
	ArrayList<Part> parts = new ArrayList<Part>();
	long lifeTime = -1;
	long createTime = -1;
	public DestroyableSprite(BufferedImage temp) {
		super(temp);
		createTime=System.currentTimeMillis();
	}

	public void setLifeTime(long lifeTime)
	{
		this.lifeTime = lifeTime;
	}
	
	public DestroyableSprite(Sprite spr) {
		super(spr.getImg());
		this.setPosition(spr.getX(), spr.getY());
		createTime=System.currentTimeMillis();
	}
	
	
	public DestroyableSprite(URL resource) {
		super(resource);
		createTime=System.currentTimeMillis();
	}

	public DestroyableSprite(int[][] resource) {
		super(resource);
		createTime=System.currentTimeMillis();
	}

	public void initBombParts() {
		Random r = new Random();
		for (int i = 0; i < this.getWidth(); i++)
			for (int j = 0; j < this.getHeight(); j++) {
				Part p = new Part(i, j, 1, 1);
				p.setVelocityVector((float) (r.nextFloat() - 0.5)/8f,
							        (float) (r.nextFloat() - 1  )/8f);
				p.setAccelerationVector(0, 0);
				parts.add(p);
			}

	}
	
	public void initParts() {
		Random r = new Random();
		for (int i = 0; i < this.getWidth(); i++)
			for (int j = 0; j < this.getHeight(); j++) {
				Part p = new Part(i, j, 1, 1);
				p.setVelocityVector((float) (r.nextFloat() - 0.5),
							        (float) (r.nextFloat() - 0.5));
				parts.add(p);
			}

	}

	public boolean isTimeOut()
	{
		if (lifeTime==-1) return false;
		else return System.currentTimeMillis() - createTime>lifeTime;
	}
	
	public void initParts(int countW, int countH) {
		Random r = new Random();
		for (int i = 0; i < countW; i++)
			for (int j = 0; j < countH; j++) {
				Part p = new Part(this.getWidth() / countW * i,
						this.getHeight() / countH * j,
						this.getWidth() / countW, this.getHeight() / countH);
				p.setVelocityVector((float) (r.nextFloat() - 0.5),
							        (float) (r.nextFloat() - 0.5));
				parts.add(p);
			}
	}

	public void recoverParts() {
		for (Part p : parts) {
			p.recover(true);
		}
	}

	public ArrayList<Part> getParts() {
		return parts;
	}
}
