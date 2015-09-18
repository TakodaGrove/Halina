import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TrackScreen extends BasicGameState {

	private int stateID = -1;
	//Sprite player;
	Sprite track;
	Sprite track2;
	Sprite blueS;
	Sprite whiteS;
	Sprite water;
	AnimatedSprite player;

	//timer code
	boolean ready = true;
	long delay = 1400; //1000 = one second
	long lastHit; //the time of the last shot

	//timer code
	boolean ready2 = true;
	long delay2 = 500;
	long lastJump; //the time of the last shot

	public static final float GRAVITY = 0.2f;

	float temp;//just a temp variable...currently being used to store player speed

	int barrier=0;

	public TrackScreen(int id){
		stateID = id;
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		player.x = 0;
		player.y = 400;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		player = new AnimatedSprite(new Image("res/GameWalker.png"), 104, 155 , 50);
		player.x = 0;
		player.y = 0;
		player.alive = true;
		player.speed = 1.0f;
		player.jumpSpeed = -1.5f;

		blueS = new Sprite(new Image("res/blueSteeple.png"));
		blueS.x = 5000;
		blueS.y = (gc.getHeight() - blueS.image.getHeight()) - 50;
		blueS.alive = true;

		whiteS = new Sprite(new Image("res/whiteSteeple.png"));
		whiteS.x = 7500;
		whiteS.y = (gc.getHeight() - whiteS.image.getHeight()) - 50;
		whiteS.alive = true;

		water = new Sprite(new Image("res/Water.png"));
		water.x = 12510 + blueS.image.getWidth();
		water.y = gc.getHeight() - water.image.getHeight();
		water.alive = true;

		track = new Sprite(new Image("res/Track.png"));
		track.x = 0;
		track.y = (gc.getHeight()-track.image.getHeight());
		track.alive = true;

		track2 = new Sprite(new Image("res/TrackTwo.png"));
		track2.x = track.x+track.image.getWidth();
		track2.y = (gc.getHeight()-track2.image.getHeight());
		track2.alive = true;
	}//end init

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		g.setBackground(Color.orange);
		player.draw(g);
		track.draw(g);
		track2.draw(g);
		water.draw(g);
		blueS.draw(g);
		whiteS.draw(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE))
			sb.enterState(Main.PAUSESCREEN);

		keepOnScreen(gc,player);
		movePlayer(gc,delta,sb);
		moveMap();
		moveBarrier();
		moveWater();
		updatePlayer(gc,sb,delta,input);
	}

	public void updatePlayer(GameContainer gc, StateBasedGame sb, int delta, Input input) throws SlickException {
		Input in = gc.getInput();
		//this code
		player.y+=GRAVITY*delta;

		if(player.x > 100)
			player.x = 100;

		//tells the sprite i am jumping
		if(in.isKeyDown(Input.KEY_SPACE))
			player.jump(delta);

		//updates and actually jumps
		if(track2.x > gc.getWidth())
			player.updateJump(delta, GRAVITY, track);
		else if(track.x > gc.getWidth())
			player.updateJump(delta, GRAVITY, track2);

		//this chunk of code slows player down if you hit the blue barrier
		if(player.spriteCollision(blueS))
			player.x = blueS.x + blueS.image.getWidth();

		if((player.spriteCollision(blueS)) && ready){
			player.speed -= .01f;
			ready = false;
			lastHit = System.currentTimeMillis();
		}

		if(lastHit + delay <= System.currentTimeMillis())
			ready = true;
		//end of timer for blue barrier

		//this chunk of code slows player down if you hit the white barrier
		if(player.spriteCollision(whiteS))
			player.x = whiteS.x + whiteS.image.getWidth();

		if((player.spriteCollision(whiteS)) && ready){
			player.speed -= .01f;
			ready = false;
			lastHit = System.currentTimeMillis();
		}

		if(lastHit + delay <= System.currentTimeMillis())
			ready = true;
		//end of timer for blue barrier

		temp = player.speed;
		//timer for waterJump
		if((player.x > water.x+5) && (player.x < (water.x+water.image.getWidth())) && (player.y >= 338) && ready2){
			player.speed = (float)temp/2;
			ready2 = false;
			lastJump = System.currentTimeMillis();
		}

		if(lastJump + delay2 <= System.currentTimeMillis() && ready2 == false){
			ready2 = true;
			player.speed = temp*2;
		}
		//end timer for waterJump
	}

	public void movePlayer(GameContainer gc, int delta, StateBasedGame sb) {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_RIGHT)){
			track.x -= player.speed * delta;
			track2.x -= player.speed * delta;
			blueS.x -= player.speed * delta;
			whiteS.x -= player.speed * delta;
			water.x -= player.speed * delta;
			player.x += player.speed * delta;
		}

		if(input.isKeyDown(Input.KEY_LEFT)){
			player.x -= player.speed * delta;
		}

	}//end movePlayer

	public void moveMap() {
		if(track.x+track.image.getWidth() <= 0)
			track.x = track2.x + track2.image.getWidth();

		if(track2.x+track2.image.getWidth() <= 0)
			track2.x = track.x + track.image.getWidth();
	}

	public void moveBarrier() {
		if(blueS.x+blueS.image.getWidth() <= 0){
			blueS.x = 5000;
			barrier++;
		}
		else if(whiteS.x+whiteS.image.getWidth() <= 0){
			whiteS.x = 5000;
			barrier++;
		}

		if(barrier == 35){
			whiteS.alive = false;
			blueS.alive = false;
		}
	}

	public void moveWater() {
		if(water.x+water.image.getWidth() <= 0){
			if(barrier == 13 || barrier == 23 || barrier == 33){
				water.x = whiteS.x+10;
			}
			else if(barrier == 8 || barrier == 18 || barrier == 28){
				water.x = blueS.x+10;
			}
		}
	}

	public void keepOnScreen(GameContainer gc, Sprite s) {
		//left
		if(s.x < 0)
			s.x = 0;

		//right
		if(s.x + s.image.getWidth() > gc.getWidth())
			s.x = gc.getWidth() - s.image.getWidth();

		//keep above the track
		if(s.y + s.image.getHeight() > gc.getHeight() - 69)
			s.y = gc.getHeight() - s.image.getHeight() - 69;
	}//end keepOnScreen

	public void keepOnScreen(GameContainer gc, AnimatedSprite s) {
		//left
		if(s.x < 0)
			s.x = 0;

		//right
		if(s.x + s.image.getWidth() > gc.getWidth())
			s.x = gc.getWidth() - s.image.getWidth();

		//keep above the track
		if(s.y + s.image.getHeight() > gc.getHeight() - 69)
			s.y = gc.getHeight() - s.image.getHeight() - 69;

	}

	@Override
	public int getID() {
		return stateID;
	}
}
