import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EndScreen extends BasicGameState{

	private int stateID = -1;

	public EndScreen(int id) {
		stateID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		gc.setShowFPS(false);
		g.drawString("EndScreen", 50, 50);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();

		if(input.isKeyPressed(Input.KEY_ENTER)){
			gc.exit();
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
