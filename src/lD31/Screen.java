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
			
			if (sprites.get(i).getClass()==Sprite.class || sprites.get(i).getClass() == AnimatedSprite.class || sprites.get(i).getClass()== ControllableAnimatedSprite.class)
			{
				Sprite currentSprite = (Sprite) sprites.get(i);
				if (currentSprite.getClass() == AnimatedSprite.class || currentSprite.getClass() == ControllableAnimatedSprite.class)
				{
					((AnimatedSprite)currentSprite).nextFrame();
				}
					for (int ii=0; ii<currentSprite.getWidth(); ii++)
						for (int jj = 0; jj<currentSprite.getHeight(); jj++)
						 if (currentSprite.get(ii, jj)!=0xFFFF00FF &&
							 ii + currentSprite.getX()>=0 && ii+currentSprite.getX()<this.getWidth() &&
							 jj + currentSprite.getY()>=0 && jj+currentSprite.getY()<this.getHeight())
							this.setRGB(ii+currentSprite.getX(), jj+currentSprite.getY(), currentSprite.get(ii, jj));
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
											currentSprite.get(currentPart.getX()+ii, currentPart.getY()+jj));
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

	public void destroy(int i)
	{
		DestroyableSprite spr = new DestroyableSprite((Sprite)this.sprites.get(i));
		spr.initParts();
		this.sprites.remove(i);
		this.sprites.add(i,spr);
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
