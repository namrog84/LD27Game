package com.newrog.simplegdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CurrentBatch {

	private static SpriteBatch batch;
	
	private CurrentBatch() {
	}

	public static SpriteBatch get() {
		return batch;
	}

	public static void set(SpriteBatch b) {
		batch = b;
	}
}
