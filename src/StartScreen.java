import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartScreen extends BasicGameState{
	
	private int stateID = -1;
	Sprite start;
	Sprite quit;
	
	Image background;

	public StartScreen(int id) {
		stateID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		// TODO Auto-generated method stub
		
		background = new Image("res/StartScreen.png");
		
		start = new Sprite(new Image("res/StartButton.png"));
		start.x = 100;
		start.y = 100;
		start.alive = true;
		
		quit = new Sprite(new Image("res/QuitButton.png"));
		quit.x = 100;
		quit.y = 200;
		quit.alive = true;
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		gc.setShowFPS(false);
		background.draw(0,0);
		
		Input input = gc.getInput();

		if(start.spriteCollisionByPoint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()))
		{
			
			g.drawImage(start.image, start.x, start.y, Color.yellow);
			
		}
		else
		{
			
			g.drawImage(start.image, start.x, start.y, Color.white);
			
		}
		
		if(quit.spriteCollisionByPoint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()))
		{
			
			g.drawImage(quit.image, quit.x, quit.y, Color.yellow);
			
		}
		else
		{
			
			g.drawImage(quit.image, quit.x, quit.y, Color.white);
			
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		
		if(input.isMouseButtonDown(0) && start.spriteCollisionByPoint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()))
			sb.enterState(Main.HUBSCREEN);
		
		if(input.isMouseButtonDown(0) && quit.spriteCollisionByPoint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()))
			gc.exit();
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
