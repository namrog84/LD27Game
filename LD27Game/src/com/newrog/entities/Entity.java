package com.newrog.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.newrog.myGame.Level;

public abstract class Entity {

	public Vector2 position = new Vector2(0, 0);
	public Vector2 acceleration;
	public float bounce = .0f;
	public float rotation;
	public Sprite sprite;
	public Level level;

	public boolean onGround = false;

	public abstract void update();

	public abstract void render();

	public void init(Level level) {
		this.level = level;
	}

	public Vector2 size = new Vector2(20f, 30f);

	public void tryMove(Vector2 acceleration) {

		onGround = false;

		if (level.isFree(position.x + acceleration.x, position.y, (int) (size.x), (int) (size.y * .9f), acceleration)) {
			// move freely in that direction
			position.x += acceleration.x;
		} else {
			hitWall(level.isType(position.x + acceleration.x, position.y+2, (int) (size.x), (int) (size.y * .9f), acceleration));
			if (acceleration.x > 0) {
				float xx = (position.x + size.x) / level.tileSize;
				acceleration.x = -((xx - Math.round(xx)) * level.tileSize);
			} else {
				double xx = position.x / level.tileSize;
				acceleration.x = (float) (-(xx - (int) xx) * level.tileSize);
			}
			if (level.isFree(position.x + acceleration.x, position.y, (int) size.x, (int) (size.y * .9f), acceleration)) {
				position.x += acceleration.x;
			}
			acceleration.x *= -bounce;
		}

		if (level.isFree(position.x, position.y + acceleration.y, (int) size.x, (int) size.y, acceleration)) {
			// move freely in that direction
			position.y += acceleration.y;
		} else {
			hitWall(level.isType(position.x+size.x/2, position.y + acceleration.y, (int) size.x/4, (int) size.y, acceleration));
			if (acceleration.y > 0) {

				float yy = (position.y + size.y) / level.tileSize;
				acceleration.y = -((yy - Math.round(yy)) * level.tileSize);
			} else {
				onGround = true;
				double yy = position.y / level.tileSize;
				acceleration.y = (float) (-(yy - (int) yy) * level.tileSize);
			}
			if (level.isFree(position.x, position.y + acceleration.y, (int) size.x, (int) size.y, acceleration)) {
				position.y += acceleration.y;
			}
			acceleration.y *= -bounce;
		}

	}

	private void hitWall(int f) {
		//System.out.println(f);
		if (f == 2) {
			kill();
		}
		//rotation = 0;
	}

	protected abstract void kill();
}
