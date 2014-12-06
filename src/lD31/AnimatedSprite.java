package lD31;

import java.awt.image.BufferedImage;
import java.net.URL;

public class AnimatedSprite extends Sprite {
	int currentFrameX, currentFrameY;
	int frameCountX, frameCountY;
	int frameWidth, frameHeight;
	int startX, startY, endX, endY;
	int delta;
	long time;
	int[][] spriteSheet;

	public AnimatedSprite(BufferedImage temp, int frameWidth, int frameHeight) {
		super(temp);
		init(frameWidth, frameHeight);	

	}
	public AnimatedSprite(int[][] arr, int frameWidth, int frameHeight) {
		super(arr);
		init(frameWidth, frameHeight);
	}
	public AnimatedSprite(URL path, int frameWidth, int frameHeight) {
		super(path);
		init( frameWidth, frameHeight);
	}
	
	public void setAnimationBorder(int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.endX = endX;
		this.startY = startY;
		this.endY = endY;
	}

	private void init( int frameWidth, int frameHeight)
	{
			this.frameWidth = frameWidth;
			this.frameHeight = frameHeight;
			this.frameCountX = this.width/this.frameWidth;
			this.frameCountY = this.height/this.frameHeight;
			this.spriteSheet = this.img.clone();
			this.img = new int[frameWidth][frameHeight];
			this.width = frameWidth;
			this.height = frameHeight;
	}
	
	public void nextFrame()
	{
		if (System.currentTimeMillis() - time > delta) {
			if ((currentFrameX < startX && currentFrameY == startY)
					|| (currentFrameY < startY)
					|| (currentFrameX > endX && currentFrameY == endY)
					|| (currentFrameY > endY)) {
				currentFrameX = startX;
				currentFrameY = startY;
			}

			currentFrameX++;
			if (currentFrameX >= frameCountX) {
				currentFrameY++;
				currentFrameX = 0;
			}

			if ((currentFrameX < startX && currentFrameY == startY)
					|| (currentFrameY < startY)
					|| (currentFrameX > endX && currentFrameY == endY)
					|| (currentFrameY > endY)) {
				currentFrameX = startX;
				currentFrameY = startY;
			}
			time = System.currentTimeMillis();
			for(int i=0; i<this.getFrameWidth(); i++)
				for (int j=0; j<this.getFrameHeight(); j++)
				{
					this.img[i][j] = this.spriteSheet[i+this.currentFrameX*this.frameWidth]
													 [j+this.currentFrameY*this.frameHeight];
				}
		}
	}
	
	public void setFrameWidth(int frameWidth)
	{
		this.frameWidth = frameWidth;
	}
	
	public void setFrameHeight(int frameHeight)
	{
		this.frameHeight = frameHeight;
	}
	
	public int getFrameWidth()
	{
		return this.frameWidth;
	}
	
	public int getFrameHeight()
	{
		return this.frameHeight;
	}
	
	public void setDelta(int delta)
	{
		this.delta = delta;
	}
}
