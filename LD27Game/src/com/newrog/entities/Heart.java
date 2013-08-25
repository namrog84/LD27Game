package com.newrog.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.newrog.simplegdx.Art;

public class Heart extends Actor {

	public Sprite sprite;
	Random r = new Random();
	public Vector2 position = new Vector2(0, 0);
	boolean floating = false;
	int tick = 0;
	public float deltaY= 4;
	
	public Heart(Art.Hearts h) {
		sprite = new Sprite(Art.hearts[h.v]);
		sprite.setScale(2);

	}

	public void init(float x, float y) {
		init(x, y, false);
	}


	public void init(float x, float y, boolean floating) {

		position.x = x;
		position.y = y;
		this.floating = floating;
		tick = r.nextInt(500);
	}

	@Override
	public void act(float delta) {
		
		sprite.setPosition(position.x, (float) (position.y + deltaY * Math.cos(tick++ / 10f)));
		sprite.setScale(this.getScaleX());
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(),
				sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
	}

}
