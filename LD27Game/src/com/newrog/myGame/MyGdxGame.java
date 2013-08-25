package com.newrog.myGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.newrog.simplegdx.Art;

public class MyGdxGame extends Game {// ApplicationListener {

	public Screen intro;
	public Screen gameScreen;
	public Screen endScreen;
	public Screen menuScreen;
	ShapeRenderer sr;

	@Override
	public void create() {
		Art.loadResources();
		sr = new ShapeRenderer();
		intro = new IntroScreen(this);
		gameScreen = new GameScreen(this);
		endScreen = new EndScreen(this);
		menuScreen = new MenuScreen(this);
		
		setScreen(intro);
	}

	float fade = 1;
	boolean fadeIn = false;
	boolean fadeOut = false;
	public boolean starting = true;
	public boolean peak = false;

	@Override
	public void render() {
		super.render();

		if (starting) {
			fade -= 0.05f;
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Filled);
			sr.setColor(0, 0, 0, fade);
			sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			if (fade <= 0.0) {
				starting = false;
				fade = 1.00f;
			}
		}
		
		
		
		if (peak) {
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Filled);
			sr.setColor(0, 0, 0, fade);
			sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			peak = false;
			setScreen(destScreen);
			
		}else if (fadeOut) {
			fade += 0.05f;
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Filled);
			sr.setColor(0, 0, 0, fade);
			sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			if (fade >= 1) {
				fadeOut = false;
				fadeIn = true;
				peak = true;
				fade = 1.0f;
			}
		} else if (fadeIn) {
			fade -= 0.05f;
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Filled);
			sr.setColor(0, 0, 0, fade);
			sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			sr.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			if (fade <= 0) {
				fadeOut = false;
				fadeIn = false;
				switching = true;
				peak = false;
				fade = 0;
			}
		} 
	}

	Screen destScreen;

	boolean switching = true;

	public void switchScreen(Screen screen) {
		if (switching) {
			
			switching = false;
			fadeOut = true;
			destScreen = screen;
			fade = 0;
			peak = false;
		}

		// fade out
		// switc
		// fade in

	}

}
