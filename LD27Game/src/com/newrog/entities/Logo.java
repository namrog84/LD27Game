package com.newrog.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.newrog.simplegdx.Art;

public class Logo {
	private ArrayList<Runnable> scripts = new ArrayList<Runnable>();
	public Sprite sprite;
	public Vector2 position;
	public float alpha;
	public boolean complete = false;

	public Logo(float x, float y) {
		position = new Vector2(x, y);
		sprite = new Sprite(Art.logoTextureRegion);

		sprite.setSize(sprite.getWidth(), sprite.getHeight());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setScale(5);
		sprite.setPosition(position.x, position.y);
		alpha = 0;
		scripts.add(new FadeIn());
		scripts.add(new SwirlIn());
	}

	public void update() {
		for (int i = 0; i < scripts.size(); i++) {
			scripts.get(i).run();
		}
		sprite.setPosition(position.x - sprite.getWidth() / 2, position.y);
	}

	public void render(SpriteBatch batch) {
		batch.setColor(1, 1, 1, alpha);
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(),
				sprite.getScaleY(), sprite.getRotation());
		batch.setColor(1, 1, 1, 1);
	}

	private class FadeIn implements Runnable {
		@Override
		public void run() {
			alpha += .008f;
			if (alpha >= 1f) {
				alpha = 1;
				scripts.remove(this);
			}
		}
	}

	private class SwirlIn implements Runnable {
		float swirlAngle = 0;
		float distance = 50;
		Vector2 original;

		@Override
		public void run() {
			if (original == null) {
				original = position.cpy();
			}
			position.x = (float) (original.x + distance * Math.cos(swirlAngle));
			position.y = (float) (original.y + distance * Math.sin(swirlAngle));
			swirlAngle += .1;
			if (distance > 0) {
				distance -= .3f;
			}
			if (swirlAngle >= 15) {
				scripts.remove(this);
				scripts.add(new Bounce());
			}

		}
	}

	private class Bounce implements Runnable {
		int tick = 0;

		@Override
		public void run() {
			if (tick % 40 < 20) {
				position.y += .3f;
			} else {
				position.y -= .3f;
			}
			tick++;
			if(tick > 40){
				complete = true;
			}
		}
	}

	public void dispose() {
	}

}
