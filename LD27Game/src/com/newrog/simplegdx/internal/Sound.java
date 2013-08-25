package com.newrog.simplegdx.internal;

import com.badlogic.gdx.Gdx;

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

    public void play(float volume, float pitch)
    {
    	sound.play(volume, pitch, 0.0F);
    }


}
