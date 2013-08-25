package com.newrog.myGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.newrog.entities.Logo;
import com.newrog.simplegdx.Sound;

public class IntroScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Logo logo;
	MyGdxGame game;
	Sound fastSound;

	public IntroScreen(MyGdxGame game) {
		this.game = game;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.setToOrtho(false);

		batch = new SpriteBatch();
		logo = new Logo(w / 2, h / 2);
		fastSound = new Sound("data/beep.wav");
	}

	public boolean iAmTired = true;

	public void update() {
		logo.update();

		if (logo.complete || Gdx.input.isKeyPressed(Keys.SPACE)) {
			if (iAmTired) {
				iAmTired = false;
				fastSound.play();
			}
			game.switchScreen(game.menuScreen);

		}

	}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		logo.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

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

		batch.dispose();
	}

}
