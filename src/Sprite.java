import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Sprite {

	//fields
	public float x;
	public float y;
	public float speed;
	float backSpeed;
	public Image image;
	float angle;
	float vx, vy; //velocity in x and y directions
	public boolean alive;
	float turnSpeed;
	public boolean jumping;
	public float jumpSpeed; //must be a negative value larger than gravity
	float ORIGJUMPSPEED; //the original jump speed

	//constructors
	public Sprite(Image i)
	{
		image = i;
		ORIGJUMPSPEED = 0;
	}//end constructor Sprite

	//methods
	public void draw(Graphics g)
	{
		if(alive)
			g.drawImage(image, x, y);
	}//end method draw

	public void rotateRight(int delta)
	{
		//keep track of what angle the image should be at
		angle += turnSpeed * delta;

		//rotate my image to the correct angle
		image.setRotation(angle);
	}//end rotateRight

	public void rotateLeft(int delta)
	{
		//keep track of what angle the image should be at
		angle -= turnSpeed * delta;

		//rotate my image to the correct angle
		image.setRotation(angle);
	}//end rotateLeft

	public float calcAngleMoveX(double angle)
	{
		return (float)Math.cos((angle-90) * Math.PI / 180);
	}//end calcAngleMoveX

	public float calcAngleMoveY(double angle)
	{
		return (float)Math.sin((angle-90) * Math.PI / 180);
	}//end calcAngleMoveY

	public void moveForward(int delta)
	{
		//calculate velocities in the x and y directions
		vx = calcAngleMoveX(angle);
		vy = calcAngleMoveY(angle);

		//move (x,y) by velocities
		x += vx * speed * delta;
		y += vy * speed * delta;
	}

	public void moveBackward(int delta)
	{
		//calculate velocities in the x and y directions
		vx = calcAngleMoveX(angle);
		vy = calcAngleMoveY(angle);

		//move (x,y) by velocities
		x -= vx * backSpeed * delta;
		y -= vy * backSpeed * delta;
	}
	
	public void moveLeft(int delta)
	{
		x-=speed*delta;
	}
	
	public void moveRight(int delta)
	{
		x+=speed*delta;
	}

	public void bounceSprite(GameContainer gc, int delta)
	{
		//move forward
		x += vx * speed * delta;
		y += vy * speed * delta;

		//left wall
		if(x <= 0)
		{
			//place it back on the screen perfectly
			x = 0;

			//reverses x velocity
			vx *= -1;
		}//end if

		//top wall
		if(y <= 0)
		{
			//place it back on the screen perfectly
			y = 0;

			//reverse y velocity
			vy *= -1;
		}//end if

		//right wall
		if(x + image.getWidth() >= gc.getWidth())
		{
			//place it back on the screen perfectly
			x = gc.getWidth() - image.getWidth();

			//reverse x velocity
			vx *= -1;
		}//end if

		//bottom wall
		if(y + image.getHeight() >= gc.getHeight())
		{
			//place it back on the screen perfectly
			y = gc.getHeight() - image.getHeight();

			//reverse y velocity
			vy *= -1;

		}//end if

	}//end bounceSprite

	public boolean spriteCollision(Sprite other)
	{
		float left1, left2;
		float right1, right2;
		float top1, top2;
		float bottom1, bottom2;

		left1 = x;
		left2 = other.x;
		right1 = x + image.getWidth();
		right2 = other.x + other.image.getWidth();
		top1 = y;
		top2 = other.y;
		bottom1 = y + image.getHeight();
		bottom2 = other.y + other.image.getHeight();

		if (bottom1 < top2) return false; //no collision
		if (top1 > bottom2) return false; //no collision

		if (right1 < left2) return false; //no collision
		if (left1 > right2) return false; //no collision

		return true; //YES, collision

	}//end spriteCollision
	
	public boolean spriteCollisionByPoint(float px, float py)
	{
		float left1, left2;
		float right1, right2;
		float top1, top2;
		float bottom1, bottom2;

		left1 = x;
		left2 = px;
		right1 = x + image.getWidth();
		right2 = px;
		top1 = y;
		top2 = py;
		bottom1 = y + image.getHeight();
		bottom2 = py;

		if (bottom1 < top2) return false; //no collision
		if (top1 > bottom2) return false; //no collision

		if (right1 < left2) return false; //no collision
		if (left1 > right2) return false; //no collision

		return true; //YES, collision

	}//end spriteCollision

	public boolean spriteCollision(AnimatedSprite other)
	{
		float left1, left2;
		float right1, right2;
		float top1, top2;
		float bottom1, bottom2;

		left1 = x;
		left2 = other.x;
		right1 = x + image.getWidth();
		right2 = other.x + other.anim.getImage(0).getWidth();
		top1 = y;
		top2 = other.y;
		bottom1 = y + image.getHeight();
		bottom2 = other.y + other.anim.getImage(0).getHeight();

		if (bottom1 < top2) return false; //no collision
		if (top1 > bottom2) return false; //no collision

		if (right1 < left2) return false; //no collision
		if (left1 > right2) return false; //no collision

		return true; //YES, collision

	}//end spriteCollision

	public void chaseWithAngle(Sprite other, int delta)
	{
		float angleBet = (float) computeAngleBetweenPoints(x, y, other.x, other.y);
		System.out.println("Angle between is " + angleBet);
		System.out.println("My angle is " + angle);

		if(angle < angleBet)
		{
			angle += turnSpeed * delta;
			image.setRotation(angle);
		}//end if
		else 
		{
			angle -= turnSpeed * delta;
			image.setRotation(angle);
		}//end else

		moveForward(delta);
	}//end chaseWithAngle

	public void chaseWithAngle2(Sprite other, int delta)
	{
		float angleBet = (float) computeAngleBetweenPoints(x, y, other.x, other.y);
		System.out.println("Angle between is " + angleBet);
		System.out.println("My angle is " + angle);

		System.out.println("Difference is " + (Math.abs(angleBet - angle)));

		if(angle < angleBet)
		{
			angle += turnSpeed * delta;
			image.setRotation(angle);
		}//end if
		else 
		{
			angle -= turnSpeed * delta;
			image.setRotation(angle);
		}//end else

		moveForward(delta);
	}//end chaseWithAngle

	public void simpleChase(Sprite other, int delta)
	{   
		float midx, midy, othermidx, othermidy;

		if(!(this instanceof AnimatedSprite))
		{
			midx = x + image.getWidth()/2;
			midy = y + image.getHeight()/2;
		}//end if
		else //this must be an animated sprite
		{
			midx = x + ((AnimatedSprite)this).anim.getImage(0).getWidth()/2;
			midy = y + ((AnimatedSprite)this).anim.getImage(0).getHeight()/2;
			//System.out.println("this is animated");
		}//end else

		if(!(other instanceof AnimatedSprite))
		{
			othermidx = other.x + other.image.getWidth()/2;
			othermidy = other.y + other.image.getHeight()/2;
		}//end if
		else //other must be an animated sprite
		{
			othermidx = other.x + ((AnimatedSprite)other).anim.getImage(0).getWidth()/2;
			othermidy = other.y + ((AnimatedSprite)other).anim.getImage(0).getHeight()/2;
			//System.out.println("other is animated");
		}//end else

		//difference in x and y values
		//The distance between them in both x and y directions
		float deltax = Math.abs(midx - (othermidx));
		float deltay = Math.abs(midy - (othermidy));

		//if the sprite is further away in the x-direction
		if(deltax > deltay)
		{
			//if I am to the right
			if(midx > othermidx)
				x -= speed * delta; //move left
			//else I am to the left
			else
				x += speed * delta; //move right
		}//end if
		//else the sprite is further away in the y-direction
		else
		{
			//if I am below the other sprite
			if(midy > othermidy)
				y -= speed * delta; //move up
			//else I am above the other sprite
			else
				y += speed * delta; //move down
		}//end else
	}//end SimpleChase

	public void simpleEvade(Sprite other, int delta)
	{   
		float midx, midy, othermidx, othermidy;

		if(!(this instanceof AnimatedSprite))
		{
			midx = x + image.getWidth()/2;
			midy = y + image.getHeight()/2;
		}//end if
		else //this must be an animated sprite
		{
			midx = x + ((AnimatedSprite)this).anim.getImage(0).getWidth()/2;
			midy = y + ((AnimatedSprite)this).anim.getImage(0).getHeight()/2;
			//System.out.println("this is animated");
		}//end else

		if(!(other instanceof AnimatedSprite))
		{
			othermidx = other.x + other.image.getWidth()/2;
			othermidy = other.y + other.image.getHeight()/2;
		}//end if
		else //other must be an animated sprite
		{
			othermidx = other.x + ((AnimatedSprite)other).anim.getImage(0).getWidth()/2;
			othermidy = other.y + ((AnimatedSprite)other).anim.getImage(0).getHeight()/2;
			//System.out.println("other is animated");
		}//end else

		//difference in x and y values
		//The distance between them in both x and y directions
		float deltax = Math.abs(midx - (othermidx));
		float deltay = Math.abs(midy - (othermidy));

		//if the sprite is further away in the x-direction
		if(deltax > deltay)
		{
			//if I am to the right
			if(midx > othermidx)
				x += speed * delta; //move left
			//else I am to the left
			else
				x -= speed * delta; //move right
		}//end if
		//else the sprite is further away in the y-direction
		else
		{
			//if I am below the other sprite
			if(midy > othermidy)
				y += speed * delta; //move up
			//else I am above the other sprite
			else
				y -= speed * delta; //move down
		}//end else
	}//end SimpleEvade

	public void keepOnScreen(GameContainer gc)
	{
		int screenw = gc.getWidth();
		int screenh = gc.getHeight();

		//if Sprite goes off the left of the screen
		if(x < 0)
		{
			x = 0;
		}//end if
		//if Sprite goes off the top of the screen
		if(y < 0)
		{
			y = 0;
		}//end if

		//if it is not an animated Sprite
		if(!(this instanceof AnimatedSprite))
		{
			//if Sprite goes off the right of the screen
			if(x + image.getWidth() > screenw)
			{
				x = screenw - image.getWidth();  
			}//end if
		}//end if
		//else if is an Animated Sprite
		else
		{
			//if AnimatedSprite goes off the right of the screen
			if(x + ((AnimatedSprite)this).anim.getImage(0).getWidth() > screenw)
			{
				x = screenw - ((AnimatedSprite)this).anim.getImage(0).getWidth();
			}//end if
		}//end else

		//if it is not an animated Sprite
		if(!(this instanceof AnimatedSprite))
		{
			//if Sprite goes off the right of the screen
			if(y + image.getHeight() > screenh)
			{
				y = screenh - image.getHeight();  
			}//end if
		}//end if
		//else if is an Animated Sprite
		else
		{
			//if AnimatedSprite goes off the right of the screen
			if(y + ((AnimatedSprite)this).anim.getImage(0).getHeight() > screenh)
			{
				y = screenh - ((AnimatedSprite)this).anim.getImage(0).getHeight();
			}//end if
		}//end else

	}//end keepOnScreen

	public boolean isOffScreen(GameContainer gc)
	{
		int screenw = gc.getWidth();
		int screenh = gc.getHeight();

		//if Sprite goes off the left of the screen
		if(x + image.getWidth() < 0)
		{
			return true;
		}//end if
		//if Sprite goes off the top of the screen
		if(y + image.getHeight() < 0)
		{
			return true;
		}//end if
		
		//if Sprite goes off the right of the screen
		if(x > screenw)
		{
			return true;
		}//end if
		if(y  > screenh)
		{
			return true;  
		}//end if
		
		return false;

	}//end keepOnScreen

	//NOTE: make sure that "shooter" x and y are passed in for x2 and y2
	//compute angle between x1,y1 to x2, y2
	//make sure to "#define PI 3.1415926535" up top near include statements
	public double computeAngleBetweenPoints(float x1, float y1, float x2, float y2)
	{   
		double angle=0;
		double deltax = Math.abs (x1 - x2);
		double deltay = Math.abs (y1 - y2);
		if (x1 >= x2 && y1 >= y2)
		{
			angle = Math.atan(deltay / deltax) * 180 / Math.PI;
		}
		else if (x1 < x2 && y1 > y2)
		{
			angle = Math.atan(deltay / deltax) * 180 / Math.PI;
			angle = 180 - angle;
		}
		else if (x1 < x2 && y1 < y2)
		{
			angle = Math.atan(deltay / deltax) * 180 / Math.PI;
			angle = 180 + angle;
		}
		else if (x1 > x2 && y1 < y2)
		{
			angle = Math.atan(deltay / deltax) * 180 / Math.PI;
			angle = 360 - angle;
		}

		return angle-90;
	}//end computeAngleBetweenPoints


	void advancedChase(GameContainer gc, int delta, Sprite b)
	{

		double ang = computeAngleBetweenPoints(b.x, b.y, x, y);
		double counter=0;
		double clockwise=0;

		if(ang != image.getRotation())
		{

			if (ang < image.getRotation())
			{

				counter = image.getRotation() - ang;

			}
			else
			{

				counter = 360 - (ang - image.getRotation());

			}

			if (ang > image.getRotation())
			{

				clockwise = ang - image.getRotation();

			}
			else
			{

				clockwise = 360 - (image.getRotation() - ang);

			}

			if (clockwise <= counter)
			{

				image.setRotation(image.getRotation()+3);

				if (image.getRotation() > 360)
				{

					image.setRotation(1);

				}

			}
			else
			{

				image.setRotation(image.getRotation()-3);

				if (image.getRotation() < 0)
				{

					image.setRotation(359);

				}

			}

		}

		vx = (float) (calcAngleMoveX(image.getRotation()) * speed * delta);
		vy = (float) (calcAngleMoveY(image.getRotation()) * speed * delta);

		x += vx;
		y += vy;

	}//end advancedChase
	
	public void jump(int delta)
	{
		if(ORIGJUMPSPEED == 0)
			ORIGJUMPSPEED = jumpSpeed;
		
		if(!jumping)
			jumping = true;
	}//end jump
	
	public void updateJump(int delta, float gravity, Sprite map)
	{
		if(jumping)
		{
			float oldx = x;
			float oldy = y;
			y += jumpSpeed * delta; //move character in y-direction
			jumpSpeed += (gravity * delta)/40; //slow down jump speed
			if(this.spriteCollision(map))
			{
				jumping = false;
				x = oldx;
				y = oldy;
				jumpSpeed = ORIGJUMPSPEED;
			}//end if
		}//end if
	}//end updateJump

}//end  class