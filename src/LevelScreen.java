import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LevelScreen extends BasicGameState{

	private int stateID = -1;
	public static int stat = 0;

	public LevelScreen(int id) {
		stateID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		gc.setShowFPS(false);
		if(stat == 1){
			g.drawString("Your final time was - " +(int)TrackScreen.fTime/60 + ":" + String.format("%02d",(int)TrackScreen.fTime%60),50, 100);
		}
		else if(stat == 2){
			g.drawString("You lasted - " + (int)TrainingScreen.fMnutes + ":" + String.format("%02d",(int)TrainingScreen.fSecnd), 50, 100);
			g.drawString("You also added - " + TrainingScreen.sAdd + " to your speed.", 50, 150);
		}
	}//end render

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();

		if(input.isKeyPressed(Input.KEY_ENTER)){
			stat = 0;
			sb.enterState(Main.HUBSCREEN);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
