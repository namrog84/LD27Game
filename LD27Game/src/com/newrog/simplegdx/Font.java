package com.newrog.simplegdx;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.newrog.simplegdx.internal.Files;

public class Font {

	private BitmapFont bitmapFont;

	public Font(String ref) {
		bitmapFont = new BitmapFont(Files.get(ref), true);
	}

	public void draw(String string, int x, int y) {
		bitmapFont.draw(CurrentBatch.get(), string, x, y);
	}

	public void setScale(float xy) {
		bitmapFont.setScale(xy);
	}

	public void drawWrapped(String string, int x, int y, int width) {
		bitmapFont.drawWrapped(CurrentBatch.get(), string, x, y, width);
	}

	public void drawCentered(String string, int x, int y, int width) {
		bitmapFont.drawWrapped(CurrentBatch.get(), string, x, y, width, HAlignment.CENTER);
	}

	public float getWidth(String text) {
		return bitmapFont.getBounds(text).width;
	}

	public void setColor(float r, float g, float b, float a) {
		bitmapFont.setColor(r, g, b, a);
	}

}
