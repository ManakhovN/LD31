package lD31;

public class Object {
	int x,y;
	int width=0, height=0;
	
	public Object(int x, int y){ this.x = x; this.y = y; }
	
	public Object(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setX(int x)	{	this.x = x;	}
	
	public void setY(int y)	{ this.y = y; }
	
	public void setPosition(int x, int y){	this.x = x;	this.y=y;}
	
	public int getX(){	return x; }
	
	public int getY(){	return y; }
	
	public void setWidth(int width)	{	this.width = width;	}
	
	public void setHeight(int height)	{ this.height = height; }
	
	public int getWidth(){	return this.width; }
	
	public int getHeight(){	return this.height; }

}
