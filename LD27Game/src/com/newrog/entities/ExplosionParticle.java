package com.newrog.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.newrog.simplegdx.Art;
import com.newrog.simplegdx.CurrentBatch;

public class ExplosionParticle extends Entity {

	Random r = new Random();

	float xBurst = 20;
	float yBurst = 30;
	int delay = 0;
	
	public ExplosionParticle(float x, float y) {
		int cp = r.nextInt(100);
		if (cp > 60) { // 40
			sprite = new Sprite(Art.pixels[3]); // green
		} else if (cp > 35) { // 25
			sprite = new Sprite(Art.pixels[2]); // tan
		} else if (cp > 25) { // 10
			sprite = new Sprite(Art.pixels[0]); // white
		} else if (cp > 10) { // 15
			sprite = new Sprite(Art.pixels[4]); // grey
		} else { // 10
			sprite = new Sprite(Art.pixels[1]); // red
		}

		float intensity = r.nextFloat() * 40 - 20;
		float direct = r.nextFloat() * 360;

		acceleration = new Vector2(intensity * MathUtils.cosDeg(direct), intensity * MathUtils.sinDeg(direct));
		position = new Vector2(x, y);
		size.x = 2;
		size.y = 2;
		delay = r.nextInt(10);
	}

	

	@Override
	public void update() {
		delay--;
		if (delay <= 0) {
			acceleration.x *= .9;// friction
			// acceleration.x -= .7;

			acceleration.y *= .95;// friction
			acceleration.y -= .3;

			tryMove(acceleration);
			delay = 0;
		}
	}

	@Override
	public void render() {
		if (delay <= 0) {
			CurrentBatch.get().draw(sprite, position.x, position.y);
		}
	}

	@Override
	protected void kill() {
		// TODO Auto-generated method stub

	}

}
