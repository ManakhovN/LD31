package lD31;

public class Part extends Object{
	float partX, partY;
	float vx, vy;
	float ax, ay;
	boolean isRecovering = false;
	
	public Part(int x, int y, int width, int height) {
		super(x, y,width,height);
		this.partX = x;
		this.partY = y;
		this.ax = 0;
		this.ay = 0.01f;
	}
	
	public void setPartPosition(float partX, float partY)
	{
		this.partX = partX;
		this.partY = partY;
	}
	
	public float getPartX()
	{
		return this.partX;
	}
	
	public float getPartY()
	{
		return this.partY;
	}
	
	public void setPartX(int partX)
	{
		this.partX = partX;
	}
	
	public void setPartY(int partY)
	{
		this.partY = partY;
	}	
	
	public void setVelocityX(float vx)
	{
		this.vx = vx;
	}
	
	public void setVelocityY(float vy)
	{
		this.vy = vy;
	}
	
	public float getVelocityX()
	{
		return this.vx;
	}
	
	public float getVelocityY()
	{
		return this.vy;
	}
	
	
	public void setAccelerationX(float ax)
	{
		this.ax = ax;
	}
	
	public void setAccelerationY(float ay)
	{
		this.ay = ay;
	}
	
	public float getAccelerationX()
	{
		return this.ax;
	}
	
	public float getAccelerationY()
	{
		return this.ay;
	}
	
	public void recover(boolean isRecovering)
	{
		this.isRecovering = isRecovering;
	}
	
	public void setVelocityVector(float vx, float vy)
	{

		this.vx = vx;
		this.vy = vy;

	}

	public void setAccelerationVector(float ax, float ay)
	{

		this.ax = ax;
		this.ay = ay;

	}
	
	public void move()
	{
		if (this.isRecovering)
		{
			this.setAccelerationVector(0, 0);
			this.setVelocityVector((this.getX()-this.getPartX())/10, (this.getY()-this.getPartY())/10);
			this.setPartPosition(this.getPartX()+this.getVelocityX(),
				   	       this.getPartY()+this.getVelocityY());
			if (Math.abs(this.getVelocityX())<0.01 && Math.abs(this.getVelocityY())<0.01){
				this.setVelocityVector(0, 0);
				this.setPartPosition(this.getX(), this.getY());
				this.recover(false);
			}
		}
		else {
			this.setPartPosition(this.getPartX()+this.getVelocityX(),
					   	   this.getPartY()+this.getVelocityY());	
			this.setVelocityX(this.getVelocityX()+this.ax);
			this.setVelocityY(this.getVelocityY()+this.ay);
		}
	}
	
	public void move(int borderY)
	{
		if (this.isRecovering)
		{
			this.setAccelerationVector(0, 0);
			this.setVelocityVector((this.getX()-this.getPartX())/10, (this.getY()-this.getPartY())/10);
			this.setPartPosition(this.getPartX()+this.getVelocityX(),
				   	       this.getPartY()+this.getVelocityY());
			if (Math.abs(this.getVelocityX())<0.01 && Math.abs(this.getVelocityY())<0.01){
				this.setVelocityVector(0, 0);
				this.setPartPosition(this.getX(), this.getY());
				this.recover(false);
			}
		} else {
			this.setPartPosition(this.getPartX()+this.getVelocityX(),
				   	   this.getPartY()+this.getVelocityY());
			if (this.getPartY()>borderY)
			{
				this.setVelocityY(-this.getVelocityY()/2);
				this.setVelocityX( this.getVelocityX()/2);
			} else{	
			 this.setVelocityX(this.getVelocityX()+this.ax);
			 this.setVelocityY(this.getVelocityY()+this.ay); 
			}
		}
	}
}