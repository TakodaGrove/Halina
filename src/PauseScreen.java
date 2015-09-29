import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseScreen extends BasicGameState{

	private int stateID = -1;
	
	public static int screen = 0;

	public PauseScreen(int id) {
		stateID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		gc.setShowFPS(false);
		g.drawString("PauseScreen", 50, 50);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();

		if(input.isKeyDown(Input.KEY_ENTER)){
			if(screen == 1){
				screen = 0;
				sb.enterState(Main.TRACKSCREEN);
			}
			else if(screen == 2){
				screen = 0;
				sb.enterState(Main.TRAININGSCREEN);
			}
			else if(screen == 3){
				screen = 0;
				sb.enterState(Main.HUBSCREEN);
			}
		}
		else if(input.isKeyDown(Input.KEY_Q))
			sb.enterState(Main.ENDSCREEN);
	}//end update

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
