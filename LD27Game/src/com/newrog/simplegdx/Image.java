package com.newrog.simplegdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.newrog.simplegdx.internal.Files;


/**
 * Helper class for images
 */
public class Image
{
	private Texture texture;
	private TextureRegion region;
	    
    public Image(String ref)
    {
        texture = new Texture(Files.get(ref));
        region = new TextureRegion(texture, 0, texture.getHeight(), texture.getWidth(), -texture.getHeight());
    }

    public void draw(float x, float y)
    {
        CurrentBatch.get().draw(region, x, y);
    }

    public void draw(float x, float y, float w, float h)
    {
        CurrentBatch.get().draw(region, x, y, w, h);
    }

    public void draw(float x, float y, float w, float h, float a)
    {
        CurrentBatch.get().setColor(1.0F, 1.0F, 1.0F, a);
        CurrentBatch.get().draw(region, x, y, w, h);
        CurrentBatch.get().setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

   
}