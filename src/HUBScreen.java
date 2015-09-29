import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HUBScreen extends BasicGameState{

	private int stateID = -1;
	
	Sprite shoes, bib;
	
	Image background;
	
	public static int PR=9999;

	public HUBScreen(int id) {
		stateID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		background = new Image("res/hubRoom.png");
		
		shoes = new Sprite(new Image("res/hangingShoes.png"));
		shoes.x = 150;
		shoes.y = 150;
		shoes.alive = true;
		
		bib = new Sprite(new Image("res/bib.png"));
		bib.x = 350;
		bib.y = 65;
		bib.alive = true;
	}//end init

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		Input input = gc.getInput();
		
		gc.setShowFPS(false);
		background.draw(0,0);
		
		if(shoes.spriteCollisionByPoint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()))
			g.drawImage(shoes.image, shoes.x, shoes.y, Color.blue);
		else
			shoes.draw(g);
		if(bib.spriteCollisionByPoint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()))
			g.drawImage(bib.image, bib.x, bib.y, Color.red);
		else
			bib.draw(g);
		
		g.setColor(Color.black);
		g.drawString("PR - "+PR/60 + ":" + String.format("%02d",PR%60), 230, 75);
		g.setColor(Color.white);
	}//end render

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isMouseButtonDown(0) && bib.spriteCollisionByPoint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()))
			sb.enterState(Main.TRACKSCREEN);
		if(input.isMouseButtonDown(0) && shoes.spriteCollisionByPoint(input.getAbsoluteMouseX(), input.getAbsoluteMouseY()))
			sb.enterState(Main.TRAININGSCREEN);
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			PauseScreen.screen = 3;
			sb.enterState(Main.PAUSESCREEN);
		}
	}//end update

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
