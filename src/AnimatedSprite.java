import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class AnimatedSprite extends Sprite{

	Animation anim;
	
	public AnimatedSprite(Image i) {
		super(i);
	}

	/*
	 * i = the animated sprite sheet
	 * w = the width of a single frame of animation
	 * h = the height of a single frame of animation
	 * duration = how long between frames
	 */
	
	public AnimatedSprite(Image i, int w, int h, int duration)
	{
		super(i);
		SpriteSheet ss = new SpriteSheet(i, w, h);
		anim = new Animation(ss, duration);
	}//end constructor
	
	public void draw(Graphics g)
	{
		if(alive)
		{
			//anim.stopAt(6);
			Image fr = anim.getCurrentFrame();
			if(anim.getFrame() != anim.getFrameCount()-1)
			{
				Image fr2 = anim.getImage(anim.getFrame()+1);
				fr2.setRotation(angle);
			}
			else
			{
				Image fr2 = anim.getImage(0);
				fr2.setRotation(angle);
			}
			fr.setRotation(angle);
			anim.draw(x,y);
		}//end if
	}//end draw
	
	public boolean spriteCollision(Sprite other)
    {
        float left1, left2;
        float right1, right2;
        float top1, top2;
        float bottom1, bottom2;

        left1 = x;
        left2 = other.x;
        right1 = x + anim.getImage(0).getWidth();
        right2 = other.x + other.image.getWidth();
        top1 = y;
        top2 = other.y;
        bottom1 = y + anim.getImage(0).getHeight();
        bottom2 = other.y + other.image.getHeight();

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
        right1 = x + anim.getImage(0).getWidth();
        right2 = other.x + other.anim.getImage(0).getWidth();
        top1 = y;
        top2 = other.y;
        bottom1 = y + anim.getImage(0).getHeight();
        bottom2 = other.y + other.anim.getImage(0).getHeight();

        if (bottom1 < top2) return false; //no collision
        if (top1 > bottom2) return false; //no collision

        if (right1 < left2) return false; //no collision
        if (left1 > right2) return false; //no collision

        return true; //YES, collision

    }//end spriteCollision
	
}//end class AnimatedSprite













