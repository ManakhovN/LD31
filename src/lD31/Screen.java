package lD31;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Screen extends BufferedImage{
	int width, height;
	ArrayList<GameObject> sprites = new ArrayList<GameObject>();
	Color backColor = new Color(0,0,0,255);
	Graphics g  = this.getGraphics();
	int[][] backgr=null;
	
	public Screen(int width, int height)
	{
		super(width, height, BufferedImage.TYPE_INT_ARGB);
		this.width = width;
		this.height = height;
	}
	
	public void setBackGround(URL path)
	{
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(path);
		} catch (IOException e) {
		}
		backgr = new int[this.width][this.height];
		for (int i = 0; i < temp.getWidth(); i++)
			for (int j = 0; j < temp.getHeight(); j++)
				backgr[i][j] = temp.getRGB(i, j);		
	}
	public void render()
	{
		if (backgr==null){
			g.setColor(Color.black);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		} else 
		{
			for (int i=0; i<this.width; i++)
				for (int j=0; j<this.height; j++)
					this.setRGB(i, j, backgr[i][j]);
		}

		for (int i=0; i<sprites.size(); i++){
			
			if (sprites.get(i).getClass()== DestroyableBlock.class)
				{if (((DestroyableBlock)sprites.get(i)).getHealth()<0) this.destroy(i, 800);}
				else 
			if (sprites.get(i).getClass()== Bonus.class){
				if (((Bonus)sprites.get(i)).landing()) {   this.deleteSprite(i); i--; continue;	}
			} else 
				if (sprites.get(i).getClass()== ShurikenThrower.class)
					((ShurikenThrower)sprites.get(i)).update(this);
				else 
					if (sprites.get(i).getClass()== Bomb.class)
						((Bomb)sprites.get(i)).update(this);
				else 
					if (sprites.get(i).getClass()== Spider.class)
						((Spider)sprites.get(i)).update(this);
			
			if (sprites.get(i).getClass()!=DestroyableSprite.class && sprites.get(i).getClass()!=GameObject.class )
			{
				Sprite currentSprite = (Sprite) sprites.get(i);
				if (currentSprite.getClass()==Laser.class)
				{
					Laser l =(Laser)currentSprite;
					l.findLaserEnding(this);
					if (l.currentFrameY==2) 
						for (int j=currentSprite.getX()+currentSprite.getWidth()/2; j<l.getLaserX(); j++)
							this.setRGB(j, l.getLaserY(), 0xFFFF0000);
					
					if (l.currentFrameY==3) 
						for (int j=l.getLaserX(); j<currentSprite.getX()+currentSprite.getWidth()/2; j++)
							this.setRGB(j, l.getLaserY(), 0xFFFF0000);
					
					if (l.currentFrameY==1) 
						for (int j=currentSprite.getY()+currentSprite.getHeight()/2; j<l.getLaserY(); j++)
							this.setRGB(l.getLaserX(), j, 0xFFFF0000);
					
					if (l.currentFrameY==0) 
						for (int j=l.getLaserY(); j<currentSprite.getY()+currentSprite.getHeight()/2; j++)
							this.setRGB(l.getLaserX(), j, 0xFFFF0000);
					
					
				}
				
				if (currentSprite.getClass() != Sprite.class && currentSprite.getClass() != DestroyableBlock.class && currentSprite.getClass() != Bonus.class)
				{
					((AnimatedSprite)currentSprite).nextFrame();
				}
					
					for (int ii=0; ii<currentSprite.getWidth(); ii++)
						for (int jj = 0; jj<currentSprite.getHeight(); jj++)
						 if (currentSprite.get(ii, jj)!=0xFFFF00FF &&
							 ii + currentSprite.getX()>=0 && ii+currentSprite.getX()<this.getWidth() &&
							 jj + currentSprite.getY()>=0 && jj+currentSprite.getY()<this.getHeight())
							this.setRGB(ii+currentSprite.getX(), jj+currentSprite.getY(), currentSprite.get(ii, jj)&currentSprite.getMask());
					if (currentSprite.getClass() == ControllableAnimatedSprite.class) currentSprite.useMask(0xFFFFFFFF);
			} else
			if (sprites.get(i).getClass()==DestroyableSprite.class)
			{
				DestroyableSprite currentSprite = (DestroyableSprite) sprites.get(i);
				if (currentSprite.isTimeOut()) 
					{
						sprites.remove(i);
						i--;
					}
				else
					for (Part currentPart:currentSprite.getParts())
						{
						for (int ii=0; ii<currentPart.getWidth(); ii++)
							for (int jj=0; jj<currentPart.getHeight();jj++)
								if (currentSprite.get(currentPart.getX()+ii, currentPart.getY()+jj) != 0xFFFF00FF &&
									currentPart.getPartX()+ii+currentSprite.getX()>=0 &&
									currentPart.getPartX()+ii+currentSprite.getX()<this.getWidth() &&
									currentPart.getPartY()+jj+currentSprite.getY()>=0 && 
									currentPart.getPartY()+jj+currentSprite.getY()<this.getHeight())
							{
								this.setRGB((int)(currentPart.getPartX()+ii+currentSprite.getX()),
											(int)(currentPart.getPartY()+jj+currentSprite.getY()),
											currentSprite.get(currentPart.getX()+ii, currentPart.getY()+jj) &currentSprite.getMask());
							}
						currentPart.move();
					}
			}
		}		
	}
	
	public void addSprite(URL path)
	{
		Sprite tempSprite = new Sprite(path);
		sprites.add(tempSprite);
	}
	
	public void addSprite(Sprite spr)
	{
		sprites.add(spr);
	}
	
	public void addGameObject(GameObject obj)
	{
		sprites.add(obj);
	}
	
	public GameObject getSprite(int index)
	{
		if (sprites.size()>index)
		return sprites.get(index); else
			return null;
	}
	
	public void deleteSprite(int index)
	{
		if (sprites.size()>index)
			sprites.remove(index);
	}
	
	public void deleteSprite(GameObject obj)
	{
		int index = this.sprites.indexOf(obj);
		if (index!=-1)
			sprites.remove(index);
	}

	public void destroy(int i)
	{
		DestroyableSprite spr = new DestroyableSprite((Sprite)this.sprites.get(i));
		spr.setLifeTime(1000);
		spr.initParts();
		this.sprites.remove(i);
		this.sprites.add(i,spr);
	}
	public void destroy(int i, int lifetime)
	{
		DestroyableSprite spr = new DestroyableSprite((Sprite)this.sprites.get(i));
		spr.setLifeTime(lifetime);
		spr.initParts();
		this.sprites.remove(i);
		this.sprites.add(i,spr);
	}
	
	public void destroy(GameObject obj, int lifetime)
	{
		int i = this.sprites.indexOf(obj);
		if (i!=-1){
			DestroyableSprite spr = new DestroyableSprite((Sprite)this.sprites.get(i));
			spr.setLifeTime(lifetime);
			spr.initParts();
			this.sprites.remove(i);
			this.sprites.add(i,spr);
		}
	}
	public void destroy(GameObject obj)
	{
		int i = this.sprites.indexOf(obj);
		if (i!=-1){
			DestroyableSprite spr = new DestroyableSprite((Sprite)this.sprites.get(i));
			spr.initParts();
			this.sprites.remove(i);
			this.sprites.add(i,spr);
		}
	}
	
	
	public void recover(int i)
	{
		((DestroyableSprite)this.sprites.get(i)).recoverParts();
	}
		
	public void destroy(int i, int partW, int partH)
	{
		DestroyableSprite spr = new DestroyableSprite((Sprite)this.sprites.get(i));
		spr.initParts(partW,partH);
		this.sprites.remove(i);
		this.sprites.add(i,spr);
	}
	
	public ArrayList<GameObject> getSprites()
	{
		return this.sprites;
	}
}
