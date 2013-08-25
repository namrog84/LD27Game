package com.newrog.myGame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.newrog.entities.Entity;
import com.newrog.entities.Heart;
import com.newrog.entities.Player;
import com.newrog.simplegdx.Art;
import com.newrog.simplegdx.CurrentBatch;
import com.newrog.simplegdx.Image;
import com.newrog.simplegdx.Sound;
import com.newrog.simplegdx.internal.Files;

public class GameScreen implements Screen {
	public MyGdxGame myGdxGame;
	private OrthographicCamera camera;
	private OrthographicCamera guiCamera;
	private SpriteBatch batch;
	Player player;
	Level level;
	Stage stage;
	Label fuelLabel;
	Label resetLabel;

	Label levelHintLabel;
	float w;
	float h;
	Sound heartSound;
	public Music song;

	public ArrayList<Heart> hurts = new ArrayList<Heart>();
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	float stateTime;

	Image finalHeart;
	
	public GameScreen(MyGdxGame myGdxGame) {
		this.myGdxGame = myGdxGame;
		batch = new SpriteBatch();
		CurrentBatch.set(batch);
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.setToOrtho(false, w * .50f, h * .50f); // half height? maybe?

		guiCamera = new OrthographicCamera();
		guiCamera.setToOrtho(false, w, h);
		// guiCamera.translate(-w/2,-h/2);
		guiCamera.update();
		heartSound = new Sound("data/powerup.wav");
		song = Gdx.audio.newMusic(Files.get("data/game.mp3"));
		finalHeart = new Image("data/original/bigHeart.png");
		resetLevel();
	}
	
	public void resetLevel() {
		entities.clear();
		hurts.clear();
		stateTime = 0;

		stage = new Stage();
		stage.setCamera(guiCamera);
		player = new Player(this);

		LabelStyle ls = new LabelStyle(Art.hieroHelv, Color.WHITE);

		fuelLabel = new Label("Fuel Remaining: " + player.fuel + " seconds", ls);
		fuelLabel.setFontScale(1f);

		fuelLabel.setPosition(w / 2 - fuelLabel.getPrefWidth() / 2, 0);

		// resetLabel
		resetLabel = new Label("Press R to restart level", ls);
		resetLabel.setFontScale(1.5f);
		resetLabel.setPosition(w / 2 - resetLabel.getPrefWidth() / 2, 400);

		/*
		 * hh = new Heart(Art.Hearts.EMPTY); hh.init(w - 60, h - 60, false); hh.setScale(4); stage.addActor(hh);
		 */

		// Gdx.input
		stage.addActor(fuelLabel);
		stage.addActor(resetLabel);

		level = new Level(this, currentLevel);

		makeHint();
		RectangleMapObject mo = (RectangleMapObject) level.map.getLayers().get("objLayer1").getObjects().get("playerStart");
		if (mo == null)
			System.out.println("ERROR");

		player.position.x = mo.getRectangle().getX();
		player.position.y = mo.getRectangle().getY();
		player.init(level);

		Heart hh = new Heart(Art.Hearts.EMPTY);
		hh.init(player.position.x, player.position.y);
		hurts.add(hh);

		CurrentBatch.set(batch);
		level.tileMapRenderer.setView(camera);

		if (currentLevel == 0) {
			player.fuel = 0;
		}
	}

	
	// //////////////////////////////////////////
		// // CURRENT MAXIMUM LEVELS CREATED!//////////////////////
	public int currentLevel = 0;
		public int totalLevels = 10;
		// ////////////////////////////////////////////
	private void makeHint() {
		LabelStyle ls = new LabelStyle(Art.hieroHelv, Color.WHITE);
		
		switch (currentLevel) {
		case 0:
			levelHintLabel = new Label("Collect Hearts for Fuel", ls);
			levelHintLabel.setFontScale(.8f);
			levelHintLabel.setPosition(3*32, 6*32);
			break;
		case 1:
			levelHintLabel = new Label("Press Up or W to fly!!", ls);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(3*32, 5*32);
			break;
			
		case 2:
			levelHintLabel = new Label("Watch out for the Spikes!", ls);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(3*32, 5*32);
			break;
		case 3:
			levelHintLabel = new Label("Fly Up!\nA heart only\ngives you\n10 Seconds\nof fuel", ls);
			levelHintLabel.setAlignment(Align.right);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(5*32+16, 4*32);
			break;
		case 4:
			levelHintLabel = new Label("Watch Out\nBelow!!", ls);
			levelHintLabel.setAlignment(Align.left);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(3*32+16, 33*32);
			break;
			
		case 5:
			levelHintLabel = new Label("It is once thought the Hearts were made from love of internet cats!", ls);
			levelHintLabel.setAlignment(Align.left);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(3*32, 17*32);
			break;
			
			
		case 6:
			levelHintLabel = new Label("We must find the Big Heart!!", ls);
			levelHintLabel.setAlignment(Align.left);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(5*32, 37*32);
			break;
		

		case 7:
			levelHintLabel = new Label("Big Heart == Infinite Love Fuel!!", ls);
			levelHintLabel.setAlignment(Align.left);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(5*32, 15*32);
			break;
		

			
		case 8:
			levelHintLabel = new Label("You are close!!!", ls);
			levelHintLabel.setAlignment(Align.left);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(3*32, 47*32);
			break;
		

		case 9:
			levelHintLabel = new Label("This is the wrong way!!  Go back now!!! Neurotoxins ahead!!!!", ls);
			levelHintLabel.setAlignment(Align.left);
			levelHintLabel.setFontScale(.5f);
			levelHintLabel.setPosition(3*32, 35*32);
			break;
		
			
			
			
			
			
			
			
		default:
			levelHintLabel = new Label("Error Loading Hint", ls);
			levelHintLabel.setFontScale(.3f);
			levelHintLabel.setPosition(10, 10);
			break;

		}
	}



