package com.newrog.myGame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Scaling;
import com.newrog.entities.Player;
import com.newrog.simplegdx.Art;
import com.newrog.simplegdx.CurrentBatch;
import com.newrog.simplegdx.internal.Files;

public class EndScreen implements Screen {
	MyGdxGame game;
	
	private OrthographicCamera camera;
	Stage stage;
	Player player;
	private MyGdxGame myGdxGame;
	private SpriteBatch batch;
	private int w;
	private int h;
	private Label pressStartLabel;
	private Label theEndLabel;
	private float floatingLabel = 120;
	private float degrees=0;
	private float floatingPlayer;
	private float playerDegrees;
	public ArrayList<Sprite> clouds = new ArrayList<Sprite>();
	Music song;
	
	public EndScreen(MyGdxGame myGdxGame) {
		song = Gdx.audio.newMusic(Files.get("data/credits.ogg"));
		this.myGdxGame = myGdxGame;
		batch = new SpriteBatch();
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.setToOrtho(false, w, h); // half height? maybe?
		
		stage = new Stage();
		stage.setCamera(camera);

		LabelStyle ls = new LabelStyle(Art.hieroHelv, Color.WHITE);
		pressStartLabel = new Label("    The Lonely Sentry\nhas enough love fuel to \n       find the others", ls);
		pressStartLabel.setFontScale(0.75f);
		pressStartLabel.setPosition(w / 2 - pressStartLabel.getPrefWidth(), floatingLabel );

		stage.addActor(pressStartLabel);
		theEndLabel = new Label("The End", ls);
		theEndLabel.setFontScale(1f);
		theEndLabel.setPosition(w / 2 - theEndLabel.getPrefWidth() / 2, 500 );
		
		stage.addActor(theEndLabel);
		
		
		player = new Player();//(Art.playerTextureRegion);
		player.position.x = 350;
		player.position.y = 300;
		player.endGame = true;
		Sprite sp = new Sprite(Art.cloud1);
		sp.setPosition(200, 200);
		clouds.add(sp);
		CurrentBatch.set(batch);
		
	}
	
	public Vector2 center = new Vector2(350,300);
	public float xWalk = 0;
	public float yWalk = 0;
	
	
	Random r = new Random();
	
	@Override
	public void render(float delta) {
		
		if(!song.isPlaying()){
			song.play();
		}
		CurrentBatch.set(batch);
		Gdx.gl.glClearColor(135/255f,206/255f, 250/255f, 0.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	//	batch.draw(logo, 0, 0);
		
		//for(Sprite sp: clouds){
			//h.position.x -=1.5f;
			//h.act(delta);
			//batch.draw(sp, 200, 200);
			//h.draw(batch, 1);
		//}
		
		/*if(r.nextFloat() < .03f){
			Heart h = new Heart(Hearts.EMPTY);
			h.init(450, 10+r.nextInt(60), true);
			h.deltaY = 6+r.nextInt(3);
			hurts.add(h);
		}*/
		if(r.nextFloat() < .05f){
			Sprite sp;
			if(r.nextBoolean()){
				sp = new Sprite(Art.cloud1);	
			}else{
				sp = new Sprite(Art.cloud2);
			}
			 
			sp.setPosition(r.nextInt(1200), 700);
			clouds.add(sp);
		}
		
		
		
		playerDegrees +=5f;
		floatingPlayer = 60+5*MathUtils.sinDeg(playerDegrees);
		//player.setX(80+10*MathUtils.cosDeg(playerDegrees*0.4f));
		//player.setRotation(-10);
		//player.setY(floatingPlayer);
		
		//player.acceleration.x = -5;
		//player.rotation = -5;
		for(Sprite sp: clouds){
			sp.setY(sp.getY()-4);
			sp.setX(sp.getX()-1);
			sp.draw(batch);
						
		}
		for(int i = 0; i < clouds.size();i++){
			if(clouds.get(i).getY()<-10){
				clouds.remove(i);
			}
		}
		
		batch.end();
		
		
		tick++;
		if(tick%200==0){
			xWalk = r.nextFloat()-.5f;
			yWalk = r.nextFloat()-.5f;
			if(player.position.y < center.y && yWalk < 0){
				yWalk*=-1;
			}else if(player.position.y > center.y && yWalk > 0){
				yWalk*=-1;
			}
			if(player.position.x < center.x && xWalk < 0){
				xWalk*=-1;
			}else if(player.position.x > center.x && xWalk > 0){
				xWalk*=-1;
			}
			xWalk *=4;
			yWalk *=4;
		}
		dx+= xWalk*.5f;
		dy+= yWalk*.5f;
		
		player.position.x = center.x + dx;
		player.position.y = center.y + dy;
		//player.position.x = MathUtils.clamp(player.position.x, center.x-100, center.x+100);
		//player.position.y  = MathUtils.clamp(player.position.y , center.y -100, center.y +100);
		player.update();
		
		camera.rotate(-7);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.render();
		batch.end();
		camera.rotate(7);
		camera.update();
		//camera.rotate(-15);
		//batch.draw(player, player.getX(), player.getY(), player.getOriginX(),player.getOriginY(),player.getWidth(),player.getHeight(), player.getScaleX(),player.getScaleY(), player.getRotation());
		
		degrees+=6.5f;
		floatingLabel = 110+5*MathUtils.sinDeg(degrees);
		pressStartLabel.setPosition(w / 2 - pressStartLabel.getPrefWidth() / 2, floatingLabel );
		stage.act();
		stage.draw();
		
		//if(Gdx.input.isKeyPressed(Keys.SPACE) && !pressed){
		//	myGdxGame.switchScreen(myGdxGame.gameScreen);
		//	pressed = true;
		//}
		
	}
	float rot = 0;
	float dx =0; float dy=0;
	
	
	int tick = 0;
	
	boolean pressed = false;
	

	@Override
	public void resize(int width, int height) {
		Vector2 size = Scaling.fit.apply(800, 600, width, height);
		int viewportX = (int) (width - size.x) / 2;
		int viewportY = (int) (height - size.y) / 2;
		int viewportWidth = (int) size.x;
		int viewportHeight = (int) size.y;
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
	}

	@Override
	public void show() {
		((GameScreen)myGdxGame.gameScreen).song.stop();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

		batch.dispose();
	}



}
