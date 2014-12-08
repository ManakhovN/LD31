package lD31;

public class GameObject extends Object {
	Collider collider = null;
	public GameObject(int x, int y) {
		super(x, y);
	}
	
	public GameObject(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void setDefaultBoxCollider()	{ collider = new Collider(0,0,width,height);}
	public void setDefaultCircleCollider(){collider = new Collider(width/2,height/2,width/2);}
	public Collider getCollider(){ return this.collider; }
	public void setCollider(Collider collider){ this.collider = collider; }
	
	public boolean isCollides(GameObject object)
	{
		if (object==null || collider == null || object.getCollider()==null) return false;
		int x1 = this.x+collider.getX();
		int y1 = this.y+collider.getY();
		int x2 = object.getX() + object.getCollider().getX();
		int y2 = object.getY() + object.getCollider().getY();
		int width1= this.collider.getWidth(), height1 = this.collider.getHeight();
		int width2= object.getCollider().getWidth(), height2 = object.getCollider().getHeight();
		
		int dx = x1-x2;
		int dy = y1-y2;

		if (height1==0 && height2==0){

			return (dx*dx+dy*dy<width1*width1+width2*width2);}
		
		if (height1==0){
			return (dx>-width1 &&
					dx+width1<width2 &&
					dy>-width1 &&
					dy-width1<height2);
			}
		
		if (height2==0){

			return (-dx>-width2 &&
					-dx-width2<width1 &&
					-dy>-width1 &&
					-dy-width2<height1);
		}
		
		return (dx<width2 &&
				dx>-width1 &&
				dy<height2 &&
				dy>-height1);
	}
	
	public boolean isCollides(int x, int y)
	{
		if (collider ==null) return false;
		int x1 = this.x+collider.getX();
		int y1 = this.y+collider.getY();
		if (collider == null) return false;
		if (this.height == 0)
			  return isPointInCircle(x1-x, y1-y, width);
		else 
			return x>x1 && x<x1+this.width &&
				   y>y1 && y<y1+this.height; 
	}
	
	private boolean isPointInCircle(int dx, int dy, int r){	
		return dx*dx+dy*dy<r*r;	}
	
	public void moveX(int delta) {	this.x += delta; }

	public void moveY(int delta) {	this.y += delta; }
	
}
