import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {
	
	public static final int STARTSCREEN = 0;
	public static final int TRACKSCREEN = 1;
	public static final int PAUSESCREEN = 2;
	public static final int ENDSCREEN = 3;
	public static final int LEVELSCREEN = 4;
	public static final int HUBSCREEN = 5;
	public static final int TRAININGSCREEN = 6;
	
	public Main(String name) {
		
		super(name);
		
		this.addState(new StartScreen(STARTSCREEN));
		this.addState(new TrackScreen(TRACKSCREEN));
		this.addState(new PauseScreen(PAUSESCREEN));
		this.addState(new EndScreen(ENDSCREEN));
		this.addState(new LevelScreen(LEVELSCREEN));
		this.addState(new HUBScreen(HUBSCREEN));
		this.addState(new TrainingScreen(TRAININGSCREEN));
		this.enterState(STARTSCREEN);
		
	}//end constructor main

	public static void main(String args[]) throws SlickException{
		AppGameContainer app = new AppGameContainer(new Main("Halina"));
		app.setDisplayMode(1200, 600, false);
		app.start();
	}//end main

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		this.getState(STARTSCREEN).init(gc, this);
		this.getState(TRACKSCREEN).init(gc, this);
		this.getState(PAUSESCREEN).init(gc, this);
		this.getState(ENDSCREEN).init(gc, this);
		this.getState(LEVELSCREEN).init(gc, this);
		this.getState(HUBSCREEN).init(gc, this);
		this.getState(TRAININGSCREEN).init(gc, this);
		
	}//end initlist
}//end class
