import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TrackScreen extends BasicGameState {

	private int stateID = -1;
	
	//Sprites
	Sprite track, track2, blueS, whiteS, water;
	AnimatedSprite player;

	//timer code 
	//1000 = one second
	boolean readyHit = true;
	long delayHit = 1400, lastHit; //the last time you hit barrier
	boolean readyJump = true;
	long delayJump = 500, lastJump;//the last time you jumped
	boolean readyStart = true;
	long delayStart = 10000, lastStart;//the timer for the start sequence

	//gravity constant
	private static final float GRAVITY = 0.2f;

	//counts the barriers you've passed
	int barrier = 0;
	
	//if you pause the game, or enter a new level
	boolean keep = true, start = false;
	
	//variables for time
	float seconds = 0, minutes = 0, tempSpeed;
	public static float fTime, oSpeed = 1.0f;

	public TrackScreen(int id){
		stateID = id;
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		if(keep == false){
			//HUD variables
			seconds = 0;
			minutes = 0;
			barrier = 0;
			
			//all sprites
			player.x = 0;
			player.y = 400;
			player.speed = oSpeed;
			player.jumpSpeed = -1.5f;
			
			blueS.x = 5000;
			blueS.y = (gc.getHeight() - blueS.image.getHeight()) - 50;
			
			whiteS.x = 7500;
			whiteS.y = (gc.getHeight() - whiteS.image.getHeight()) - 50;
			
			water.x = 12510 + blueS.image.getWidth();
			water.y = gc.getHeight() - water.image.getHeight();
			
			track.x = 0;
			track.y = (gc.getHeight()-track.image.getHeight());

			track2.x = track.x+track.image.getWidth();
			track2.y = (gc.getHeight()-track2.image.getHeight());
		}
	}//end enter

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		player = new AnimatedSprite(new Image("res/runner.png"), 129, 150, 20);
		player.x = 0;
		player.y = 400;
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
		gc.setShowFPS(true);
		player.draw(g);
		track.draw(g);
		track2.draw(g);
		water.draw(g);
		blueS.draw(g);
		whiteS.draw(g);
		drawHUD(gc,g);
	}//end render
	
	public void drawHUD(GameContainer gc, Graphics g){
		g.drawString("Time - " + (int)minutes + ":", 1070, 20);
		g.drawString(""+String.format("%02d",(int)seconds), 1155, 20);
		g.drawString("Barrier - "+barrier, 1070, 40);
		//g.drawString(""+player.jumpSpeed, 1070, 60);
	}//end drawHUD

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		Input input = gc.getInput();
		
		seconds += 0.001f * delta;//the timer for the race
		if((int)seconds>59){
			minutes++;
			seconds = 0;
		}
		
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			PauseScreen.screen = 1;
			sb.enterState(Main.PAUSESCREEN);
		}
		
		if(barrier == 34 && blueS.x < 0){
			keep = false;
			fTime = (60 * minutes) + seconds;
			LevelScreen.stat = 1;
			if(HUBScreen.PR>=(int)fTime)
				HUBScreen.PR = (int)fTime;
			sb.enterState(Main.LEVELSCREEN);
		}

		keepOnScreen(gc,player);
		movePlayer(gc,delta,sb);
		moveMap();
		moveBarrier();
		moveWater();
		updatePlayer(gc,sb,delta,input);
	}//end update

	public void updatePlayer(GameContainer gc, StateBasedGame sb, int delta, Input input) throws SlickException {
		Input in = gc.getInput();
		//this code
		player.y+=GRAVITY*delta;

		if(player.x > 100)
			player.x = 100;

		//tells the sprite i am jumping
		if(in.isKeyPressed(Input.KEY_SPACE))
			player.jump(delta);

		//updates and actually jumps
		if(track2.x >= gc.getWidth())
			player.updateJump(delta, GRAVITY, track);
		else if(track.x > gc.getWidth())
			player.updateJump(delta, GRAVITY, track2);

		//this chunk of code slows player down if you hit the blue barrier
		if(player.spriteCollision(blueS))
			player.x = blueS.x + blueS.image.getWidth();

		if((player.spriteCollision(blueS)) && readyHit){
			player.speed -= .01f;
			readyHit = false;
			lastHit = System.currentTimeMillis();
		}

		if(lastHit + delayHit <= System.currentTimeMillis())
			readyHit = true;
		//end of timer for blue barrier

		//this chunk of code slows player down if you hit the white barrier
		if(player.spriteCollision(whiteS))
			player.x = whiteS.x + whiteS.image.getWidth();

		if((player.spriteCollision(whiteS)) && readyHit){
			player.speed -= .01f;
			readyHit = false;
			lastHit = System.currentTimeMillis();
		}

		if(lastHit + delayHit <= System.currentTimeMillis())
			readyHit = true;
		//end of timer for blue barrier

		tempSpeed = player.speed;
		//timer for waterJump
		if((player.x > water.x+5) && (player.x < (water.x+water.image.getWidth())) && (player.y >= 338) && readyJump){
			player.speed = (float)tempSpeed/2;
			readyJump = false;
			lastJump = System.currentTimeMillis();
		}

		if(lastJump + delayJump <= System.currentTimeMillis() && readyJump == false){
			readyJump = true;
			player.speed = tempSpeed*2;
		}
		//end timer for waterJump
	}//end updatePlayer

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
			track.x = track2.x + track2.image.getWidth() - 1;

		if(track2.x+track2.image.getWidth() <= 0)
			track2.x = track.x + track.image.getWidth() - 1;
	}//end moveMap

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
	}//endMoveBarrier

	public void moveWater() {
		if(water.x+water.image.getWidth() <= 0){
			if(barrier == 13 || barrier == 23 || barrier == 33){
				water.x = whiteS.x+10;
			}
			else if(barrier == 8 || barrier == 18 || barrier == 28){
				water.x = blueS.x+10;
			}
		}
	}//end moveWater

	public void keepOnScreen(GameContainer gc, Sprite s) {
		//left
		if(s.x < 0)
			s.x = 0;

		//right
		if(s.x + s.image.getWidth() > gc.getWidth())
			s.x = gc.getWidth() - s.image.getWidth();

		//keep above the track
		if(s.y + s.image.getHeight() > gc.getHeight() - track.image.getHeight())
			s.y = gc.getHeight() - track.image.getHeight() - s.image.getHeight();
	}//end keepOnScreen

	public void keepOnScreen(GameContainer gc, AnimatedSprite s) {
		//left
		if(s.x < 0)
			s.x = 0;

		//right
		if(s.x + s.anim.getImage(0).getWidth() > gc.getWidth())
			s.x = gc.getWidth() - s.anim.getImage(0).getWidth();

		//keep above the track
		if(s.spriteCollision(track) || s.spriteCollision(track2))
			s.y = gc.getHeight() - (s.image.getHeight() + track.image.getHeight());
	}//end keepOnScreen Animated Sprite

	@Override
	public int getID() {
		return stateID;
	}//end getID
}
