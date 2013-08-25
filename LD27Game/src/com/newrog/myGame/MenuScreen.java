package com.newrog.myGame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Scaling;
import com.newrog.entities.Heart;
import com.newrog.simplegdx.Art;
import com.newrog.simplegdx.CurrentBatch;
import com.newrog.simplegdx.Sound;
import com.newrog.simplegdx.Art.Hearts;

public class MenuScreen implements Screen{
	private OrthographicCamera camera;
	Stage stage;
	Sprite logo;
	Sprite player;
	private MyGdxGame myGdxGame;
	private SpriteBatch batch;
	private int w;
	private int h;
	private Label pressStartLabel;
	private float floatingLabel = 120;
	private float degrees=0;
	private float floatingPlayer;
	private float playerDegrees;
	public ArrayList<Heart> hurts = new ArrayList<Heart>();
	Animation ringEffects;
	float stateTime = 0;
	Sound fastSound;
	
	public MenuScreen(MyGdxGame myGdxGame){
		this.myGdxGame = myGdxGame;
		batch = new SpriteBatch();
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.setToOrtho(false, w * .50f, h * .50f); // half height? maybe?
		
		logo = new Sprite(Art.mainMenuLogo);
		stage = new Stage();
		stage.setCamera(camera);

		LabelStyle ls = new LabelStyle(Art.hieroHelv, Color.WHITE);
		pressStartLabel = new Label("Press Spacebar to Continue", ls);
		pressStartLabel.setFontScale(.5f);
		pressStartLabel.setPosition(w / 4 - pressStartLabel.getPrefWidth() / 2, floatingLabel );

		stage.addActor(pressStartLabel);
		
		player = new Sprite(Art.playerTextureRegion);
		player.setPosition(50, 60);
		
		Heart h = new Heart(Hearts.EMPTY);
		h.init(350, 30, true);
		h.deltaY = 10;
		hurts.add(h);
		fastSound = new Sound("data/beep.wav");
		ringEffects = new Animation(0.06f, Art.rings);
	}
	Random r = new Random();
	
	int tick = 0;
	@Override
	public void render(float delta) {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(logo, 0, 0);
		
		for(Heart h: hurts){
			h.position.x -=1.5f;
			h.act(delta);
			h.draw(batch, 1);
		}
		for(int i = 0; i < hurts.size(); i++){
			if(hurts.get(i).getX()<-20){
				hurts.remove(i);
			}
		}
		
		if(r.nextFloat() < .03f){
			Heart h = new Heart(Hearts.EMPTY);
			h.init(450, 10+r.nextInt(60), true);
			h.deltaY = 6+r.nextInt(3);
			hurts.add(h);
		}
		if(r.nextFloat() < .0005f){
			Heart h = new Heart(Hearts.FULL);
			h.init(450, 10+r.nextInt(60), true);
			h.deltaY = 6+r.nextInt(3);
			hurts.add(h);
		}
		
		if (ringEffects.isAnimationFinished(stateTime)) {
			stateTime = 0;
			ringEffects.setPlayMode(Animation.NORMAL);

		}
		int a = ringEffects.getKeyFrameIndex(stateTime);
		if (ringEffects.isAnimationFinished(stateTime)) {
			stateTime = 0;
			ringEffects.setPlayMode(Animation.NORMAL);

		}
		player.setRotation(-10);
		batch.setColor(1, 1, 1, 1 - a / 7f);
		
		
		Texture t = ringEffects.getKeyFrame(stateTime, false).getTexture();
		//batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
		
		batch.draw(ringEffects.getKeyFrame(stateTime, false),
				(float) (player.getX() + 15 * MathUtils.sinDeg(player.getRotation())), 
				(float)(player.getY() - a),
				2f, 
				8f, 
				20, 
				8, 
				1f, 
				1f,
				-15f);
		//batch.draw(ringEffects.getKeyFrame(stateTime, false), (float) (player.getX() + 15 * MathUtils.sinDeg(player.getRotation())),
		//		player.getY() - a);
		batch.setColor(1, 1, 1, 1f);
		
		
		
		playerDegrees +=5f;
		floatingPlayer = 60+5*MathUtils.sinDeg(playerDegrees);
		player.setX(80+10*MathUtils.cosDeg(playerDegrees*0.4f));
	
		player.setY(floatingPlayer);
		batch.draw(player, player.getX(), player.getY(), player.getOriginX(),player.getOriginY(),player.getWidth(),player.getHeight(), player.getScaleX(),player.getScaleY(), player.getRotation());
		batch.end();
		degrees+=6.5f;
		floatingLabel = 120+2*MathUtils.sinDeg(degrees);
		pressStartLabel.setPosition(w / 4 - pressStartLabel.getPrefWidth() / 2, floatingLabel );
		stage.act();
		stage.draw();
		
		tick++;
		if(Gdx.input.isKeyPressed(Keys.SPACE) && !pressed && tick > 30){
			myGdxGame.switchScreen(myGdxGame.gameScreen);
			pressed = true;
			fastSound.play();
		}
		
		
	}
		
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
		// TODO Auto-generated method stub
		
	}

}