	private void update() {

		if(!song.isPlaying()){
			song.setLooping(true);
			song.play();
		}
		
		if (myGdxGame.peak) {
			currentLevel++;

			if (currentLevel >= totalLevels) {
				// System.out.println("SWISAHDFIOSADF");
				// myGdxGame.switchScreen(myGdxGame.endScreen);
			} else {
				resetLevel();
				transitioning = true;
			}

			// myGdxGame.peak = false;
		
		}
		
		
		
		if (!player.alive) {
			resetLabel.setVisible(true);
		}

		if (Gdx.input.isKeyPressed(Keys.R)) {
			resetLevel();
		}
		player.update();

		for (Entity e : entities) {
			e.update();
		}
		String foo = formatValue(player.fuel);// String.valueOf(fuel);// String.format("%.2f", fuel);

		fuelLabel.setColor(1, (player.fuel / 10), (player.fuel / 10), 1);
		fuelLabel.setText("Fuel Remaining: " + foo + " seconds");

		camera.position.x = Math.round(player.position.x);
		camera.position.y = Math.round(player.position.y);

		// System.out.println(camera.position.x + " " + camera.viewportWidth);
		camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, level.tileSize * level.height - camera.viewportHeight / 2);
		camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, level.tileSize * level.width - camera.viewportWidth / 2);

		camera.update();

		stage.act();
	}

	@Override
	public void render(float delta) {
		CurrentBatch.set(batch);
		update();
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		level.tileMapRenderer.setView(camera);
		level.render();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// ////////////////////////////////////////////////
		for (Heart ha : hurts) {
			ha.act(delta);
			ha.draw(batch, 1);
		}
		// ////////////////////////////////////////////////////
		for (Entity e : entities) {
			e.render();
		}
		// resetLabel.setVisible(true);
		// resetLabel.draw(batch, 1);
		levelHintLabel.draw(batch, 1);
		if(currentLevel==9){
		finalHeart.draw(12*32, 3*32+hurts.get(0).sprite.getY(), 200,-200);
		}
		
		player.render();

		batch.end();

		stateTime += delta;
		stage.draw();
	}

	public String formatValue(double a) {
		String t = String.valueOf(a);
		if (t.contains(".")) {
			String t2 = String.valueOf((int) a);
			if (t2.length() + 3 < t.length()) {
				return t.substring(0, t2.length() + 2);
			} else if (t2.length() + 2 < t.length()) {
				return t.substring(0, t2.length() + 1);
			}
		}
		return String.valueOf((int) a);
	}

	@Override
	public void resize(int width, int height) {
		Vector2 size = Scaling.fit.apply(800, 600, width, height);
		int viewportX = (int) (width - size.x) / 2;
		int viewportY = (int) (height - size.y) / 2;
		int viewportWidth = (int) size.x;
		int viewportHeight = (int) size.y;
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);

		// camera.viewportHeight = 500;
		// camera.visetViewport(800, 480, true);
		// stage.getCamera().translate(-stage.getGutterWidth(), -stage.getGutterHeight(), 0);
		// camera.viewportWidth = width;
		// camera.viewportHeight = height;
		// camera.setToOrtho(false, width,height/2);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
	//	song.stop();
	}

	@Override
	public void pause() {
	//	song.stop();
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	boolean transitioning = true;

	public void nextLevel() {
		if (transitioning) {
			heartSound.play();
			if ((currentLevel + 1) >= totalLevels) {
				myGdxGame.switchScreen(myGdxGame.endScreen);
			} else if (myGdxGame.getScreen() == this && (currentLevel + 1) < totalLevels) {
				myGdxGame.switchScreen(this);
			}
			transitioning = false;
		}

	}

}
