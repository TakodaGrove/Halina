import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TrainingScreen extends BasicGameState{

	private int stateID = -1;

	Random rand = new Random();

	AnimatedSprite runner;
	Sprite road1, road2;
	ArrayList<Sprite> car = new ArrayList<Sprite>(0);
	boolean start = true;
	float seconds = 0, minutes = 0;
	public static float fSecnd, fMnutes, sAdd;

	public TrainingScreen(int id) {
		stateID = id;
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		if(start == true){
			seconds = 0;
			minutes = 0;
			initCar(gc);
			runner.x = 50;
			runner.y = 50;
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		runner = new AnimatedSprite(new Image("res/miniRunner.png"), 43, 50, 25);
		runner.x = 50;
		runner.y = 50;
		runner.alive = true;

		road1 = new Sprite(new Image("res/road.png"));
		road1.x = 0;
		road1.alive = true;
		road2 = new Sprite(new Image("res/road2.png"));
		road2.x = road1.image.getWidth();
		road2.alive = true;

		initCar(gc);
	}//end init

	public void initCar(GameContainer gc) throws SlickException{
		Sprite oCar = new Sprite(new Image("res/orangeCar.png"));
		Sprite rCar = new Sprite(new Image("res/redCar.png"));
		Sprite gCar = new Sprite(new Image("res/greenCar.png"));
		Sprite yCar = new Sprite(new Image("res/yellowCar.png"));
		Sprite bCar = new Sprite(new Image("res/blueCar.png"));
		car.add(oCar);
		car.add(rCar);
		car.add(gCar);
		car.add(yCar);
		car.add(bCar);
		for(int i = 0; i < car.size(); i++){
			car.get(i).x = 1280 + rand.nextInt(5000);
			car.get(i).y = 500 - rand.nextInt(400);
			car.get(i).alive = true;
			car.get(i).speed = rand.nextFloat();
			if(car.get(i).speed <= 0.5f)
				car.get(i).speed += .4f;
			if(car.size()>5)
				car.remove(car.size()-1);
		}
	}//end initCar

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		road1.draw(g);
		road2.draw(g);
		runner.draw(g);
		renderCar(g);
		drawHUD(gc, g);
	}//end render

	public void renderCar(Graphics g){
		for(int i = 0; i < car.size(); i++)
			car.get(i).draw(g);
	}//end renderCar

	public void drawHUD(GameContainer gc, Graphics g){
		g.drawString("Time - " + (int)minutes + ":", 1070, 20);
		g.drawString(""+String.format("%02d",(int)seconds), 1155, 20);
	}//end drawHUD

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		Input input = gc.getInput();

		seconds += 0.001f * delta;//the timer for the race
		if((int)seconds>59){
			minutes++;
			seconds = 0;
		}

		movePlayer(gc,delta);
		updateCar(gc, sb, delta);
		moveRoad(delta);
		keepOnScreen(gc, runner);

		if(input.isKeyDown(Input.KEY_ESCAPE)){
			start = false;
			PauseScreen.screen = 2;
			sb.enterState(Main.PAUSESCREEN);
		}
	}//end update

	public void moveRoad(int delta) {

		road1.x -= .5*delta;
		road2.x -= .5*delta;

		if(road1.x+road1.image.getWidth() <= 0)
			road1.x = road2.x + road2.image.getWidth() - 1;

		if(road2.x+road2.image.getWidth() <= 0)
			road2.x = road1.x + road1.image.getWidth() - 1;
	}//end moveMap

	public void updateCar(GameContainer gc, StateBasedGame sb, int delta){
		for(int i = 0; i < car.size(); i++){
			car.get(i).x -= car.get(i).speed * delta;

			if(car.get(i).x+car.get(i).image.getWidth()<0){
				car.get(i).x = 1280 + rand.nextInt(5000);
				car.get(i).y = 500 - rand.nextInt(400);
				car.get(i).speed += 0.05f;
			}
			if(car.get(i).y+car.get(i).image.getHeight() > gc.getHeight())
				car.get(i).y -= 100;
			if(runner.spriteCollision(car.get(i))){
				start = true;
				fMnutes = minutes;
				fSecnd = seconds;
				seconds += minutes*60;
				sAdd = ((int)seconds % 10) * 0.005f;
				TrackScreen.oSpeed += sAdd;
				LevelScreen.stat = 2;
				sb.enterState(Main.LEVELSCREEN);
			}
		}//end for loop
	}//end updateCar

	public void movePlayer(GameContainer gc, int delta){
		Input input = gc.getInput();

		if(input.isKeyDown(Input.KEY_UP)){
			runner.y -= .4 *delta;
		}
		if(input.isKeyDown(Input.KEY_DOWN)){
			runner.y += .4 * delta;
		}
	}//end movePlayer
	
	public void keepOnScreen(GameContainer gc, Sprite s){
		if(s.y < 0)
			s.y = 0;
		if(s.y+s.image.getHeight() > gc.getHeight())
			s.y = gc.getHeight() - s.image.getHeight();
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
