package com.newrog.simplegdx;

import com.badlogic.gdx.Gdx;
import com.newrog.simplegdx.internal.Files;

public class Sound
{
	private com.badlogic.gdx.audio.Sound sound;
	
    public Sound(String ref)
    {
    	sound = Gdx.audio.newSound(Files.get(ref));
    }

    public void play()
    {
    	sound.play();
    }
    public void loop()
    {
    	sound.loop();
    }
    public void resume()
    {
    	sound.resume();
    }
    public void stop()
    {
    	sound.stop();
    }

    public void play(float volume, float pitch)
    {
    	sound.play(volume, pitch, 0.0F);
    }

   
}
